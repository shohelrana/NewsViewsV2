// Generated code from Butter Knife. Do not modify!
package info.shohelranabd.newsviewsv2;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchResultActivity_ViewBinding implements Unbinder {
  private SearchResultActivity target;

  @UiThread
  public SearchResultActivity_ViewBinding(SearchResultActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchResultActivity_ViewBinding(SearchResultActivity target, View source) {
    this.target = target;

    target.resutTextV = Utils.findRequiredViewAsType(source, R.id.resutTextV, "field 'resutTextV'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchResultActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.resutTextV = null;
  }
}
