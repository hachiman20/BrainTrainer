package com.example.braintrainerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0, val, operand1, operand2, ans, operator, incorrectAnswer;
    TextView resultTextView;
    TextView scoreTextView;
    TextView sumTextView;
    TextView timerTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    int gameStatus = 0;
    CountDownTimer myCountdowntimer = new CountDownTimer(500000, 1000){

        public void onTick(long millisecondsUntilDone) {
        }

        public void onFinish() {
        }
    }.start();


    public void generateQuestion() {
            Random rand = new Random();

            operator = rand.nextInt(4);
            String sign;

            if (operator == 0)
                sign = " + ";
            else if (operator == 1)
                sign = " - ";
            else if (operator == 2)
                sign = " x ";
            else
                sign = " / ";


            if (operator == 0 || operator == 1) {
                val = 41;

                operand1 = rand.nextInt(val);
                operand2 = rand.nextInt(val);

                if (operator == 0)
                    ans = operand1 + operand2;
                else
                    ans = operand1 - operand2;
            }

            if (operator == 2 || operator == 3) {
                val = 13;

                operand1 = rand.nextInt(val);
                operand2 = rand.nextInt(val);

                if (operator == 3)
                    operand1 = operand2 * operand1;

                if (operator == 2)
                    ans = operand1 * operand2;
                else
                    ans = operand1 / operand2;
            }

            String disp = Integer.toString(operand1) + sign + Integer.toString(operand2);
            if (operand1 < 10 && operand2 < 10)
                disp = " " + disp;
            sumTextView.setText(disp);

            answers.clear();

            locationOfCorrectAnswer = rand.nextInt(4);

            for (int i = 0; i < 4; i++) {

                if (i == locationOfCorrectAnswer)
                    answers.add(ans);

                else {
                    if (operator == 0) {
                        incorrectAnswer = rand.nextInt(81);
                        while (incorrectAnswer == ans)
                            incorrectAnswer = rand.nextInt(81);
                        answers.add(incorrectAnswer);
                    } else if (operator == 1) {
                        incorrectAnswer = rand.nextInt(30);
                        while (incorrectAnswer == Math.abs(ans))
                            incorrectAnswer = rand.nextInt(30);
                        int s = rand.nextInt(2);
                        if (s == 1)
                            incorrectAnswer = (-1) * incorrectAnswer;
                        answers.add(incorrectAnswer);
                    } else if (operator == 2) {
                        incorrectAnswer = rand.nextInt(226);
                        while (incorrectAnswer == ans)
                            incorrectAnswer = rand.nextInt(226);
                        answers.add(incorrectAnswer);
                    } else {
                        incorrectAnswer = rand.nextInt(13);
                        while (incorrectAnswer == ans)
                            incorrectAnswer = rand.nextInt(13);
                        answers.add(incorrectAnswer);
                    }
                }

            }

            button0.setText(Integer.toString(answers.get(0)));
            button1.setText(Integer.toString(answers.get(1)));
            button2.setText(Integer.toString(answers.get(2)));
            button3.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer (View view)
    {
        try {
            if (gameStatus == 1) {
                if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
                    score++;
                    String str = Integer.toString(score);
                    if (score < 10)
                        str = "0" + str;
                    scoreTextView.setText(str);
                    resultTextView.setText("Correct!");
                    timerClock();
                } else {
                    myCountdowntimer.cancel();
                    resultTextView.setText("Wrong!");
                    gameStatus=0;
                    new CountDownTimer(1000,1000){
                        public void onTick(long millisecondsUntilDone) {
                        }
                        public void onFinish() {
                            timerTextView.setText("00s");
                            resultTextView.setText("Your Final Score is : " + Integer.toString(score));
                            startButton.setText("Play Again");
                            startButton.setVisibility(View.VISIBLE);
                        }
                    }.start();
                }
                generateQuestion();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void start (View view)
    {
        startButton.setVisibility(View.INVISIBLE);
        gameStatus = 1;
        timerClock();
        score=0;
        scoreTextView.setText("00");
    }

    public void timerClock()
    {
        try {
            myCountdowntimer.cancel();

            myCountdowntimer = new CountDownTimer(5000, 1000) {
                public void onTick(long millisecondsUntilDone) {
                    timerTextView.setText("0" + String.valueOf(millisecondsUntilDone / 1000) + "s");
                }

                public void onFinish() {
                    timerTextView.setText("00s");
                    resultTextView.setText("Your Final Score is : " + Integer.toString(score));
                    gameStatus = 0;
                    startButton.setText("Play Again");
                    startButton.setVisibility(View.VISIBLE);
                }
            }.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        generateQuestion();

    }
}