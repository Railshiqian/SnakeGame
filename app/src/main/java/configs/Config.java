package configs;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

import utils.DimenUtil;
import utils.LogUtil;

public class Config {

    /*is debugable*/
    public static boolean isDebugable = true;

    public static Context mApplication;

    /*dimen dp*/
    public static int SnakeWidthAndHeight = 30;
    /*dimen px*/
    public static int mSnakeViewSize;

    /*init the body count*/
    public static final int mInitSnakeCount = 3;
    public static int foodCount = 6;

    /*1000ms跳一次*/
    public static int StartSpeep = 500;
    public static int mSnakeSpeep = StartSpeep;
    public static int mSpeedPercent = 250;
    public static int miniSpeed = 250;
    public static int maxSpeed = 2000;

    /*all of snake body colors*/
    public static ArrayList<Integer> mColorList = null;
    private static int mColorCount = 0;

    public static Random mRandom;

    public static int getOneColor() {
        int pos = mRandom.nextInt(mColorCount);
        LogUtil.d("color:" + pos);
        return mColorList.get(pos);
    }

    public static int getHeadColor() {
        return Color.RED;
    }

    public static int getBodyColor() {
        return getOneColor();
    }

    public static int getTailColor() {
        return Color.BLACK;
    }


    /*used in application*/
    public static void init(Context application) {
        mApplication = application;
        LogUtil.d("init()");
        initColor();
        mSnakeViewSize = DimenUtil.dp2px(mApplication, SnakeWidthAndHeight);
    }

    /*Set your own color collection*/
    public static void setColor(ArrayList<Integer> colorList) {
        if (colorList == null || colorList.size() == 0) {
            throw new RuntimeException("must init colorList first and add at least on color before setColor!!!");
        }
        mColorList = colorList;
    }

    public static void initColor() {
        if (mColorList == null) {
            mColorList = new ArrayList<>();
            mColorList.add(Color.BLUE);
            mColorList.add(Color.YELLOW);
            mColorList.add(Color.GREEN);
            mColorList.add(Color.GRAY);
            mColorList.add(Color.CYAN);
            mColorList.add(Color.MAGENTA);

        }
        mColorCount = mColorList.size();
        mRandom = new Random();
    }

    public static boolean speedUp() {
        int temp = mSnakeSpeep - mSpeedPercent;
        if (temp >= miniSpeed) {
            mSnakeSpeep = temp;
            return true;
        } else {
            return false;
        }
    }

    public static boolean speedDown() {

        int temp = mSnakeSpeep + mSpeedPercent;
        if (temp <= maxSpeed) {
            mSnakeSpeep = temp;
            return true;
        } else {
            return false;
        }

    }

    public static void resetSpeed() {
        mSnakeSpeep = StartSpeep;
    }


}
