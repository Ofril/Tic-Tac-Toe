package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isXPlayerTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createButtonsListeners();
    }

        private void createButtonsListeners() {
            for (int i = 1; i <= 9; i++) {
                String buttonID = "button" + i;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                Button btn = findViewById(resID);
                btn.setOnClickListener(this::playTurn);
            }
        }

        public void playTurn(View view) {
            ((Button)view).setBackground(AppCompatResources.getDrawable(this, isXPlayerTurn ? R.drawable.x : R.drawable.o));
            this.isXPlayerTurn = !this.isXPlayerTurn;
    }

    @Override
    public void onClick(View v) {
        this.playTurn(v);
    }
}