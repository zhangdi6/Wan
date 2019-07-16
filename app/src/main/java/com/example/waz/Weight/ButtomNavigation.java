package com.example.waz.Weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class ButtomNavigation extends CoordinatorLayout.Behavior<View> {
    public ButtomNavigation( Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean layoutDependsOn( CoordinatorLayout parent,  BottomNavigationView child,  View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            this.updateSnackbar(child, (Snackbar.SnackbarLayout) dependency);
        }

        return super.layoutDependsOn(parent, child, dependency);
    }

    public boolean onStartNestedScroll( CoordinatorLayout coordinatorLayout,  BottomNavigationView child,  View directTargetChild,  View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    public void onNestedPreScroll( CoordinatorLayout coordinatorLayout,  BottomNavigationView child,  View target, int dx, int dy,  int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        child.setTranslationY(Math.max(0.0f, Math.min(child.getHeight(), child.getTranslationY() + dy)));
    }
    private void updateSnackbar(BottomNavigationView child, Snackbar.SnackbarLayout snackbarLayout) {
        if (snackbarLayout.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            android.view.ViewGroup.LayoutParams layoutParams = snackbarLayout.getLayoutParams();
            if (layoutParams == null) {
                throw new RuntimeException("null cannot be cast to non-null type android.support.design.widget.CoordinatorLayout.LayoutParams");
            }
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layoutParams;
            params.setAnchorId(child.getId());
            params.anchorGravity = Gravity.TOP;
            params.gravity = Gravity.TOP;
            snackbarLayout.setLayoutParams(params);
        }

    }
}
