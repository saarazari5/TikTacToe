package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
final static  String BTN_CSS_STYLE_GAME = "-fx-border-width: 2px;" +
        " -fx-border-color:#000000; -fx-font: normal bold 40px 'serif'; -fx-margin:0px;";
    final static  String BTN_CSS_STYLE_MENU = "-fx-border-width: 2px;" +
            " -fx-border-color:#000000; -fx-font: normal 10px 'serif'; -fx-margin:0px;";
    public Button AI;
    public Button pvp;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setTitle("welcome to my first project");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

    }

    public void initialize() {
        AI.setStyle(BTN_CSS_STYLE_MENU);
        pvp.setStyle(BTN_CSS_STYLE_MENU);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setGame(ActionEvent actionEvent) throws IOException {
       Button currentBtn= (Button) actionEvent.getSource();
        String cuID= currentBtn.getId();
        //((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        pvp.setDisable(true);
        AI.setDisable(true);
        switch (cuID){
            case "pvp":
                activateGame(false);
                break;
            case "AI":
                activateGame(true);
        }
        pvp.setDisable(false);
        AI.setDisable(false);

    }

    private void activateGame(boolean b) throws IOException {
        Stage gameStage=new Stage();
        gameStage.setFullScreen(true);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        gameStage.setTitle("welcome to my first project");
        gameStage.setScene(new Scene(root, 500, 500));
        gameStage.show();
        Controller.setPrimaryStage(gameStage,b);
    }
}
