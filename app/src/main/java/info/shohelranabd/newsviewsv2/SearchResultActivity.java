package info.shohelranabd.newsviewsv2;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import info.shohelranabd.newsviewsv2.common.Common;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

public class SearchResultActivity extends AppCompatActivity {

    OkHttpClient client;
    StringBuilder reqUrlBuilder;
    String response = "";

    @BindView(R.id.resutTextV)
    TextView resutTextV;

    AlertDialog alertDialog;

    String TAG = "SearchResultAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ButterKnife.bind(this);

        alertDialog = new SpotsDialog.Builder().setContext(this).build();

        Bundle bundle = getIntent().getExtras();

        client = new OkHttpClient();
        reqUrlBuilder = new StringBuilder("http://numbersapi.com/");

        if (bundle != null) {
            String search = bundle.getString(Common.SEARCH_KEY);
            boolean isValid = false;
            try {
                int number = Integer.parseInt(search);

                reqUrlBuilder.append(number)
                        .append("/trivia");

                isValid = true;

            } catch (NumberFormatException e) {
                if (search.contains("/") && search.length() <= 5) {
                    String[] splittedAr = search.split("/");
                    try {
                        int month = Integer.parseInt(splittedAr[0]);
                        int day = Integer.parseInt(splittedAr[1]);

                        if (month > 0 && month <= 12 && day > 0 && day <= 31) {
                            reqUrlBuilder.append(month)
                                    .append("/")
                                    .append(day)
                                    .append("/date");

                            isValid = true;
                        }

                    } catch (NumberFormatException ex) {
                    }
                }
            }

            if (isValid) // execute asynctask

                new AsyncTask<String, String, String>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        alertDialog.show();
                    }

                    @Override
                    protected String doInBackground(String... strings) {
                        try {
                            response = runReq(reqUrlBuilder.toString());
                            Log.d(TAG, response);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, e.getMessage());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        resutTextV.setText(response);
                        super.onPostExecute(s);
                        alertDialog.dismiss();
                    }
                }.execute();

            else {
                Toast.makeText(SearchResultActivity.this, "Invalid input", Toast.LENGTH_LONG).show();
            }
        }
    }

    String runReq(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
