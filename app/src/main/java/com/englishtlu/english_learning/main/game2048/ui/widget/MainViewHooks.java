package com.englishtlu.english_learning.main.game2048.ui.widget;

public interface MainViewHooks {

    void onScoreChanged(long score);
    void onHighScoreChanged(long highScore);
    void gameLost();
    void gameWon();

    void endlessModeEntered();
    void onNewGame();
    void onGameReverted();

}
