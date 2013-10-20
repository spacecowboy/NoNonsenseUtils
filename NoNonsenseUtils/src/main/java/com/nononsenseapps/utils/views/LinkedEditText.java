package com.nononsenseapps.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.nononsenseapps.utils.R;
import com.nononsenseapps.utils.TextMethods;

/**
 * An EditText where links are clickable and the touches are handled properly.
 */
public class LinkedEditText extends EditText {

    private boolean clickableLinks = true;
    private TextMethods.LinkHandler handler = null;

    public LinkedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.LinkedTextView, 0, 0);

        if (a != null) {
            try {
                clickableLinks = a.getBoolean(R.styleable.LinkedTextView_clickableLinks, true);

            } finally {
                a.recycle();
            }
        }

        // Link on change
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (clickableLinks) {
                    TextMethods.addLinks(LinkedEditText.this);
                }
            }
        });
    }

    public void setClickableLinks(final boolean clickableLinks) {
        this.clickableLinks = clickableLinks;
    }

    public void setHandler(final TextMethods.LinkHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return TextMethods.OnLinkTouchEvent(this, event, handler) || super.onTouchEvent(event);
    }
}
