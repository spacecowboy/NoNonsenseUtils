package com.nononsenseapps.utils;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.ArrowKeyMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Contains methods that operate on Text retrieved in an EditText or TextView.
 */
public class TextMethods {

    public static void addLinks(final TextView view) {
        Linkify.addLinks(view, Linkify.ALL);
        // Links shouldn't steal click focus
        // But text must still be selectable etc
        view.setMovementMethod(new ArrowKeyMovementMethod());
    }

    /**
     * If user did not touch a link, false is returned and you should
     * let the regular OnTouchEvent method do its thing.
     * <p/>
     * Will perform the system's standard action for the link in question,
     * which could be a phone number or a web url for example.
     * <p/>
     * If a handler is given, the link is handed off to that.
     *
     * @return True if the touch was handled.
     */
    public static boolean OnLinkTouchEvent(final TextView view, final MotionEvent event,
                                           final LinkHandler handler) {
        final Object text = view.getText();
        if (text instanceof Spanned) {
            final Spannable buffer = (Spannable) text;

            final int action = event.getAction();

            if (action == MotionEvent.ACTION_UP
                    || action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= view.getTotalPaddingLeft();
                y -= view.getTotalPaddingTop();

                x += view.getScrollX();
                y += view.getScrollY();

                Layout layout = view.getLayout();
                final int line = layout.getLineForVertical(y);
                final int off = layout.getOffsetForHorizontal(line, x);

                final ClickableSpan[] link = buffer.getSpans(off, off,
                        ClickableSpan.class);

                // Cant click to the right of a span, if the line ends with the span!
                if (x <= layout.getLineRight(line) && link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        if (handler != null) {
                            handler.onClick(getLinkText(link[0], buffer));
                        } else {
                            link[0].onClick(view);
                        }
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                    }
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * Return the text of a clickable span
     */
    public static CharSequence getLinkText(final ClickableSpan link, final Spannable text) {
        return text.subSequence(text.getSpanStart(link), text.getSpanEnd(link));
    }

    public interface LinkHandler {
        /**
         * Called when the link was clicked
         */
        public void onClick(final CharSequence linkText);
    }
}
