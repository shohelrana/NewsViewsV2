package info.shohelranabd.newsviewsv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import info.shohelranabd.newsviewsv2.common.Common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.ClientCertRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsDetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String url;

    @BindView(R.id.detailsRefresh)
    SwipeRefreshLayout detailsRefresh;
    @BindView(R.id.newsWebView)
    WebView newsWebView;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        ButterKnife.bind(this);

        dialog = new SpotsDialog.Builder().setContext(this).build();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            url = bundle.getString("url");

            newsWebView.getSettings().setJavaScriptEnabled(true);
            newsWebView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return false;
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                }
            });

            newsWebView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if (newProgress > 90 )
                        detailsRefresh.setRefreshing(false);
                        //dialog.dismiss();
                }
            });
            //dialog.show();
            detailsRefresh.setRefreshing(true);
            loadUrlInWv();
        }
    }

    @Override
    public void onRefresh() {
        loadUrlInWv();
    }

    public void loadUrlInWv() {
        if(Common.isNetworkAvailable(this))
            newsWebView.loadUrl(url);
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning message")
                    .setMessage("Check your internet connection.")
                    .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loadUrlInWv();
                            dialog.dismiss();
                        }
                    });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    NewsDetailsActivity.this.finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
