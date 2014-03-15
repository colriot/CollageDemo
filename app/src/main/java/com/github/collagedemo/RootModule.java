package com.github.collagedemo;

import android.app.Application;
import android.util.Log;
import com.github.collagedemo.ui.CollageFragment;
import com.github.collagedemo.ui.GridFragment;
import com.github.collagedemo.ui.InputFragment;
import com.github.collagedemo.ui.MainActivity;
import com.squareup.okhttp.HttpResponseCache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

import static retrofit.RestAdapter.LogLevel;

/**
 * @author Sergey Ryabov
 *         Date: 12.03.14
 */
@Module(injects = {
                App.class,
                MainActivity.class,
                InputFragment.class,
                GridFragment.class,
                CollageFragment.class,
                GridFragment.MyMakeCollageTask.class,
        }
)
public class RootModule {
    private static final String TAG = "InstaCollage";

    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private final App app;

    public RootModule(App app) {
        this.app = app;
    }

    @Provides @Singleton Application provideApplication() {
        return app;
    }

    @Provides @Singleton RestAdapter provideRestAdapter(RequestInterceptor requestInterceptor, OkHttpClient client) {
        return new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(InstagramApi.PRODUCTION_API_HOST)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                .build();
    }

    @Provides @Singleton InstagramApi provideInstagram(RestAdapter restAdapter) {
        return restAdapter.create(InstagramApi.class);
    }

    @Provides @Singleton RequestInterceptor provideRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addQueryParam("client_id", InstagramApi.CLIENT_ID);
            }
        };
    }

    @Provides @Singleton OkHttpClient provideOkHttpClient(Application app) {
        final OkHttpClient okHttpClient = new OkHttpClient();

        try {
            final File cacheDir = new File(app.getCacheDir(), "http");
            final HttpResponseCache cache = new HttpResponseCache(cacheDir, DISK_CACHE_SIZE);
            okHttpClient.setResponseCache(cache);
        } catch (IOException e) {
            Log.e(TAG, "Installing disk cache failed.");
        }

        return okHttpClient;
    }

    @Provides @Singleton Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(client))
                .debugging(BuildConfig.DEBUG)
                .build();
    }
}
