package kodman.re;

import java.util.List;

import kodman.re.Models.Product;
import kodman.re.Models.ResponseLogin;
import kodman.re.Models.ResponseReview;
import kodman.re.Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dd on 25.02.2018.
 */

public interface SMK {
    @GET("api/products/")
    Call<List<Product>> getProduct();

    @GET("api/reviews/{id}")
    Call<List<ResponseReview>> getReviews(@Path("id")String id);

    @POST("api/reviews/{id}")
    Call<List<ResponseReview>> postReviews(@Path("id")String id);

    @POST("api/register/")
    Call<ResponseLogin> register(@Body User user);
    @POST("api/login/")
    Call<ResponseLogin> login(@Body User user);

    //@POST("api/register/{username}/{password}")
    //Call<ResponseLogin> register(@Path("username") String userName, @Path("password") String password);
   // @POST("api/login/{username}/{password}")
   // Call<ResponseLogin> login(@Path("username") String userName, @Path("password") String password);

  // @GET("api/register/{name}/{password}")
   // Call<ResponseLogin> authenticate(@Path("name") String email, @Path("password") String password);
  //  @POST("api/{name}/{password}")
  //  Call<ResponseLogin> registration(@Path("name") String email, @Path("password") String password);

   // @GET("/api/get")
 //  Call<List<Model>> getData(@Query("name") String resourceName, @Query("num") int count);
}
