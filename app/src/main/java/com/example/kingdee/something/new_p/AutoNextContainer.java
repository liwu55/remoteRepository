package com.example.kingdee.something.new_p;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kingdee.something.R;

public class AutoNextContainer extends ViewGroup implements View.OnClickListener {

    private int padding = 20;
    private int margin = 15;
    private ButtonClickListener listener;

    public AutoNextContainer(Context context) {
        super(context);
    }

    public AutoNextContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addButton(String buttonText) {
        final TextView tv = createTextView(buttonText);
        addView(tv);
        tv.setOnClickListener(this);
        invalidate();
    }

    private TextView createTextView(String buttonText) {
        TextView tv = new TextView(getContext());
        tv.setText(buttonText);
        tv.setTextSize(16);
        tv.setPadding(20, 10, 20, 10);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape));
        return tv;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(MeasureSpec.makeMeasureSpec(10, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(10, MeasureSpec.UNSPECIFIED));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int widthTotal = getMeasuredWidth();
        int left = padding;
        int top = padding;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (left == 0) {
                child.layout(left, top, left + childWidth, top + childHeight);
                left += childWidth;
                left += margin;
                continue;
            }
            int lessWidth = widthTotal - left - margin;
            if (lessWidth < childWidth) {
                left = padding;
                top += childHeight;
                top += margin;
            }
            child.layout(left, top, left + childWidth, top + childHeight);
            left += childWidth;
            left += margin;
        }
    }

    public void setButtonClickListener(ButtonClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(!(v instanceof TextView)){
            return;
        }
        if(listener==null){
            return;
        }
        String buttonText = ((TextView) v).getText().toString();
        listener.buttonClick(buttonText);
    }

    public interface ButtonClickListener{
        void buttonClick(String buttonText);
    }
}
