package sample;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;

public class GameContainer {

    public final GridPane container = new GridPane();
    private Board gameBoard;
    public final VBox menu = new VBox();

    public int size;
    private int mode = 0;
    private int maxPlayers = 2;

    public void initializeContainer(int n) {
        container.getChildren().clear();
        menu.getChildren().clear();

        Slider sliderPlayer = new Slider(2, 10, maxPlayers);
        sliderPlayer.setValueChanging(true);
        sliderPlayer.setShowTickLabels(true);
        sliderPlayer.setMajorTickUnit(1);
        sliderPlayer.setBlockIncrement(1);
        sliderPlayer.setValueChanging(true);
        Slider slider = new Slider(3, 50, n);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(7);
        slider.setBlockIncrement(7);
        slider.setValueChanging(true);

        //
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            size = newValue.intValue();
        });
        sliderPlayer.valueProperty().addListener((observable, oldValue, newValue) -> {
            maxPlayers = newValue.intValue();
        });

        Label playerLabel = new Label("Player: " + 1);
        gameBoard = Board.createBoard(n, mode, maxPlayers, playerLabel);

        //contain the checkerboard
        ColumnConstraints hContain = new ColumnConstraints();
        RowConstraints vContain = new RowConstraints();
        hContain.prefWidthProperty().bind(container.getScene().widthProperty().subtract(150));
        vContain.prefHeightProperty().bind(container.getScene().heightProperty());
        //contain the menu
        ColumnConstraints hMenu = new ColumnConstraints(150);

        hContain.setHgrow(Priority.ALWAYS);
        vContain.setVgrow(Priority.ALWAYS);
        hMenu.setHgrow(Priority.NEVER);

        container.getColumnConstraints().addAll(hContain, hMenu);
        container.getRowConstraints().add(vContain);
        container.add(gameBoard, 0, 0);

        gameBoard.cellWidthProperty().bind(hContain.prefWidthProperty().divide(gameBoard.columns));
        gameBoard.cellHeightProperty().bind(vContain.prefHeightProperty().divide(gameBoard.columns));

        menu.prefWidthProperty().bind(hMenu.prefWidthProperty());
        menu.minWidthProperty().bind(hMenu.prefWidthProperty());
        menu.maxWidthProperty().bind(hMenu.prefWidthProperty());
        menu.prefHeightProperty().bind(vContain.prefHeightProperty());
        menu.minHeightProperty().bind(vContain.prefHeightProperty());
        menu.maxHeightProperty().bind(vContain.prefHeightProperty());
        menu.setStyle("-fx-background-color: white");


        menu.getChildren().add(sliderPlayer);
        menu.getChildren().add(slider);
        menu.getChildren().add(MenuButtons.restartGame(this));
        menu.getChildren().add(playerLabel);
        menu.setSpacing(5);

        container.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Q:
                    mode++;
                    if (mode == 4) {
                        mode = 0;
                    }
                    gameBoard.mode = mode;
                    gameBoard.handleBoard();
                    gameBoard.onlyDraw();
                    break;
                case E:
                    mode--;
                    if (mode == -1) {
                        mode = 3;
                    }
                    gameBoard.mode = mode;
                    gameBoard.handleBoard();
                    gameBoard.onlyDraw();
                    break;
            }


        });


        container.add(menu, 1, 0);
    }


}
