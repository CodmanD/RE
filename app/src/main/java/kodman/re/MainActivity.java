package kodman.re;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kodman.re.Constants.Constants;
import kodman.re.Models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbarMain)
    Toolbar toolbar;
    @BindView(R.id.rvProducts)
    RecyclerView recyclerView;

    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        toolbar.setTitle("Select product");
        setSupportActionBar(toolbar);

        products= new ArrayList<Product>() ;

        final RecyclerView rv= findViewById(R.id.rvProducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        final MyAdapter adapter=   new MyAdapter(this,products);
        rv.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SMK smkApi=retrofit.create(SMK.class);
        smkApi.getProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                {
                    for(Product p:response.body())
                    {
                        products.add(p);
                    }
                   rv.getAdapter().notifyDataSetChanged();
                }
                else
                    Toast.makeText(MainActivity.this,response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Log.e(TAG,"----Failure");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionLogout: {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                if(preferences.contains(Constants.TOKEN))
                {
                    SharedPreferences.Editor editor = preferences.edit();
                    preferences.edit().clear();
                    editor.commit();
                    Log.d(Constants.MAIN_TAG,"CLEAR SHARED");
                }
                Intent intent = new Intent(MainActivity.this,Activity_Login.class);

                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
