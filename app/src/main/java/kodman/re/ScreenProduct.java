package kodman.re;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import kodman.re.Constants.Constants;
import kodman.re.Models.Product;
import kodman.re.Models.ResponsePostReview;
import kodman.re.Models.ResponseReview;
import kodman.re.Models.Review;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ScreenProduct extends AppCompatActivity implements View.OnClickListener {
    List<ResponseReview> reviews = new ArrayList<>();

    @BindView(R.id.etReview)
    EditText etReview;

    @BindView(R.id.ratingBar)
    RatingBar rBar;

    @BindView(R.id.ivFoto)
    ImageView ivFoto;

    @BindView(R.id.toolbarProduct)
    Toolbar toolbar;

    @BindView(R.id.btnGetReviews)
    Button btnGetReviews;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindView(R.id.tvDescipt)
    TextView tvDescript;

    Product product;
    int id;
    String token;

    SMK smkApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_product);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        token = preferences.getString(Constants.TOKEN, "NotToken");
        Log.d(Constants.MAIN_TAG, "Token = " + token);

        ButterKnife.bind(this);

        btnGetReviews.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);

        this.product = new Product();
        Intent intent = getIntent();

        product.setId(intent.getStringExtra(Constants.ID));
        product.setTitle(intent.getStringExtra(Constants.TITLE));
        product.setText(intent.getStringExtra(Constants.TEXT));
        toolbar.setTitle(product.getTitle());
        tvDescript.setText(product.getText());

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ScreenProduct.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Glide.with(this)
                .load(Constants.PATH_IMAGE + "img" + product.getId() + ".png")
                //.centerCrop()
                .fitCenter()
                .into(ivFoto);

        smkApi = new Retrofit.Builder()
                .baseUrl(Constants.PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(SMK.class);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                postReview();
                break;
            case R.id.btnGetReviews:
                showReviews();
                break;
        }
    }

    private void postReview() {
        Review review = new Review((int) rBar.getRating(), etReview.getText().toString());
        smkApi.postReview("Token " + token, product.getId(), review).enqueue(new Callback<ResponsePostReview>() {

            @Override
            public void onResponse(Call<ResponsePostReview> call, Response<ResponsePostReview> response) {
                if (response.isSuccessful()) {

                    ResponsePostReview rR = response.body();
                    Toast.makeText(getBaseContext(), "Added review", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePostReview> call, Throwable t) {
                // Log.e(Constants.MAIN_TAG, "------------------Failure");
            }
        });


    }

    private void showReviews() {
        final ProgressDialog dialog = ProgressDialog.show(this, "",
                " Please wait...", true);
        dialog.show();
        smkApi.getReviews(product.getId()).enqueue(new Callback<List<ResponseReview>>() {
            @Override
            public void onResponse(Call<List<ResponseReview>> call, Response<List<ResponseReview>> response) {

                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (reviews.size() > 0)
                        reviews.clear();
                    for (ResponseReview r : response.body()) {

                        reviews.add(r);
                    }
                    createListReviews();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseReview>> call, Throwable t) {
                //Log.e(Constants.MAIN_TAG, "------------------Failure");
                dialog.dismiss();
            }
        });

    }


    private void createListReviews() {
        View view = getLayoutInflater().inflate(R.layout.list_reviews, null);


        ArrayAdapter<ResponseReview> adapter = new ArrayAdapter<ResponseReview>(this,
                R.layout.item_review, R.id.tvReview, reviews) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                TextView tvUser = (TextView) view.findViewById(R.id.tvUser);
                TextView tvReview = (TextView) view.findViewById(R.id.tvReview);
                RatingBar rBarUser = (RatingBar) view.findViewById(R.id.rBarUser);

                ResponseReview review = reviews.get(position);
                tvUser.setText(review.getCreated_by().getUsername());
                tvReview.setText(review.getText());
                rBarUser.setRating(review.getRate());

                return view;
            }
        };
        ListView lv = view.findViewById(R.id.lvReviews);
        lv.setAdapter(adapter);

        AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setTitle(R.string.reviews)
                .setView(view)
                .setCancelable(true)
                .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        adb.create().show();
    }

}
