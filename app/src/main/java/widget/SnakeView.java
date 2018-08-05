package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import configs.Config;
import utils.LogUtil;

public class SnakeView extends View {

    private int color = 0;
    private int widthAndHeight = 0;
    public Paint mPaint = null;
    private Rect mRect = null;

    public int mX = 0;
    public int mY = 0;
    public int mL;
    public int mR;
    public int mT;
    public int mB;

    public static final int STATE_FOOD = 1;
    public static final int STATE_WHITE = 2;
    public static final int STATE_SNAKE = 3;

    public int mSnakeState = STATE_WHITE;

    public SnakeView nextSnakeView = null;

    public static final int PART_HEAD = 1;
    public static final int PART_BODY = 2;
    public static final int PART_TAIL = 3;

    public int mPart = 0;

    public SnakeView(Context context,int part) {
        super(context);

        mPart = part;
        switch (part){
            case PART_BODY:
                color = Config.getBodyColor();
                break;
            case PART_HEAD:
                color = Config.getHeadColor();
                break;
            case PART_TAIL:
                color = Config.getTailColor();
                break;
        }

        widthAndHeight = Config.mSnakeViewSize;
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mRect = new Rect();
        mRect.set(0, 0, widthAndHeight, widthAndHeight);
//        this(context, null);
    }

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        color = Config.getOneColor();
        widthAndHeight = Config.mSnakeViewSize;
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mRect = new Rect();
        mRect.set(0, 0, widthAndHeight, widthAndHeight);

    }

    public void setmSnakeState(int state){
        mSnakeState = state;
    }

    public void setXY(int x, int y) {
        mX = x;
        mY = y;
        mL = mX * Config.mSnakeViewSize;
        mT = mY * Config.mSnakeViewSize;
        mR = mL + Config.mSnakeViewSize;
        mB = mT + Config.mSnakeViewSize;
        LogUtil.d("l,t,r,b:"+mL+","+mT+","+mR+","+mB);
    }

    public void requestLayoutSnakeView(){
        this.layout(mL,mT,mR,mB);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthAndHeight, widthAndHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mSnakeState==STATE_WHITE){
            LogUtil.d("mSnakeState==WHITE,abandon!");
            /*white do not draw*/
            return;
        }
        canvas.drawRect(mRect, mPaint);
    }

    @Override
    public String toString() {
        return "color:"+color+",x:"+mX+",mY:"+mY;
    }
}
