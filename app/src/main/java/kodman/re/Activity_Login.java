package kodman.re;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kodman.re.Constants.Constants;
import kodman.re.Models.Product;
import kodman.re.Models.ResponseLogin;
import kodman.re.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dd on 03.03.2018.
 */

public class Activity_Login extends AppCompatActivity  implements View.OnClickListener{
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPass)
    EditText etPass;
    @BindView(R.id.btnUp)
    Button btnUp;
    @BindView(R.id.btnIn)
    Button btnIn;
    Retrofit retrofit;
    SMK smkApi;


    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        //return super.onCreateView(inflater, container, savedInstanceState);
        //View view=inflater.inflate(R.layout.login,container,false);
        //Log.d(TAG,"LLLL---------");
        ButterKnife.bind(this);
        /*
        btnSignIn=view.findViewById(R.id.button2);
        btnSignUp=view.findViewById(R.id.button);
        etName=view.findViewById(R.id.editText);
        etPass=view.findViewById(R.id.editText2);
        */
        btnIn.setOnClickListener(this);
        btnUp.setOnClickListener(this);
        retrofit=new Retrofit.Builder()
                .baseUrl(Constants.PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        smkApi=retrofit.create(SMK.class);
       // return view;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnUp:

                User user= new User();
                user.setUsername(etName.getText().toString());
                user.setPassword(etPass.getText().toString());
                    smkApi.register(user).enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {



                            if(response.isSuccessful())
                            {
                                ResponseLogin rL=response.body();

                                Log.d(Constants.MAIN_TAG,"-----------------call="+call+"response = "+new Gson().toJson(response));

                                Log.d(Constants.MAIN_TAG,rL.getToken());
                            }
                            Log.d(Constants.MAIN_TAG,"Response" +response.message());
                        }

                        @Override
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            Log.d(Constants.MAIN_TAG,"------------------Failure");
                        }
                    });



                Log.d(Constants.MAIN_TAG,"Click Sign UP = "+etName.getText()+"/"+etPass.getText());
                break;

            case R.id.btnIn:


                smkApi.login(etName.getText().toString(),etPass.getText().toString()).enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                        if(response.isSuccessful())

                        {
                            Log.d(Constants.MAIN_TAG,"-----------------call="+call+"response = "+new Gson().toJson(response));
                        }
                        Log.d(Constants.MAIN_TAG,"Response" +response.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Log.d(Constants.MAIN_TAG,"------------------Failure");
                    }


                });
                break;
        }
    }
}

