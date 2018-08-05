package utils;

import android.view.ViewGroup;

import java.util.ArrayList;

import widget.SnakeBodyView;
import widget.SnakeView;

public class DirectionUtil {
    public static final int UpDirection = 1;
    public static final int DownDirection = 2;
    public static final int LeftDirection = 3;
    public static final int RightDirection = 4;

    public int mCurrentDirection;

    public DirectionUtil(int direction) {
        mCurrentDirection = direction;
    }


    public boolean setNextDirection(int direction) {
        LogUtil.d("setNextDirection,mCurrentDirection:" + mCurrentDirection + ", direction:" + direction);
        boolean result = true;
        switch (direction) {
            case DirectionUtil.UpDirection:
                if (mCurrentDirection == getReverseDirection(direction)) {
                    result = false;
                } else {
                    mCurrentDirection = direction;
                }
                break;
            case DirectionUtil.DownDirection:
                if (mCurrentDirection == getReverseDirection(direction)) {
                    result = false;
                } else {
                    mCurrentDirection = direction;
                }
                break;
            case DirectionUtil.LeftDirection:
                if (mCurrentDirection == getReverseDirection(direction)) {
                    result = false;
                } else {
                    mCurrentDirection = direction;
                }
                break;
            case DirectionUtil.RightDirection:
                if (mCurrentDirection == getReverseDirection(direction)) {
                    result = false;
                } else {
                    mCurrentDirection = direction;
                }
                break;
        }
        LogUtil.d("setNextDirection,result:" + result);
        return result;
    }

    private static int getReverseDirection(int direction) {
        switch (direction) {
            case DirectionUtil.UpDirection:
                return DirectionUtil.DownDirection;
            case DirectionUtil.DownDirection:
                return DirectionUtil.UpDirection;
            case DirectionUtil.LeftDirection:
                return DirectionUtil.RightDirection;
            case DirectionUtil.RightDirection:
                return DirectionUtil.LeftDirection;
        }
        return -1;
    }

    public void setNextState(SnakeBodyView body, SnakeView headSnakeView, GameStateutil stateUtil, int xCount, int yCount, ArrayList<SnakeView> foodList) {
        int preX = headSnakeView.mX;
        int preY = headSnakeView.mY;
        int currentX = 0;
        int currentY = 0;

        int x = headSnakeView.mX;
        int y = headSnakeView.mY;
        switch (mCurrentDirection) {
            case DirectionUtil.UpDirection:
                y = y - 1;
                break;
            case DirectionUtil.DownDirection:
                y = y + 1;
                break;
            case DirectionUtil.LeftDirection:
                x = x - 1;
                break;
            case DirectionUtil.RightDirection:
                x = x + 1;
                break;
        }


        boolean isDeadState = false;
        switch (mCurrentDirection) {
            case UpDirection:
                if (y < 0) isDeadState = true;
                break;
            case DownDirection:
                if (y > yCount) isDeadState = true;
                break;
            case LeftDirection:
                if (x < 0) isDeadState = true;
                break;
            case RightDirection:
                if (x > xCount) isDeadState = true;
                break;

        }
        /*Hit the wall*/
        if (isDeadState) {
            stateUtil.setCurrentState(GameStateutil.DeadState);
            return;
        }

        SnakeView foodView = checkFoodAhead(foodList, x, y);

        headSnakeView.setXY(x, y);
        if(foodView!=null){
            foodList.remove(foodView);
            foodView.setmSnakeState(SnakeView.STATE_SNAKE);

            SnakeView temp = headSnakeView.nextSnakeView;
            headSnakeView.nextSnakeView = foodView;
            foodView.nextSnakeView = temp;
            foodView.setXY(preX,preY);

        }else{
            SnakeView mTempSnake = headSnakeView.nextSnakeView;
            while (mTempSnake != null) {
                currentX = mTempSnake.mX;
                currentY = mTempSnake.mY;
                mTempSnake.setXY(preX, preY);
                preX = currentX;
                preY = currentY;
                mTempSnake = mTempSnake.nextSnakeView;
            }
        }

        if (foodView != null) {
            body.addAFood();
        }
        body.requestLayout();
    }

    private SnakeView checkFoodAhead(ArrayList<SnakeView> foodList, int x, int y) {
        int foodSize = foodList.size();
        for (SnakeView snakeView : foodList) {
            if (snakeView.mX == x && snakeView.mY == y) {
                return snakeView;
            }
        }
        return null;
    }

}
