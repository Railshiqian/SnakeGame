package utils;

public class GameStateutil {


    public static final int ReadyState = 1;
    public static final int RunningState = 2;
    public static final int PauseState = 3;
    public static final int DeadState = 4;
    public static final int ReplayState = 5;

    private onGameStateChangedListener listener;

    public void setOnGameStateChangedListener(onGameStateChangedListener listener) {
        this.listener = listener;
    }

    public int mCurrentState = ReadyState;

    public GameStateutil(int state, onGameStateChangedListener listener) {
        mCurrentState = state;
        this.listener = listener;
    }

    public void resetState() {
        mCurrentState = ReadyState;
    }

    public void setCurrentState(int state) {
        if (mCurrentState == DeadState && state != ReadyState && state != ReplayState) {
            return;
        }

        if (mCurrentState == ReadyState && state != RunningState&& state != ReplayState) {
            return;
        }

        if (mCurrentState == RunningState && state == ReadyState) {
            return;
        }

        mCurrentState = state;
        listener.onGameStateChanged(mCurrentState);
    }

    public interface onGameStateChangedListener {
        public void onGameStateChanged(int state);
    }


}
