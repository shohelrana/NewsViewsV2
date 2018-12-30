// Generated code from Butter Knife. Do not modify!
package info.shohelranabd.newsviewsv2.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import info.shohelranabd.newsviewsv2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsViewHolder_ViewBinding implements Unbinder {
  private NewsViewHolder target;

  @UiThread
  public NewsViewHolder_ViewBinding(NewsViewHolder target, View source) {
    this.target = target;

    target.article_iamge_thumb = Utils.findRequiredViewAsType(source, R.id.article_iamge_thumb, "field 'article_iamge_thumb'", ImageView.class);
    target.article_title = Utils.findRequiredViewAsType(source, R.id.article_title, "field 'article_title'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.article_iamge_thumb = null;
    target.article_title = null;
  }
}
