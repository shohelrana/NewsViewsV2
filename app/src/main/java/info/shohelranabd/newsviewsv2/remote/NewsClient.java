package info.shohelranabd.newsviewsv2.remote;

import info.shohelranabd.newsviewsv2.common.Common;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Md. Shohel Rana on 30 December,2018
 */
public class NewsClient {
    static Retrofit retrofit = null;
    public static Retrofit getNewsClient(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(Common.getUnsafeOkHttpClient())
                .build();

        return retrofit;
    }
}
