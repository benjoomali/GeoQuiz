package com.example.benjoomali.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.benjoomali.geoquiz.answer_is_true";
    //prevent name collisions by using package name
    private static final String EXTRA_ANSWER_SHOWN = "com.example.benjoomali.geoquiz.answer_shown";
    //constant for extra's key

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private TextView mShowApiLevel;
    private String mApiLevel;
    private Button mShowAnswer;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    //method to help decode extra for QuizActivity
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);

                //this checks to see if the build version is lollipop, otherwise this animation does not exist
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //circular animation that hides the button
                int cx = mShowAnswer.getWidth() / 2;
                int cy = mShowAnswer.getHeight() / 2;
                float radius = mShowAnswer.getWidth();
                //create an animator with createCircularReveal
                Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                //listener tells when the animation is complete
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mAnswerTextView.setVisibility(View.VISIBLE);
                        mShowAnswer.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
            } else {
                mAnswerTextView.setVisibility(View.VISIBLE);
                mShowAnswer.setVisibility(View.INVISIBLE);
            }
            }
        });

        mApiLevel = ("API level is " + Build.VERSION.SDK_INT);
        mShowApiLevel = (TextView) findViewById(R.id.show_api_level);
        mShowApiLevel.setText(mApiLevel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

}
