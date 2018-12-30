// Generated code from Butter Knife. Do not modify!
package info.shohelranabd.newsviewsv2;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.gms.common.SignInButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f08013e;

  private View view7f0800a7;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.sign_in_button, "field 'signInButton' and method 'googleSignIn'");
    target.signInButton = Utils.castView(view, R.id.sign_in_button, "field 'signInButton'", SignInButton.class);
    view7f08013e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.googleSignIn();
      }
    });
    view = Utils.findRequiredView(source, R.id.g_signout_bnt, "field 'g_signout_bnt' and method 'gSignOut'");
    target.g_signout_bnt = Utils.castView(view, R.id.g_signout_bnt, "field 'g_signout_bnt'", Button.class);
    view7f0800a7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.gSignOut();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.signInButton = null;
    target.g_signout_bnt = null;

    view7f08013e.setOnClickListener(null);
    view7f08013e = null;
    view7f0800a7.setOnClickListener(null);
    view7f0800a7 = null;
  }
}
