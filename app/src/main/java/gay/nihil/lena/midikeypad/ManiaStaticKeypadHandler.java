package gay.nihil.lena.midikeypad;

import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Objects;

public class ManiaStaticKeypadHandler extends KeypadHandler {
    public ManiaStaticKeypadHandler(View view) {
        super(view);
    }

    // stores pairs of: action index -> code
    // this allows pointer up events to release a pointer's original key,
    // preventing a situation where the finger slides to a different key and a wrong code would be fired
    private static final HashMap<Integer, Integer> motions = new HashMap<>();

    @Override
    public KeypadData HandleMotionEvent(MotionEvent event) {
        // https://android-developers.googleblog.com/2010/06/making-sense-of-multitouch.html
        final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        final int pointerId = event.getPointerId(pointerIndex);
        int actionType = event.getActionMasked();

        if (actionType == MotionEvent.ACTION_DOWN || actionType == MotionEvent.ACTION_POINTER_DOWN) {
            float x = event.getX(pointerIndex);
            float width = view.getWidth();
            float keyWidth = width / 4;

            int code = 60 + (int) (x / keyWidth);
            motions.put(pointerId, code);

            return new KeypadData(true, true, code);
        } else if (actionType == MotionEvent.ACTION_UP || actionType == MotionEvent.ACTION_POINTER_UP) {
            Integer code = Objects.requireNonNull(motions.get(pointerId));
            motions.remove(pointerId);
            return new KeypadData(true, false, code);
        } else {
            return new KeypadData(false, false, 0);
        }
    }
}
