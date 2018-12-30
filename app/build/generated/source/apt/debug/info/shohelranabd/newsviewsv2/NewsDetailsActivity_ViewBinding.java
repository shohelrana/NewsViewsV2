// Generated code from Butter Knife. Do not modify!
package info.shohelranabd.newsviewsv2;

import android.view.View;
import android.webkit.WebView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsDetailsActivity_ViewBinding implements Unbinder {
  private NewsDetailsActivity target;

  @UiThread
  public NewsDetailsActivity_ViewBinding(NewsDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewsDetailsActivity_ViewBinding(NewsDetailsActivity target, View source) {
    this.target = target;

    target.detailsRefresh = Utils.findRequiredViewAsType(source, R.id.detailsRefresh, "field 'detailsRefresh'", SwipeRefreshLayout.class);
    target.newsWebView = Utils.findRequiredViewAsType(source, R.id.newsWebView, "field 'newsWebView'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.detailsRefresh = null;
    target.newsWebView = null;
  }
}
