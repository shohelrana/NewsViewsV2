package info.shohelranabd.newsviewsv2.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import info.shohelranabd.newsviewsv2.interfaces.NewsService;
import info.shohelranabd.newsviewsv2.remote.NewsClient;
import okhttp3.OkHttpClient;

/**
 * Created by Md. Shohel Rana on 30 December,2018
 */
public class Common {
    //  Preference name
    public static final String PREFS_NAME = "news_prefs";
    public static final String ACT_ID_PREFS_KEY = "activity_id";
    public static final String LOG_WITH_PREFS_KEY = "login_with";
    public static final String LOG_PREFS_KY = "isLoggedIn";

    //log with name
    public static final String LOG_WITH_FB= "login_with_fb";
    public static final String LOG_WITH_GOOGLE = "login_with_google";

    public static final String SEARCH_KEY = "search_key";

    private static final String BASE_URL = "https://newsapi.org/";
    public static final String API_KEY = "c4d1e307e4b34358b6f4a7c9dc2b9e0c";

    public static NewsService getNewsService() {
        return NewsClient.getNewsClient(BASE_URL).create(NewsService.class);
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
