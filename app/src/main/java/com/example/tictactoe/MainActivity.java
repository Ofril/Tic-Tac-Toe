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
        for (int i = 1; i <= 9; i++) {
            Button btn = getButton(i);
            btn.setOnClickListener(this::playTurn);
        }
    }

    private Button getButton(int i) {
        String buttonID = "button" + i;
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        return findViewById(resID);
    }

    private void playTurn(View view) {
        applyPlayerMoveButton((Button) view);
        changeTurns();
        checkForWin();
    }

    private void applyPlayerMoveButton(Button view) {
        view.setBackgroundResource(isXPlayerTurn ? R.drawable.x : R.drawable.o);
        view.setText(isXPlayerTurn ? R.string.x : R.string.o);
        view.setTextColor(getResources().getColor(R.color.transparent, getTheme()));
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
                setScoreBar(getButton(winOption[0]).getText().equals(
                        getResources().getString(R.string.x)) ? R.drawable.xwin : R.drawable.owin);
                findViewById(R.id.buttonplay).setVisibility(View.VISIBLE);

            }
        }
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