package org.nearbyshops.enduser.DaggerModules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.nearbyshops.enduser.Model.CartItem;
import org.nearbyshops.enduser.MyApplication;
import org.nearbyshops.enduser.RetrofitRESTContract.CartItemService;
import org.nearbyshops.enduser.RetrofitRESTContract.CartService;
import org.nearbyshops.enduser.RetrofitRESTContract.CartStatsService;
import org.nearbyshops.enduser.RetrofitRESTContract.ShopItemService;
import org.nearbyshops.enduser.Utility.UtilityGeneral;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sumeet on 14/5/16.
 */

        /*
        retrofit = new Retrofit.Builder()
                .baseUrl(UtilityGeneral.getServiceURL(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        */

@Module
public class NetModule {

    String serviceURL;

    // Constructor needs one parameter to instantiate.
    public NetModule() {

    }

    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    /*
    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    */

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        // cache is commented out ... you can add cache by putting it back in the builder options
        //.cache(cache)

        //Cache cache

        return client;
    }



    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(UtilityGeneral.getServiceURL(MyApplication.getAppContext()))
                .client(okHttpClient)
                .build();

        return retrofit;
    }


    @Provides
    @Singleton
    ShopItemService provideShopItemService(Retrofit retrofit)
    {

        ShopItemService shopItemService = retrofit.create(ShopItemService.class);

        return shopItemService;
    }


    @Provides
    @Singleton
    CartService provideCartService(Retrofit retrofit)
    {
        CartService cartService = retrofit.create(CartService.class);

        return cartService;
    }


    @Provides
    @Singleton
    CartItemService provideCartItemService(Retrofit retrofit)
    {
        CartItemService cartItemService = retrofit.create(CartItemService.class);

        return  cartItemService;
    }


    @Provides
    @Singleton
    CartStatsService provideCartStatsService(Retrofit retrofit)
    {
        CartStatsService service = retrofit.create(CartStatsService.class);

        return service;
    }

}