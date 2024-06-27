package gay.nihil.lena.midikeypad;

import android.view.MotionEvent;
import android.view.View;

public class ManiaStaticKeypadHandler extends KeypadHandler {
    public ManiaStaticKeypadHandler(View view) {
        super(view);
    }

    @Override
    public KeypadData HandleMotionEvent(MotionEvent event) {
        float x = event.getX(event.getActionIndex());
        float width = view.getWidth();
        float keyWidth = width / 4;

        int code = 60 + (int) (x / keyWidth);

        int a = event.getActionMasked();
        if (a == MotionEvent.ACTION_DOWN || a == MotionEvent.ACTION_POINTER_DOWN) {
            return new KeypadData(true, true, code);
        } else if (a == MotionEvent.ACTION_UP || a == MotionEvent.ACTION_POINTER_UP) {
            return new KeypadData(true, false, code);
        } else {
            return new KeypadData(false, false, 0);
        }
    }
}
