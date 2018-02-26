package kodman.re;

import java.util.List;

import kodman.re.Models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dd on 25.02.2018.
 */

public interface SMK {
    @GET("/api/get")
    Call<List<User>> getData(@Query("name") String resourceName, @Query("num") int count);

    @POST("/api/register/")
    Call<List<User>> register(@Query("username") String userName, @Query("password") String password);
    @POST("/api/login/")
    Call<List<User>> login(@Query("username") String userName, @Query("password") String password);

   // @GET("/api/get")
 //  Call<List<Model>> getData(@Query("name") String resourceName, @Query("num") int count);
}
