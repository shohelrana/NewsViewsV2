package info.shohelranabd.newsviewsv2.interfaces;

import info.shohelranabd.newsviewsv2.common.Common;
import info.shohelranabd.newsviewsv2.model.News;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Md. Shohel Rana on 30 December,2018
 */
public interface NewsService {
    @GET("v2/top-headlines?language=en&apiKey=" + Common.API_KEY)
    Call<News> getNews();
}
