package com.englishtlu.english_learning.main.game2048.ui.widget;

import static com.englishtlu.english_learning.main.game2048.util.Constants.INITIAL_VELOCITY;
import static com.englishtlu.english_learning.main.game2048.util.Constants.MERGING_ACCELERATION;
import static com.englishtlu.english_learning.main.game2048.util.Util.log2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.game2048.data.AnimationCell;
import com.englishtlu.english_learning.main.game2048.data.Tile;
import com.englishtlu.english_learning.main.game2048.logic.G2048Game;
import com.englishtlu.english_learning.main.game2048.util.InputListener;

import java.util.ArrayList;

public class G2048View extends View {

    private final String TAG = G2048View.class.getName();

    public final int numCellTypes = 21;
    private final BitmapDrawable[] bitmapCell = new BitmapDrawable[numCellTypes];
    public G2048Game game;

    // Internal Variables.
    private final Paint paint = new Paint();
    public boolean hasSaveState = false;
    public boolean continueButtonEnabled = false;
    public int startingX;
    public int startingY;
    public int endingX;
    public int endingY;

    //Misc
    public boolean refreshLastTime = true;
    public boolean showHelp;

    //Timing
    private long lastFPSTime = System.nanoTime();

    //Text
    private float titleTextSize;
    private float bodyTextSize;
    private float headerTextSize;
    private float instructionsTextSize;
    private float gameOverTextSize;

    //Layout variables
    private int cellSize = 0;
    private float textSize = 0;
    private float cellTextSize = 0;
    private int gridWidth = 0;
    private int textPaddingSize;
    private int iconPaddingSize;

    //Assets
    private Drawable backgroundRectangle;
    private Drawable lightUpRectangle;
    private Drawable fadeRectangle;
    private Bitmap background = null;
    private BitmapDrawable loseGameOverlay;
    private BitmapDrawable winGameContinueOverlay;
    private BitmapDrawable winGameFinalOverlay;

    //Hook
    private MainViewHooks mainViewHooks;

    public G2048View(Context context) {
        super(context);
        initViews(context);
    }

    public G2048View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public G2048View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    public void setGame(G2048Game game) {
        this.game = game;
    }

    public void setMainViewHooks(MainViewHooks mainViewHooks) {
        this.mainViewHooks = mainViewHooks;
    }

    public MainViewHooks getMainViewHooks() {
        return mainViewHooks;
    }

    private void initViews(Context context) {
        Resources resources = context.getResources();
        setGame(new G2048Game(context , this));

        try {

            //Getting assets
            backgroundRectangle = ContextCompat.getDrawable(getContext(), R.drawable.background_rectangle);
            lightUpRectangle = ContextCompat.getDrawable(getContext(),R.drawable.light_up_rectangle);
            fadeRectangle = ContextCompat.getDrawable(getContext(),R.drawable.fade_rectangle);

            //todo THIS SHOULD BE DIFFERENT FOR EACH THEME.
            //this.setBackgroundColor(resources.getColor(R.color.background));

            Typeface font = Typeface.createFromAsset(resources.getAssets(), "ClearSans-Bold.ttf");
            paint.setTypeface(font);
            paint.setAntiAlias(true);
        } catch (Exception e) {
            Log.e(TAG, "Error getting assets?", e);
        }

        setOnTouchListener(new InputListener(this));
        game.newGame();

    }

    private void drawDrawable(Canvas canvas, Drawable draw, int startingX, int startingY, int endingX, int endingY) {
        if (draw != null) {
            draw.setBounds(startingX, startingY, endingX, endingY);
            draw.draw(canvas);
        }
    }

    private void drawBackground(Canvas canvas) {
        drawDrawable(canvas, backgroundRectangle, startingX, startingY, endingX, endingY);
    }

    //Renders the set of 16 background squares.
    private void drawBackgroundGrid(Canvas canvas) {
        Resources resources = getResources();
        Drawable backgroundCell = ContextCompat.getDrawable(getContext() , R.drawable.cell_rectangle);
        // Outputting the game grid
        for (int currentColumn = 0; currentColumn < game.numSquaresX; currentColumn++) {
            for (int currentRow = 0; currentRow < game.numSquaresY; currentRow++) {
                int startX = startingX + gridWidth + (cellSize + gridWidth) * currentColumn;
                int endX = startX + cellSize;
                int startY = startingY + gridWidth + (cellSize + gridWidth) * currentRow;
                int endY = startY + cellSize;

                drawDrawable(canvas, backgroundCell, startX, startY, endX, endY);
            }
        }
    }

    //todo: Make it dependent on the luminosity of the background of the cell
    private void drawCellText(Canvas canvas, int value) {
        int textShiftY = centerText();
        Drawable tempBox = ContextCompat.getDrawable(getContext() , getCellRectangleIds()[log2(value)]);

        double lum = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            lum = ColorUtils.calculateLuminance(((GradientDrawable) tempBox).getColor().getDefaultColor());
        }

        if (lum < 0.5) {
            paint.setColor(getResources().getColor(R.color.text_white));
        } else {
            paint.setColor(getResources().getColor(R.color.text_black));
        }

        Log.e("LUM" , "Luminance : " + value + " : " + lum);
        canvas.drawText("" + value, cellSize / 2f, cellSize / 2f - textShiftY, paint);
    }

    private void drawCells(Canvas canvas) {
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        // Outputting the individual cells
        for (int currentColumn = 0; currentColumn < game.numSquaresX; currentColumn++) {
            for (int currentRow = 0; currentRow < game.numSquaresY; currentRow++) {

                int startX = startingX + gridWidth + (cellSize + gridWidth) * currentColumn;
                int endX = startX + cellSize;
                int startY = startingY + gridWidth + (cellSize + gridWidth) * currentRow;
                int endY = startY + cellSize;

                Tile currentTile = game.grid.getCellContent(currentColumn, currentRow);
                if (currentTile != null) {
                    //Get and represent the value of the tile
                    int value = currentTile.getValue();
                    int index = log2(value);

                    //Check for any active animations
                    ArrayList<AnimationCell> aArray = game.aGrid.getAnimationCell(currentColumn, currentRow);
                    boolean animated = false;
                    for (int i = aArray.size() - 1; i >= 0; i--) {
                        AnimationCell aCell = aArray.get(i);
                        //If this animation is not active, skip it
                        if (aCell.getAnimationType() == G2048Game.SPAWN_ANIMATION) {
                            animated = true;
                        }

                        if (!aCell.isActive()) {
                            continue;
                        }

                        if (aCell.getAnimationType() == G2048Game.SPAWN_ANIMATION) { // Spawning animation
                            double percentDone = aCell.getPercentageDone();
                            float textScaleSize = (float) (percentDone);
                            paint.setTextSize(textSize * textScaleSize);

                            float cellScaleSize = cellSize / 2f * (1 - textScaleSize);
                            bitmapCell[index].setBounds((int) (startX + cellScaleSize), (int) (startY + cellScaleSize), (int) (endX - cellScaleSize), (int) (endY - cellScaleSize));
                            bitmapCell[index].draw(canvas);
                        } else if (aCell.getAnimationType() == G2048Game.MERGE_ANIMATION) { // Merging Animation
                            double percentDone = aCell.getPercentageDone();
                            float textScaleSize = (float) (1 + INITIAL_VELOCITY * percentDone
                                    + MERGING_ACCELERATION * percentDone * percentDone / 2);
                            paint.setTextSize(textSize * textScaleSize);

                            float cellScaleSize = cellSize / 2f * (1 - textScaleSize);
                            bitmapCell[index].setBounds((int) (startX + cellScaleSize),
                                    (int) (startY + cellScaleSize),
                                    (int) (endX - cellScaleSize),
                                    (int) (endY - cellScaleSize));

                            bitmapCell[index].draw(canvas);
                        } else if (aCell.getAnimationType() == G2048Game.MOVE_ANIMATION) {  // Moving animation
                            double percentDone = aCell.getPercentageDone();
                            int tempIndex = index;
                            if (aArray.size() >= 2) {
                                tempIndex = tempIndex - 1;
                            }
                            int previousX = aCell.extras[0];
                            int previousY = aCell.extras[1];
                            int currentX = currentTile.getX();
                            int currentY = currentTile.getY();
                            int dX = (int) ((currentX - previousX) * (cellSize + gridWidth)
                                    * (percentDone - 1) * 1.0);

                            int dY = (int) ((currentY - previousY) * (cellSize + gridWidth)
                                    * (percentDone - 1) * 1.0);

                            bitmapCell[tempIndex].setBounds(startX + dX, startY + dY, endX + dX, endY + dY);
                            bitmapCell[tempIndex].draw(canvas);
                        }
                        animated = true;
                    }

                    //No active animations? Just draw the cell
                    if (!animated) {
                        bitmapCell[index].setBounds(startX, startY, endX, endY);
                        bitmapCell[index].draw(canvas);
                    }
                }
            }
        }
    }

    // todo: Should be dependent on the theme
    private void drawEndGameState(Canvas canvas) {
        double alphaChange = 1;
        continueButtonEnabled = false;

        for (AnimationCell animation : game.aGrid.globalAnimation) {
            if (animation.getAnimationType() == G2048Game.FADE_GLOBAL_ANIMATION) {
                alphaChange = animation.getPercentageDone();
            }
        }

        BitmapDrawable displayOverlay = null;
        if (game.gameWon()) {
            if (game.canContinue()) {
                continueButtonEnabled = true;
                displayOverlay = winGameContinueOverlay;
            } else {
                displayOverlay = winGameFinalOverlay;
            }
        } else if (game.gameLost()) {
            displayOverlay = loseGameOverlay;
        }

        if (displayOverlay != null) {
            displayOverlay.setBounds(startingX, startingY, endingX, endingY);
            displayOverlay.setAlpha((int) (255 * alphaChange));
            displayOverlay.draw(canvas);
        }
    }

    private void createEndGameStates(Canvas canvas, boolean win, boolean showButton) {
        int width = endingX - startingX;
        int length = endingY - startingY;
        int middleX = width / 2;
        int middleY = length / 2;

        if (win) {
            lightUpRectangle.setAlpha(127);
            drawDrawable(canvas, lightUpRectangle, 0, 0, width, length);
            lightUpRectangle.setAlpha(255);

            paint.setColor(getResources().getColor(R.color.text_white));
            paint.setAlpha(255);
            paint.setTextSize(gameOverTextSize);
            paint.setTextAlign(Paint.Align.CENTER);

            int textBottom = middleY - centerText();
            canvas.drawText(getResources().getString(R.string.you_win), middleX, textBottom, paint);
            paint.setTextSize(bodyTextSize);
            String text = showButton ? getResources().getString(R.string.go_on) : getResources().getString(R.string.for_now);
            canvas.drawText(text, middleX, textBottom + textPaddingSize * 2 - centerText() * 2, paint);

        } else {

            fadeRectangle.setAlpha(127);
            drawDrawable(canvas, fadeRectangle, 0, 0, width, length);
            fadeRectangle.setAlpha(255);

            paint.setColor(getResources().getColor(R.color.text_black));
            paint.setAlpha(255);
            paint.setTextSize(gameOverTextSize);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getResources().getString(R.string.game_over), middleX, middleY - centerText(), paint);
        }
    }

    private void createBackgroundBitmap(int width, int height) {
        background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        drawBackground(canvas);
        drawBackgroundGrid(canvas);
        //if (showHelp) drawInstructions(canvas);
    }

    private void createBitmapCells() {
        Resources resources = getResources();
        int[] cellRectangleIds = getCellRectangleIds();
        paint.setTextAlign(Paint.Align.CENTER);

        for (int currentBlock = 1; currentBlock < bitmapCell.length; currentBlock++) {
            int value = (int) Math.pow(2, currentBlock);
            paint.setTextSize(cellTextSize);
            float tempTextSize = cellTextSize * cellSize * 0.9f /
                    Math.max(cellSize * 0.9f, paint.measureText(String.valueOf(value)));

            paint.setTextSize(tempTextSize);
            Bitmap bitmap = Bitmap.createBitmap(cellSize, cellSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawDrawable(canvas, ContextCompat.getDrawable(getContext(), cellRectangleIds[currentBlock]),
                    0, 0, cellSize, cellSize);
            drawCellText(canvas, value);
            bitmapCell[currentBlock] = new BitmapDrawable(resources, bitmap);
        }

    }

    private int[] getCellRectangleIds() {
        int[] cellRectangleIds = new int[numCellTypes];
        cellRectangleIds[0] = R.drawable.cell_rectangle;
        cellRectangleIds[1] = R.drawable.cell_rectangle_2;
        cellRectangleIds[2] = R.drawable.cell_rectangle_4;
        cellRectangleIds[3] = R.drawable.cell_rectangle_8;
        cellRectangleIds[4] = R.drawable.cell_rectangle_16;
        cellRectangleIds[5] = R.drawable.cell_rectangle_32;
        cellRectangleIds[6] = R.drawable.cell_rectangle_64;
        cellRectangleIds[7] = R.drawable.cell_rectangle_128;
        cellRectangleIds[8] = R.drawable.cell_rectangle_256;
        cellRectangleIds[9] = R.drawable.cell_rectangle_512;
        cellRectangleIds[10] = R.drawable.cell_rectangle_1024;
        cellRectangleIds[11] = R.drawable.cell_rectangle_2048;
        for (int xx = 12; xx < cellRectangleIds.length; xx++) {
            cellRectangleIds[xx] = R.drawable.cell_rectangle_4096;
        }
        return cellRectangleIds;
    }

    private void createOverlays() {
        Resources resources = getResources();

        //Initialize overlays
        Bitmap bitmap = Bitmap.createBitmap(endingX - startingX, endingY - startingY,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        createEndGameStates(canvas, true, true);
        winGameContinueOverlay = new BitmapDrawable(resources, bitmap);

        bitmap = Bitmap.createBitmap(endingX - startingX, endingY - startingY,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        createEndGameStates(canvas, true, false);
        winGameFinalOverlay = new BitmapDrawable(resources, bitmap);

        bitmap = Bitmap.createBitmap(endingX - startingX, endingY - startingY,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        createEndGameStates(canvas, false, false);
        loseGameOverlay = new BitmapDrawable(resources, bitmap);
    }

    private void tick() {
        long currentTime = System.nanoTime();
        game.aGrid.tickAll(currentTime - lastFPSTime);
//        Log.e("FPS" , 1000 / ((currentTime - lastFPSTime) / 1_000_000) + "");
        lastFPSTime = currentTime;
    }

    public void reSyncTime() {
        lastFPSTime = System.nanoTime();
    }

    private void getLayout(int width, int height) {
        cellSize = Math.min(width / (game.numSquaresX + 1), height / (game.numSquaresY + 3));
        gridWidth = cellSize / 8;
        int screenMiddleX = width / 2;
        int screenMiddleY = height / 2;
        int boardMiddleY = screenMiddleY + cellSize / 2;

        //Grid Dimensions
        double halfNumSquaresX = game.numSquaresX / 2d;
        double halfNumSquaresY = game.numSquaresY / 2d;
        startingX = (int) (screenMiddleX - (cellSize + gridWidth) * halfNumSquaresX - gridWidth / 2);
        endingX = (int) (screenMiddleX + (cellSize + gridWidth) * halfNumSquaresX + gridWidth / 2);
        startingY = (int) (screenMiddleY - (cellSize + gridWidth) * halfNumSquaresY - gridWidth / 2) - 100;
        endingY = (int) (screenMiddleY + (cellSize + gridWidth) * halfNumSquaresY + gridWidth / 2) - 100;

        float widthWithPadding = endingX - startingX;

        // Text Dimensions
        paint.setTextSize(cellSize);
        textSize = cellSize * cellSize / Math.max(cellSize, paint.measureText("0000"));

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(1000);

        instructionsTextSize = Math.min(
                1000f * (widthWithPadding / (paint.measureText(getResources().getString(R.string.instructions)))),
                textSize / 1.5f
        );

        gameOverTextSize = Math.min(
                Math.min(
                        1000f * ((widthWithPadding - gridWidth * 2) / (paint.measureText(getResources().getString(R.string.game_over)))),
                        textSize * 2
                ),
                1000f * ((widthWithPadding - gridWidth * 2) / (paint.measureText(getResources().getString(R.string.you_win))))
        );

        paint.setTextSize(cellSize);
        cellTextSize = textSize;
        titleTextSize = textSize / 3;
        bodyTextSize = (int) (textSize / 1.5);
        headerTextSize = textSize * 2;
        textPaddingSize = (int) (textSize / 3);
        iconPaddingSize = (int) (textSize / 5);

        paint.setTextSize(titleTextSize);

        //static variables
        paint.setTextSize(bodyTextSize);
        reSyncTime();
    }

    private int centerText() {
        return (int) ((paint.descent() + paint.ascent()) / 2);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //Reset the transparency of the screen

        canvas.drawBitmap(background, 0, 0, paint);

//        if (mainViewHooks != null) {
//            mainViewHooks.onScoreChanged(game.score);
//            mainViewHooks.onHighScoreChanged(game.highScore);
//        }

        drawCells(canvas);

        if (!game.isActive()) {
            drawEndGameState(canvas);
        }

        if (!game.canContinue()) {
            mainViewHooks.endlessModeEntered();
        }

        //Refresh the screen if there is still an animation running
        if (game.aGrid.isAnimationActive()) {
            invalidate(startingX, startingY, endingX, endingY);
            tick();
            //Refresh one last time on game end.
        } else if (!game.isActive() && refreshLastTime) {
            invalidate();
            refreshLastTime = false;
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldW, int oldH) {
        super.onSizeChanged(width, height, oldW, oldH);
        getLayout(width, height);
        createBitmapCells();
        createBackgroundBitmap(width, height);
        createOverlays();
    }
}
