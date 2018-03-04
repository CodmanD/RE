package kodman.re;

import java.util.List;

import kodman.re.Models.Product;
import kodman.re.Models.ResponseLogin;
import kodman.re.Models.ResponsePostReview;
import kodman.re.Models.ResponseReview;
import kodman.re.Models.Review;
import kodman.re.Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SMK {

    @GET("api/products/")
    Call<List<Product>> getProduct();

    @GET("api/reviews/{id}")
    Call<List<ResponseReview>> getReviews(@Path("id")String id);

    @POST("api/reviews/{id}")
    Call<ResponsePostReview> postReview(@Header("Authorization")String token, @Path("id")String id,@Body Review review);


    @POST("api/register/")
    Call<ResponseLogin> register(@Body User user);
    @POST("api/login/")
    Call<ResponseLogin> login(@Body User user);
}
