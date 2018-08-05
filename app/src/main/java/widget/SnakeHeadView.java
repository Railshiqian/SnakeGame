package widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.shiqian.snakegame.R;

import configs.Config;
import utils.DirectionUtil;

public class SnakeHeadView extends SnakeView {

    private int mDirection;
    private Bitmap upImage;
    private Matrix matrix;
    private int centerX;
    private int centerY;


    public SnakeHeadView(Context context, int part) {
        super(context, part);
        setDiretion(DirectionUtil.RightDirection);
        centerX = Config.mSnakeViewSize / 2;
        centerY = centerX;

        upImage = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.snake_head_up);
        upImage = Bitmap.createScaledBitmap(upImage, Config.mSnakeViewSize, Config.mSnakeViewSize, true);

    }

    public void setDiretion(int direction) {
        mDirection = direction;
        matrix = new Matrix();

        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        switch (mDirection) {
            case DirectionUtil.LeftDirection:
                matrix.setRotate(270, centerX, centerY);
                break;
            case DirectionUtil.UpDirection:
                matrix.setRotate(0, centerX, centerY);
                break;
            case DirectionUtil.RightDirection:
                matrix.setRotate(90, centerX, centerY);
                break;
            case DirectionUtil.DownDirection:
                matrix.setRotate(180, centerX, centerY);
                break;
        }
        canvas.drawBitmap(upImage, matrix, mPaint);
        canvas.restore();

    }

}
