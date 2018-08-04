package widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class SnakeBodyView extends ViewGroup {

    private Context mContext = null;
    private int mWidth;
    private int mHeight;

    public SnakeBodyView(Context context) {
        this(context, null);
    }

    public SnakeBodyView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public SnakeBodyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public LayoutParams getLayoutParams() {
        return super.getLayoutParams();
    }

    private class DirectionUtil {
        public int upDirection = 1;
        public int downDirection = 1;
        public int leftDirection = 1;
        public int rightDirection = 1;

        public int currentDirection = rightDirection;

    }

}
