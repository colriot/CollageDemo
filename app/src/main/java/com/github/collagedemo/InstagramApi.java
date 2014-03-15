package com.github.collagedemo;

import com.github.collagedemo.model.IGResponse;
import com.github.collagedemo.model.Media;
import com.github.collagedemo.model.User;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * @author Sergey Ryabov
 *         Date: 12.03.14
 */
public interface InstagramApi {

    public static final String CLIENT_ID = "677b4c354f1f48b89f355d882a7a8328";

    public static final String PRODUCTION_API_HOST = "https://api.instagram.com/v1";


    @GET("/users/search")
    Observable<IGResponse<User>> searchUser(@Query("q") String query);

    @GET("/users/{user-id}/media/recent")
    Observable<IGResponse<Media>> getRecentMedia(@Path("user-id") String userId);
}
