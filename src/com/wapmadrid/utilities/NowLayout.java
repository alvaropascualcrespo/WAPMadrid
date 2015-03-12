package com.wapmadrid.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.wapmadrid.R;

public class NowLayout extends RelativeLayout implements OnGlobalLayoutListener {

	public NowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayoutObserver();

	}

	public NowLayout(Context context) {
		super(context);
		initLayoutObserver();
	}

	private void initLayoutObserver() {
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@Override
	public void onGlobalLayout() {
		getViewTreeObserver().removeGlobalOnLayoutListener(this);

		final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;

		boolean inversed = false;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);

			int[] location = new int[2];

			child.getLocationOnScreen(location);

			if (location[1] > heightPx) {
				break;
			}

			if (!inversed) {
				child.startAnimation(AnimationUtils.loadAnimation(getContext(),
						R.anim.up_from_bottom));
			} else {
				child.startAnimation(AnimationUtils.loadAnimation(getContext(),
						R.anim.down_from_top));
			}

			inversed = !inversed;
		}

	}

}
