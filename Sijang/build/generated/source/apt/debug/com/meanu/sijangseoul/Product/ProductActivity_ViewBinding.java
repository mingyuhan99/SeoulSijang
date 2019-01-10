// Generated code from Butter Knife. Do not modify!
package com.meanu.sijangseoul.Product;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.meanu.sijangseoul.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProductActivity_ViewBinding implements Unbinder {
  private ProductActivity target;

  private View view2131296260;

  @UiThread
  public ProductActivity_ViewBinding(ProductActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProductActivity_ViewBinding(final ProductActivity target, View source) {
    this.target = target;

    View view;
    target.myCurruntLocation = Utils.findRequiredViewAsType(source, R.id.myCurruntLocation, "field 'myCurruntLocation'", TextView.class);
    view = Utils.findRequiredView(source, R.id.NestedScrollView, "method 'setNestedScrollView'");
    view2131296260 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setNestedScrollView();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.myCurruntLocation = null;

    view2131296260.setOnClickListener(null);
    view2131296260 = null;
  }
}
