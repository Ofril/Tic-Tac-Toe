package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isXPlayerTurn = true;

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
            applyPlayerMoveButton((Button) view);
            changeTurns();
            checkForWin();
        }
    }

    private void restartGame(View replayButton) {
        resetToolbars(replayButton);
        resetButtons();
    }

    private void resetButtons() {
        for (int i = 1; i <= 9; i++) {
            Button btn = getButton(i);
            btn.setBackgroundResource(R.color.transparent);
            btn.setText("");
            btn.setClickable(true);
        }
    }

    private void resetToolbars(View replayButton) {
        replayButton.setVisibility(View.INVISIBLE);
        isXPlayerTurn = true;
        setScoreBar(R.drawable.xplay);
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
        int[][] winningPositions = {{1,2,3},{4,5,6},{7,8,9},{1,4,7},{2,5,8},{3,6,9},{1,5,9},{3,5,7}};
        for (int[] winOption: winningPositions) {
            if (isWin(winOption)) {
                handleWin(winOption);
            }
        }
    }

    private void handleWin(int[] winPosition) {
        setScoreBar(getButton(winPosition[0]).getText().equals(
                getResources().getString(R.string.x)) ? R.drawable.xwin : R.drawable.owin);
        findViewById(R.id.buttonplay).setVisibility(View.VISIBLE);
        disableButtonsClick();
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