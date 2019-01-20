package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

@SuppressWarnings("restriction")
public class MenuButtons {
    
    public static Button restartGame(GameContainer container) {
        Button ret = new Button("restart game");
        ret.setOnAction((handler) -> container.initializeContainer(container.size));

        ret.setMinWidth(150);
        ret.setPrefWidth(150);
        ret.setMaxWidth(150);
        ret.setAlignment(Pos.CENTER);
        return ret;
    }
}
