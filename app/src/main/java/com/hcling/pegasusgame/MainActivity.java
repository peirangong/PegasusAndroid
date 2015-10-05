
package com.hcling.pegasusgame;

import gameElements.BaseCharacter;
import gameElements.CygnusHyoga;
import gameElements.Move;
import gameElements.MoveGenerator;
import gameElements.PegasusSeiya;
import gameElements.Skill;
import gameElements.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    private static final String TAG = "PegasusGame";

    private TextView mResultText;

    private Button mSimulateGameButton, mSimulateRoundButton;

    private ScrollView mScrollView;

    private boolean mGameOver = true;

    private BaseCharacter mCharA, mCharB;

    private int mRound;

    private double mRandomNum;

    private Random mRnd;

    private String mGameLog;

    private MoveGenerator mMoveGenA, mMoveGenB;

    private Move mMoveA, mMoveB;

    private ToggleButton mGatBnA, mDefBnA, mWearBnA, mWeapBnA, mAttBnA;

    private List<ToggleButton> mBnListA = new ArrayList<ToggleButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultText = (TextView) findViewById(R.id.resultText);
        mResultText.setMovementMethod(new ScrollingMovementMethod());
        mScrollView = (ScrollView) findViewById(R.id.scroller1);

        mSimulateGameButton = (Button) findViewById(R.id.button1);
        mSimulateRoundButton = (Button) findViewById(R.id.button2);

        mGatBnA = (ToggleButton) findViewById(R.id.toggleButton1);
        mDefBnA = (ToggleButton) findViewById(R.id.toggleButton2);
        mWearBnA = (ToggleButton) findViewById(R.id.toggleButton3);
        mWeapBnA = (ToggleButton) findViewById(R.id.toggleButton4);
        mAttBnA = (ToggleButton) findViewById(R.id.toggleButton5);

        mBnListA.add(mGatBnA);
        mBnListA.add(mDefBnA);
        mBnListA.add(mWearBnA);
        mBnListA.add(mWeapBnA);
        mBnListA.add(mAttBnA);

        setupGame();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSimulateGameButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mGameOver) {
                    setupGame();
                }
                playWholeGame();

                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });

        mSimulateRoundButton.setOnClickListener(new OnClickListener() {
            // TODO: simulate single round
            @Override
            public void onClick(View v) {
                if (mGameOver) {
                    setupGame();
                }

                List<Skill> skills = mCharA.getAvailableSkills();
                for(int i = 0; i < mBnListA.size() && mMoveA == null; i++) {
                    if (mBnListA.get(i).isChecked()) {
                        switch (i) {
                            case 0:
                                mMoveA = mCharA.gather();
                                break;
                            case 1:
                                mMoveA = mCharA.defense();
                                break;
                            case 2:
                                mMoveA = mCharA.wearArmor();
                                break;
                            case 3:
                                if (skills.size() == 0) {
                                    mMoveA = mCharA.gather();
                                } else {
                                    mMoveA = mCharA.attack(skills.get(0));
                                }
                                break;
                            case 4:
                                if (skills.size() == 0) {
                                    mMoveA = mCharA.gather();
                                } else {
                                    mMoveA = mCharA.attack(skills.get(skills.size()-1));
                                }
                                break;
                        }
                    }
                }
                playOneRound();
                mResultText.setText(mGameLog);

                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            for(int i=0;i<mBnListA.size();i++) {
                mBnListA.get(i).setEnabled(false);
            }
            ((ToggleButton) view).setEnabled(true);
        } else {
            for(int i=0;i<mBnListA.size();i++) {
                mBnListA.get(i).setEnabled(true);
            }
        }
    }

    private void setupGame() {
        Log.d(TAG, "------- New Game ------");
        mCharA = new PegasusSeiya();
        mCharB = new CygnusHyoga();
        mGameOver = false;
        mResultText.setText("");
        mRound = 1;
        mRnd = new Random();
        mGameLog = "";
        mMoveGenA = new MoveGenerator(mCharA);
        mMoveGenB = new MoveGenerator(mCharB);

        mCharA.gather();
        mCharA.wearArmor();
        mCharB.gather();
        mCharB.wearArmor();
        mGameLog += mCharA.fightLog + mCharB.fightLog + "-------------------\n";
        mCharA.clearLog();
        mCharB.clearLog();
    }

    /** Returns true if player A wins */
    private void playWholeGame() {
        mRandomNum = mRnd.nextDouble();

        Log.d(TAG, "\n");
        while (!mGameOver) {
            playOneRound();
        }
        mResultText.setText(mGameLog);
    }

    private void playOneRound() {
        Log.d(TAG, "Round " + mRound + " begins!");
        mGameLog += "Round " + mRound + " begins!" + "\n";
        // TODO: player 1
        if (mMoveA == null) {
            mRandomNum = mRnd.nextDouble();
            mMoveA = mMoveGenA.generateMove_1(mRandomNum);
        }

        // TODO: player 2
        if (mMoveB == null) {
            mRandomNum = mRnd.nextDouble();
            mMoveB = mMoveGenB.generateMove_2(mRandomNum);
        }

        // TODO: analyze result

        mGameOver = Move.Analyze(mMoveA, mMoveB);
        mGameLog += mCharA.fightLog + mCharB.fightLog;
        mCharA.clearLog();
        mCharB.clearLog();
        mMoveA = null;
        mMoveB = null;
        if (mGameOver) {
            if (mCharA.isAlive()) {
                Log.d(TAG, "Character: " + mCharA.getName()
                        + " wins!");
                mGameLog += "Character: " + mCharA.getName() + " wins!" + "\n";
            } else {
                Log.d(TAG, "Character: " + mCharB.getName()
                        + " wins!");
                mGameLog += "Character: " + mCharB.getName() + " wins!" + "\n";
            }
            Log.d(TAG, "Game Over!");
        } else {
            // TODO: end round
            Log.d(TAG, mCharA.getName() + " " + mCharA.getStatus());
            Log.d(TAG, mCharB.getName() + " " + mCharB.getStatus());
            Log.d(TAG, "----------------------------------------------");
            mGameLog += mCharA.getName() + " " + mCharA.getStatus() + "\n" + mCharB.getName() + " " + mCharB.getStatus() + "\n" + "-------------------\n";
            mRound++;
        }
    }
}
