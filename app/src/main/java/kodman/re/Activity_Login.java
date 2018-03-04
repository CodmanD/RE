package kodman.re;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kodman.re.Constants.Constants;
import kodman.re.Models.ResponseLogin;
import kodman.re.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ButterKnife.bind(this);
        btnIn.setOnClickListener(this);
        btnUp.setOnClickListener(this);
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        smkApi = retrofit.create(SMK.class);

    }


    @Override
    public void onClick(View v) {

        if(etName.getText().toString().equals(""))
        {
            Toast.makeText(this,"Incorrect login",Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(etName.getText().toString(), etPass.getText().toString());
        final ProgressDialog dialog = ProgressDialog.show(this, "",
                " Please wait...", true);
        dialog.show();


        switch (v.getId()) {
            case R.id.btnUp:
                smkApi.register(user).enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            ResponseLogin rL = response.body();

                            if (rL.isSuccess()) {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(Constants.TOKEN, rL.getToken());
                                editor.commit();
                                Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                etName.setError("Error");
                                //etPass.setError("Error");
                            }

                        } else
                            Toast.makeText(Activity_Login.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        //Log.e(Constants.MAIN_TAG, "------------------Failure");
                        dialog.dismiss();
                    }
                });


                Log.d(Constants.MAIN_TAG, "Click Sign UP = " + etName.getText() + "/" + etPass.getText());
                break;

            case R.id.btnIn:

                smkApi.login(user).enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            ResponseLogin rL = response.body();
                            if (rL.isSuccess()) {
                                saveTokenToPrefShared(rL.getToken());
                                Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                etName.setError("Error");
                                //etPass.setError("Error");
                            }

                        } else
                            Toast.makeText(Activity_Login.this, response.message(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        //Log.e(Constants.MAIN_TAG, "------------------Failure");
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    private void saveTokenToPrefShared(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.TOKEN, token);
        editor.commit();
    }

}

