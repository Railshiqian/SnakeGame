package widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.HashMap;

import configs.Config;
import utils.DirectionUtil;
import utils.GameStateutil;
import utils.LogUtil;

public class SnakeBodyView extends ViewGroup {

    private Context mContext = null;
    private int mWidth;
    private int mHeight;
    private int mWidthCount = 0;
    private int mHeightCount = 0;
    private int count = Config.mInitSnakeCount;
    private SnakeHeadView mHeadSnakeView;
    private SnakeView mTempSnake = null;

    private GameStateutil mGameStateutil;
    private DirectionUtil mDirectionUtil;

    private ArrayList<SnakeView> mAllList;
    private ArrayList<SnakeView> mFoodList;
    private int totalCount = 0;

    private boolean isInited = false;

    /*when eat a food,notify listener*/
    private OnScoreChangeListener mOnScoreChangeListener;

    public SnakeBodyView(Context context) {
        this(context, null);
    }

    public SnakeBodyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnakeBodyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mDirectionUtil = new DirectionUtil(DirectionUtil.RightDirection);
        mGameStateutil = new GameStateutil(GameStateutil.ReadyState, onGameStateChangedListener);
        initOneSnakeBody();
    }


    public void setDirection(int direction) {
        boolean result = mDirectionUtil.setNextDirection(direction);
        if (result) {
            LogUtil.d("mHeadSnakeView.setDiretion:" + direction);
            mHeadSnakeView.setDiretion(direction);
        }
    }

    public void requestStart() {
        mGameStateutil.setCurrentState(GameStateutil.RunningState);
    }

    public void requestPause() {
        mGameStateutil.setCurrentState(GameStateutil.PauseState);
    }

    public void requestReplay() {
        mGameStateutil.setCurrentState(GameStateutil.ReplayState);
    }


    private void initAllList() {
        totalCount = mWidthCount * mHeightCount;
        int least = totalCount - Config.mInitSnakeCount;
        if (mAllList == null) {
            mAllList = new ArrayList<>();
        } else {
            mAllList.clear();
        }

        SnakeView temp = mHeadSnakeView;
        while (temp != null) {
            mAllList.add(temp);
            temp = temp.nextSnakeView;
        }

        for (int i = 0; i < least; i++) {
            SnakeView whiteView = new SnakeView(mContext, SnakeView.PART_BODY);
            whiteView.setmSnakeState(SnakeView.STATE_WHITE);
            mAllList.add(whiteView);
        }
        LogUtil.d("mAllList.size:" + mAllList.size() + ",mWidthCount:" + mWidthCount + ", mHeightCount:" + mHeightCount);
    }

    private void initFood() {
        if (mFoodList == null) {
            mFoodList = new ArrayList<>();
        } else {
            mFoodList.clear();
        }
        SnakeView temp = null;
        for (int j = 0; j < Config.foodCount; j++) {
            int foodPosition = Config.mRandom.nextInt(mAllList.size() - count - mFoodList.size());
            LogUtil.d("mRandom.nextIn,getfoodPosition:" + foodPosition);
            for (int i = 0; i <= foodPosition; i++) {
                temp = mAllList.get(i);
                if (temp.mSnakeState == SnakeView.STATE_SNAKE || temp.mSnakeState == SnakeView.STATE_FOOD) {
                    foodPosition++;
                    LogUtil.d("foodPosition++:" + foodPosition);
                    continue;
                }
                if (i == foodPosition) {
                    /*how to getXY??*/
                    int y = foodPosition / mWidthCount;
                    int x = foodPosition % mWidthCount;
                    LogUtil.d("get food! foodPosition:" + foodPosition + ",x:" + x + ",y:" + y);
                    temp.setXY(x, y);
                    temp.setmSnakeState(SnakeView.STATE_FOOD);
                    mFoodList.add(temp);
                    break;
                }
            }
        }

        int foodSize = mFoodList.size();
        LogUtil.d("init food end,foodSize:" + foodSize);
        for (int i = 0; i < foodSize; i++) {
            addView(mFoodList.get(i));
        }
        requestLayout();
    }

    public void addAFood() {
        SnakeView temp = null;
        int foodPosition = Config.mRandom.nextInt(mAllList.size() - count - mFoodList.size());
        LogUtil.d("mRandom.nextIn,getfoodPosition:" + foodPosition);
        for (int i = 0; i < foodPosition; i++) {
            temp = mAllList.get(i);
            if (temp.mSnakeState == SnakeView.STATE_SNAKE || temp.mSnakeState == SnakeView.STATE_FOOD) {
                foodPosition++;
                LogUtil.d("foodPosition++:" + foodPosition);
                continue;
            }
            if (i == foodPosition - 1) {
                /*how to getXY??*/
                int y = foodPosition / mWidthCount;
                int x = foodPosition % mWidthCount;
                LogUtil.d("get food! foodPosition:" + foodPosition + ",x:" + x + ",y:" + y);
                temp.setXY(x, y);
                temp.setmSnakeState(SnakeView.STATE_FOOD);
                mFoodList.add(temp);
                break;
            }
        }
        count++;
        addView(temp);
        requestLayout();
        mOnScoreChangeListener.OnScoreChanged(count - Config.mInitSnakeCount);
    }

    private void initOneSnakeBody() {
        int temp = Config.mInitSnakeCount - 1;
        for (int i = temp; i >= 0; i--) {
            if (mHeadSnakeView == null) {
                mHeadSnakeView = new SnakeHeadView(mContext, SnakeView.PART_HEAD);
                mHeadSnakeView.setXY(i, 0);
                mTempSnake = mHeadSnakeView;
            } else {
                SnakeView nextSnakeview;
                if (i == 0) {
                    nextSnakeview = new SnakeView(mContext, SnakeView.PART_TAIL);
                } else {
                    nextSnakeview = new SnakeView(mContext, SnakeView.PART_BODY);
                }
                nextSnakeview.setXY(i, 0);
                mTempSnake.nextSnakeView = nextSnakeview;
                mTempSnake = nextSnakeview;
            }
            mTempSnake.setmSnakeState(SnakeView.STATE_SNAKE);
            addView(mTempSnake);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtil.d("onMeasure");
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = MeasureSpec.getSize(MeasureSpec.AT_MOST);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = MeasureSpec.getSize(MeasureSpec.AT_MOST);
        }
        mWidthCount = mWidth / Config.mSnakeViewSize;
        mHeightCount = mHeight / Config.mSnakeViewSize;
        LogUtil.d("mWidth:" + mWidth + ",mHeight:" + mHeight);
        LogUtil.d("mWidthCount:" + mWidthCount + ",mHeightCount:" + mHeightCount);
//        initFood();
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean b, int l, int t, int r, int bottom) {
        LogUtil.d("onLayout");
        mTempSnake = mHeadSnakeView;
        while (mTempSnake != null) {
            mTempSnake.requestLayoutSnakeView();
            mTempSnake = mTempSnake.nextSnakeView;
        }

        /*init food after onMesure*/
        if (!isInited) {
            initAllList();
            initFood();
            isInited = true;
        }

        if (mFoodList != null) {
            int foodCount = mFoodList.size();
            for (int i = 0; i < foodCount; i++) {
                mFoodList.get(i).requestLayoutSnakeView();
            }
        }


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtil.d("snakeBody onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtil.d("snakeBody onDetachedFromWindow");
        mGameStateutil.setCurrentState(GameStateutil.PauseState);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handler.sendEmptyMessageDelayed(1, Config.mSnakeSpeep);
            mDirectionUtil.setNextState(SnakeBodyView.this, mHeadSnakeView, mGameStateutil, mWidthCount, mHeightCount, mFoodList);
            invalidate();
        }
    };

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        LogUtil.d("onWindowVisibilityChanged");
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.d("onConfigurationChanged");
    }

    GameStateutil.onGameStateChangedListener onGameStateChangedListener = new GameStateutil.onGameStateChangedListener() {
        @Override
        public void onGameStateChanged(int state) {
            switch (state) {
                case GameStateutil.ReadyState:
                case GameStateutil.PauseState:
                case GameStateutil.DeadState:
                    handler.removeCallbacksAndMessages(null);
                    break;
                case GameStateutil.RunningState:
                    if (!handler.hasMessages(1)) {
                        handler.sendEmptyMessageDelayed(1, Config.mSnakeSpeep);
                    }
                    break;
                case GameStateutil.ReplayState:
                    replay();
                    break;
            }
        }
    };

    private void replay() {
        handler.removeCallbacksAndMessages(null);

        mHeadSnakeView = null;
        removeAllViews();

        mDirectionUtil.mCurrentDirection = DirectionUtil.RightDirection;
//        mDirectionUtil.setNextDirection(DirectionUtil.RightDirection);
        mGameStateutil.resetState();
        count = Config.mInitSnakeCount;
        initOneSnakeBody();
        initAllList();
        initFood();

        Config.resetSpeed();


//        handler.sendEmptyMessageDelayed(1, Config.mSnakeSpeep);


    }


    public void setOnScoreChangeListener(OnScoreChangeListener listener) {
        mOnScoreChangeListener = listener;
    }

    public interface OnScoreChangeListener {
        public void OnScoreChanged(int score);
    }

}
