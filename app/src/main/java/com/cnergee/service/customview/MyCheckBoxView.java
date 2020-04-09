package com.cnergee.service.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.cnergee.broadbandservice.R;


/**
 * Created by Siddhesh on 8/12/2016.
 */
public class MyCheckBoxView extends CheckBox {

    public MyCheckBoxView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public MyCheckBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCheckBoxView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.MyTextView);
            String fontName = a.getString(R.styleable.MyTextView_fontName);
//            if (fontName != null) {
//                Typeface myTypeface = Typeface.createFromAsset(getContext()
//                        .getAssets(), /*"fonts/" +*/ fontName);
//                setTypeface(myTypeface);
//            }
            a.recycle();
        }
    }
}

