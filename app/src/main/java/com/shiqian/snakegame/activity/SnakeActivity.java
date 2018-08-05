package com.shiqian.snakegame.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shiqian.snakegame.R;

import configs.Config;
import utils.DirectionUtil;
import widget.SnakeBodyView;

public class SnakeActivity extends Activity implements View.OnClickListener, SnakeBodyView.OnScoreChangeListener {

    private SnakeBodyView snake;
    private TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        findViewById(R.id.main_btn_left).setOnClickListener(this);
        findViewById(R.id.main_btn_top).setOnClickListener(this);
        findViewById(R.id.main_btn_right).setOnClickListener(this);
        findViewById(R.id.main_btn_bottom).setOnClickListener(this);

        findViewById(R.id.main_btn_start).setOnClickListener(this);
        findViewById(R.id.main_btn_pause).setOnClickListener(this);
        findViewById(R.id.main_btn_replay).setOnClickListener(this);

        findViewById(R.id.main_btn_speedup).setOnClickListener(this);
        findViewById(R.id.main_btn_speeddown).setOnClickListener(this);

        snake = findViewById(R.id.snake);
        snake.setOnScoreChangeListener(this);
        tvScore = findViewById(R.id.main_tv_score);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_left:
                snake.setDirection(DirectionUtil.LeftDirection);
                break;
            case R.id.main_btn_top:
                snake.setDirection(DirectionUtil.UpDirection);
                break;
            case R.id.main_btn_right:
                snake.setDirection(DirectionUtil.RightDirection);
                break;
            case R.id.main_btn_bottom:
                snake.setDirection(DirectionUtil.DownDirection);
                break;

            case R.id.main_btn_start:
                snake.requestStart();
                break;
            case R.id.main_btn_pause:
                snake.requestPause();
                break;
            case R.id.main_btn_replay:
                snake.requestReplay();
                break;

            case R.id.main_btn_speedup:
                Config.speedUp();
                break;
            case R.id.main_btn_speeddown:
                Config.speedDown();
                break;

        }
    }

    @Override
    public void OnScoreChanged(int score) {
        tvScore.setText(score+"分");
    }
}
