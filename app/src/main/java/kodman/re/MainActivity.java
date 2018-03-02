package kodman.re;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import kodman.re.Constants.Constants;
import kodman.re.Models.Product;
import kodman.re.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    final String PATH="http://smktesting.herokuapp.com/";
    final String TAG="MainActivity:";

    RecyclerView rv;
    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate");
        setContentView(R.layout.activity_main);

        products= new ArrayList<Product>() ;


      //  FragmentTransaction fTrans = getFragmentManager().beginTransaction();
      //  fTrans.add(R.id.frame,new LoginFragment(),"login");
      //  fTrans.commit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SMK smkApi=retrofit.create(SMK.class);

       // setContentView(R.layout.product_item);
      //  TextView tvTitle= findViewById(R.id.tvTitle);
       // final ImageView iv= findViewById(R.id.iv);
        final RecyclerView rv= findViewById(R.id.rvProducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        final MyAdapter adapter=   new MyAdapter(this,products);
        //Log.d(TAG,"Rv = "+rv);
       rv.setAdapter(adapter);
        Log.d(TAG,"-------------------Count ="+rv.getAdapter().getItemCount());


        smkApi.getProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                {
                    //ResponseLogin rL=response.body();
                     for(Product p:response.body())
                    {
                        products.add(p);

                    }
                   // rv.setAdapter(new MyAdapter(MainActivity.this,products));
                    Log.d(TAG,"-----------------count = "+rv.getAdapter().getItemCount());
                   rv.getAdapter().notifyDataSetChanged();

                }
                Log.d(TAG,"Response" +response.message());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d(TAG,"------------------Failure");
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //
    }
}
