package kodman.re;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kodman.re.Constants.Constants;
import kodman.re.Models.ResponseLogin;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import kodman.re.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by dd on 27.02.2018.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
final String TAG="LoginFragment";

    @BindView(R.id.editText)
    EditText etName;
    @BindView(R.id.editText2)
    EditText etPass;
    @BindView(R.id.button)
    Button btnSignUp;
    @BindView(R.id.button2)
    Button btnSignIn;
    Retrofit retrofit;
    SMK smkApi;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.login,container,false);
        //Log.d(TAG,"LLLL---------");
        ButterKnife.bind(view);
        btnSignIn=view.findViewById(R.id.button2);
        btnSignUp=view.findViewById(R.id.button);
        etName=view.findViewById(R.id.editText);
        etPass=view.findViewById(R.id.editText2);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        retrofit=new Retrofit.Builder()
                .baseUrl(Constants.PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        smkApi=retrofit.create(SMK.class);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button:

                smkApi.register(etName.getText().toString(),etPass.getText().toString()).enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                        if(response.isSuccessful())
                            {
                                Log.d(TAG,"-----------------call="+"response = ");
                            }
                        Log.d(TAG,"Response" +response.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Log.d(TAG,"------------------Failure");
                    }
                });
break;
            //Log.d(TAG,"Click Sign UP");
            case R.id.button2:


                smkApi.login(etName.getText().toString(),etPass.getText().toString()).enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                        if(response.isSuccessful())

                        {
                            Log.d(TAG,"-----------------call="+call+"response = "+response.message());
                        }
                        Log.d(TAG,"Response" +response.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Log.d(TAG,"------------------Failure");
                    }
                });
                break;
        }
    }
}
