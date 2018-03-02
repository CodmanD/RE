package kodman.re;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import kodman.re.Constants.Constants;

/**
 * Created by dd on 02.03.2018.
 */

public class ScreenProduct extends AppCompatActivity {

    @BindView(R.id.ratingBar)
    RatingBar rBar;

    @BindView(R.id.ivFoto)
    ImageView imageView;

    @BindView(R.id.toolbarProduct)
    Toolbar toolbar;

    int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_product);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScreenProduct.this, MainActivity.class);
                startActivity(intent);
            }
        });



        Intent intent=getIntent();

        id=intent.getIntExtra(Constants.ID,-1);


        Log.d(Constants.MAIN_TAG,"---------------rate = "+rBar);
        Glide.with(this)
                .load(Constants.PATH_IMAGE+"img"+id+".png")
               // .centerCrop()

                .into(imageView);
    }
}
