package com.spm.ui.CustomElements;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.card.MaterialCardView;

public class SquareMaterialCardView extends MaterialCardView {

    public SquareMaterialCardView(Context context) {
        super(context);
    }

    public SquareMaterialCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareMaterialCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set the height to be the same as the width
        //noinspection SuspiciousNameCombination
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}

