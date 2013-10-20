NoNonsenseUtils
===============

Various utility implementations for Android, such as TextViews that
handle links properly.

## Links in TextViews or EditText Adding links to TextViews is easy in
Android. It is completely broken however when done in TextViews that
populate ListItems, or in EditTexts.  The code implemented in
_TextMethods.java_ solves the problems in these two circumstances.

Examples can be seen in
_com.nononsenseapps.utils.views.LinkedTextView.java_ and
_com.nononsenseapps.utils.LinkedEditText.java_.

Just use them instead of your normal EditText for example. If you
already have your custom TextView implemented, have a look inside and
copy the _OnTextChangedListener_ from the Constructor and the
_OnTouchEvent_ method.
