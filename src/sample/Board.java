package sample;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Random;

public class Board extends TrueGrid {

    private Rect[][] rectangles;
    private Rect lastRect;
    private Label playerLabel;
    private int playerMax;
    private int player = 1;
    private Player[] players;

    public int n, mode;

    public static Board createBoard(int n, int mode, int playerMax, Label label) {
        Board board = new Board(n);
        board.playerLabel = label;
        board.mode = mode;
        board.n = n;
        board.playerMax = playerMax;
        board.players = board.initPlayers(playerMax);

        board.setTiles();
        return board;
    }

    private Board(int n) {
        super(n, n);
    }

    public void setTiles() {
        rectangles = new Rect[this.n][this.n];
        drawBoard();
        handleBoard();
    }

    private void drawBoard() {
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                Rect tmp = new Rect();
                tmp.i = j;
                tmp.j = i;

                tmp.widthProperty().bind(cellWidthProperty());
                tmp.heightProperty().bind(cellHeightProperty());
                tmp.setFill(getPaint(i, j));
                rectangles[j][i] = tmp;
                add(tmp, i, j);
            }
        }
        onlyDraw();
    }

    public void onlyDraw() {
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                Rect tmp = rectangles[j][i];

                if (tmp.clicked.isClicked()) {
                    tmp.setFill(tmp.clicked.getColor());
                } else {
                    tmp.setFill(getPaint(i, j));
                }
                if (lastRect != null) {
                    Rect showOption = showOption(lastRect.i, lastRect.j);
                    if (showOption != null && !showOption.clicked.isClicked()) {
                        showOption.setFill(tmp.clicked.getColor());
                    }
                    lastRect.setFill(tmp.clicked.getColor());
                }
            }
        }
    }

    public void handleBoard() {
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                Rect tmp = rectangles[j][i];
                tmp.i = j;
                tmp.j = i;

                Rect showOption = showOption(tmp.i, tmp.j);
                tmp.setOnMouseClicked(event -> {
                    if (showOption != null) {
                        if (!showOption.clicked.isClicked() && !tmp.clicked.isClicked()) {
                            if (player == playerMax) {
                                player = 0;
                            }

                            Color c = players[player].getColor();
                            showOption.clicked.setClicked(true);
                            showOption.clicked.setColor(c);
                            showOption.setFill(c);
                            tmp.clicked.setClicked(true);
                            tmp.clicked.setColor(c);
                            tmp.setFill(c);

                            this.playerLabel.setText("Player: " + ++player);
                        }
                    }
                });

                tmp.setOnMouseEntered(event -> {
                    if (!tmp.clicked.isClicked()) {
                        tmp.setFill(Color.BLACK);
                        if (showOption != null && !showOption.clicked.isClicked()) {
                            showOption.setFill(Color.BLACK);
                            lastRect = tmp;
                        }
                    }
                });

                tmp.setOnMouseExited(event -> {
                    if (!tmp.clicked.isClicked()) {
                        tmp.setFill(getPaint(tmp.i, tmp.j));
                    }
                    if (showOption != null && !showOption.clicked.isClicked())
                        showOption.setFill(getPaint(showOption.i, showOption.j));
                });
            }
        }
    }

    private Paint getPaint(int i, int j) {
        if (j % 2 == 0) {
            if (i % 2 == 0) {
                return Color.CYAN;
            } else {
                return Color.TAN;
            }
        } else {
            if (i % 2 == 0) {
                return Color.TAN;
            } else {
                return Color.CYAN;
            }
        }
    }

    private Rect showOption(int i, int j) {
        Rect rect = null;

        if (i != 0 && mode == 0) {
            rect = rectangles[i - 1][j];
        }
        if (j != 0 && mode == 1) {
            rect = rectangles[i][j - 1];
        }
        if (i != n - 1 && mode == 2) {
            rect = rectangles[i + 1][j];
        }
        if (j != n - 1 && mode == 3) {
            rect = rectangles[i][j + 1];
        }
        return rect;
    }

    private Player[] initPlayers(int playerMax) {
        Player[] players = new Player[playerMax];
        for (int i = 0; i < playerMax; i++) {
            players[i] = new Player(i, Color.color(new Random().nextDouble(), new Random().nextDouble(), new Random().nextDouble()));
        }
        return players;
    }

}
