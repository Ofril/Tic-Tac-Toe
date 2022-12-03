package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isXPlayerTurn = true;
    private int plays = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createButtonsListeners();
        setScoreBar(isXPlayerTurn ? R.drawable.xplay : R.drawable.oplay);
    }

    private void createButtonsListeners() {
        addPlayListenersToButtons();
        findViewById(R.id.buttonplay).setOnClickListener(this::restartGame);
    }

    private void addPlayListenersToButtons() {
        for (int i = 1; i <= 9; i++) {
            Button btn = getButton(i);
            btn.setOnClickListener(this::playTurn);
        }
    }

    private void disableButtonsClick() {
        for (int i = 1; i <= 9; i++) {
            Button btn = getButton(i);
            btn.setClickable(false);
        }
    }

    private Button getButton(int i) {
        String buttonID = "button" + i;
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        return findViewById(resID);
    }

    private void playTurn(View view) {
        if (((Button)view).getText().equals("")) {
            plays++;
            applyPlayerMoveButton((Button) view);
            changeTurns();
            checkForWin();
        }
    }

    private void restartGame(View replayButton) {
        resetViews(replayButton);
        resetButtons();
        plays = 0;
    }

    private void resetButtons() {
        for (int i = 1; i <= 9; i++) {
            Button btn = getButton(i);
            btn.setBackgroundResource(R.color.transparent);
            btn.setText("");
            btn.setClickable(true);
        }
    }

    private void resetViews(View replayButton) {
        replayButton.setVisibility(View.INVISIBLE);
        isXPlayerTurn = true;
        setScoreBar(R.drawable.xplay);
        setWinningViewBackground(R.color.transparent);
    }

    private void applyPlayerMoveButton(Button button) {
        button.setBackgroundResource(isXPlayerTurn ? R.drawable.x : R.drawable.o);
        button.setText(isXPlayerTurn ? R.string.x : R.string.o);
        button.setTextColor(getResources().getColor(R.color.transparent, getTheme()));
    }

    private void changeTurns() {
        isXPlayerTurn = !isXPlayerTurn;
        setScoreBar(isXPlayerTurn ? R.drawable.xplay : R.drawable.oplay);
    }

    private void setScoreBar(int drawableId) {
        Toolbar scoreBar = findViewById(R.id.scorebar);
        scoreBar.setBackgroundResource(drawableId);
    }

    private void checkForWin() {
        int[][] winningPositions = {{1,2,3, R.drawable.mark3},{4,5,6, R.drawable.mark4},
                {7,8,9, R.drawable.mark5}, {1,4,7, R.drawable.mark6},{2,5,8, R.drawable.mark7},
                {3,6,9, R.drawable.mark8}, {1,5,9, R.drawable.mark1},{3,5,7, R.drawable.mark2}};

        for (int[] winOption: winningPositions) {
            if (isWin(winOption)) {
                handleWin(winOption);
                return;
            }
        }

        checkForTie();
    }

    private void checkForTie() {
        if (plays == 9) {
            setScoreBar(R.drawable.nowin);
            findViewById(R.id.buttonplay).setVisibility(View.VISIBLE);
            disableButtonsClick();
        }
    }

    private void handleWin(int[] winPosition) {
        setScoreBar(getButton(winPosition[0]).getText().equals(
                getResources().getString(R.string.x)) ? R.drawable.xwin : R.drawable.owin);
        findViewById(R.id.buttonplay).setVisibility(View.VISIBLE);
        disableButtonsClick();
        setWinningViewBackground(winPosition[3]);
    }

    private void setWinningViewBackground(int winPosition) {
        View winningView = findViewById(R.id.winning_view);
        winningView.setBackgroundResource(winPosition);
    }

    private boolean isWin(int[] option) {
        return getButton(option[0]).getText().equals(getButton(option[1]).getText()) &&
                getButton(option[1]).getText().equals(getButton(option[2]).getText()) &&
                !getButton(option[0]).getText().equals("");
    }

    @Override
    public void onClick(View view) {
        playTurn(view);
    }
}