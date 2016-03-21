
package com.hcling.pegasusgame;

import gameElements.BaseCharacter;
import gameElements.CygnusHyoga;
import gameElements.Move;
import gameElements.MoveGenerator;
import gameElements.PegasusSeiya;
import gameElements.Skill;

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

    private TextView mResultText, mWinCntAText, mWinCntBText;

    private Button mSimulateGameButton, mSimulateRoundButton, mResetButton;

    private ScrollView mScrollView;

    private boolean mGameOver = true;

    private BaseCharacter mCharA, mCharB;

    private int mRound, mWinCntA, mWinCntB;

    private double mRandomNum;

    private Random mRnd;

    private String mGameLog;

    private MoveGenerator mMoveGenA, mMoveGenB;

    private Move mMoveA, mMoveB;

    private ToggleButton mGatBnA, mDefBnA, mWearBnA, mWeapBnA, mAttBnA;

    private ToggleButton mGatBnB, mDefBnB, mWearBnB, mWeapBnB, mAttBnB;

    private List<ToggleButton> mBnListA = new ArrayList<ToggleButton>();
    private List<ToggleButton> mBnListB = new ArrayList<ToggleButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultText = (TextView) findViewById(R.id.resultText);
        mResultText.setMovementMethod(new ScrollingMovementMethod());
        mScrollView = (ScrollView) findViewById(R.id.scroller1);

        mWinCntAText = (TextView) findViewById(R.id.winCntAText);
        mWinCntBText = (TextView) findViewById(R.id.winCntBText);

        mSimulateGameButton = (Button) findViewById(R.id.button1);
        mSimulateRoundButton = (Button) findViewById(R.id.button2);
        mResetButton = (Button) findViewById(R.id.resetButton);

        mGatBnA = (ToggleButton) findViewById(R.id.gatherButtonP1);
        mDefBnA = (ToggleButton) findViewById(R.id.defendButtonP1);
        mWearBnA = (ToggleButton) findViewById(R.id.armorButtonP1);
        mWeapBnA = (ToggleButton) findViewById(R.id.weaponButtonP1);
        mAttBnA = (ToggleButton) findViewById(R.id.attackButtonP1);

        mGatBnB = (ToggleButton) findViewById(R.id.gatherButtonP2);
        mDefBnB = (ToggleButton) findViewById(R.id.defendButtonP2);
        mWearBnB = (ToggleButton) findViewById(R.id.armorButtonP2);
        mWeapBnB = (ToggleButton) findViewById(R.id.weaponButtonP2);
        mAttBnB = (ToggleButton) findViewById(R.id.attackButtonP2);

        mBnListA.add(mGatBnA);
        mBnListA.add(mDefBnA);
        mBnListA.add(mWearBnA);
        mBnListA.add(mWeapBnA);
        mBnListA.add(mAttBnA);

        mBnListB.add(mGatBnB);
        mBnListB.add(mDefBnB);
        mBnListB.add(mWearBnB);
        mBnListB.add(mWeapBnB);
        mBnListB.add(mAttBnB);

        setupGame();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mWinCntA = 0;
        mWinCntB = 0;
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

                List<Skill> skillsA = mCharA.getAvailableSkills();
                List<Skill> skillsB = mCharB.getAvailableSkills();
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
                                if (skillsA.size() == 0) {
                                    mMoveA = mCharA.gather();
                                } else {
                                    mMoveA = mCharA.attack(skillsA.get(0));
                                }
                                break;
                            case 4:
                                if (skillsA.size() == 0) {
                                    mMoveA = mCharA.gather();
                                } else {
                                    mMoveA = mCharA.attack(skillsA.get(skillsA.size()-1));
                                }
                                break;
                        }
                    }
                }

                for(int i = 0; i < mBnListB.size() && mMoveB == null; i++) {
                    if (mBnListB.get(i).isChecked()) {
                        switch (i) {
                            case 0:
                                mMoveB = mCharB.gather();
                                break;
                            case 1:
                                mMoveB = mCharB.defense();
                                break;
                            case 2:
                                mMoveB = mCharB.wearArmor();
                                break;
                            case 3:
                                if (skillsB.size() == 0) {
                                    mMoveB = mCharB.gather();
                                } else {
                                    mMoveB = mCharB.attack(skillsB.get(0));
                                }
                                break;
                            case 4:
                                if (skillsB.size() == 0) {
                                    mMoveB = mCharB.gather();
                                } else {
                                    mMoveB = mCharB.attack(skillsB.get(skillsB.size()-1));
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

    public void onToggleClickedP1(View view) {
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

    public void onToggleClickedP2(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            for(int i=0;i<mBnListB.size();i++) {
                mBnListB.get(i).setEnabled(false);
            }
            ((ToggleButton) view).setEnabled(true);
        } else {
            for(int i=0;i<mBnListB.size();i++) {
                mBnListB.get(i).setEnabled(true);
            }
        }
    }

    public void onResetClicked(View view) {
        mResultText.setText("");
        mWinCntA = 0;
        mWinCntB = 0;
        setWinCount();
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
        setWinCount();

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
        setWinCount();
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
        // TODO: better way to display analyzed move results
        mCharA.clearLog();
        mCharB.clearLog();
        mGameOver = Move.Analyze(mMoveA, mMoveB);
        mGameLog += mMoveA.moveLog + mMoveB.moveLog;
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
                mWinCntA++;
            } else {
                Log.d(TAG, "Character: " + mCharB.getName()
                        + " wins!");
                mGameLog += "Character: " + mCharB.getName() + " wins!" + "\n";
                mWinCntB++;
            }
            Log.d(TAG, "Game Over!");
            setWinCount();
        } else {
            // TODO: end round
            Log.d(TAG, mCharA.getName() + " " + mCharA.getStatus());
            Log.d(TAG, mCharB.getName() + " " + mCharB.getStatus());
            Log.d(TAG, "----------------------------------------------");
            mGameLog += mCharA.getName() + " " + mCharA.getStatus() + "\n" + mCharB.getName() + " " + mCharB.getStatus() + "\n" + "-------------------\n";
            mRound++;
        }
    }

    private void setWinCount() {
        Log.d(TAG, "Set win count. A: " + mWinCntA + " B: " + mWinCntB);
        mWinCntAText.setText(mCharA.getName() + " " + mWinCntA);
        mWinCntBText.setText(mWinCntB + " " + mCharB.getName());
    }
}

