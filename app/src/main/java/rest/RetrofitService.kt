package rest

import com.appstyx.authtest.models.GenderResponse
import com.appstyx.authtest.models.SignUpResponse
import com.appstyx.authtest.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface RetrofitService {

    //Be careful do not add "/" before any api

    @GET("genders")
    suspend fun getGenders():Response<GenderResponse>

    @POST("users")
    suspend fun signUpUser(
        @Query("email")email:String?,
        @Query("firstName")firstName:String?,
        @Query("lastName")lastName:String?,
        @Query("gender")gender:String?,
    ):Response<SignUpResponse>

    @GET("users/me")
    suspend fun getProfile():Response<UserResponse>

}