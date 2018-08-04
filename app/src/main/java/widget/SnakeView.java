package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import configs.Config;

public class SnakeView extends View {

    private int color = 0;
    private int widthAndHeight = 0;
    private Paint mPaint = null;
    private Rect mRect = null;

    public SnakeView(Context context) {
        this(context, null);
    }

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        color = Config.getOneColor();
        widthAndHeight = Config.SnakeViewSize;
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mRect = new Rect();
        mRect.set(0, 0, widthAndHeight, widthAndHeight);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthAndHeight, widthAndHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRect, mPaint);

    }
}
