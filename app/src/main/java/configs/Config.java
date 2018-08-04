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
    public static int SnakeWidthAndHeight =30;
    /*dimen px*/
    public static int SnakeViewSize;

    /*all of snake body colors*/
    public static ArrayList<Integer> mColorList = null;
    private static int mColorCount = 0;

    private static Random mRandom;

    public static int getOneColor() {
        int pos = mRandom.nextInt(mColorCount);
        LogUtil.d("color pos:"+(pos-1));
        return mColorList.get(pos);
    }

    /*used in application*/
    public static void init(Context application) {
        mApplication = application;
        LogUtil.d("init()");
        initColor();
        SnakeViewSize = DimenUtil.dp2px(mApplication, SnakeWidthAndHeight);
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
            mColorList.add(Color.RED);
            mColorList.add(Color.BLUE);
            mColorList.add(Color.YELLOW);
            mColorList.add(Color.GREEN);
            mColorList.add(Color.GRAY);
        }
        mColorCount = mColorList.size();
        mRandom = new Random();
    }

}
