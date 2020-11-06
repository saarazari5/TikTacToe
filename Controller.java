package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Model.AI;
import sample.Model.TikModel;

import java.util.ArrayList;

public class Controller {
    private final char[][] board = new char[3][3];
    static Stage stage;
    //buttons from fxml
    public Button b00,b01,b02,b10,b11,b12,b20,b21,b22;
    //using min max algorithem  winner will be decided by end val of winner >0|<0
    int winner=0;
    public static boolean gameOver=false;
    public  ArrayList<Button>buttons= new ArrayList<>();
    private static   boolean playAgainstAI;

    public static void setPrimaryStage(Stage primaryStage, boolean b) {
        playAgainstAI=b;
        stage=primaryStage;
    }

    public void initialize() {
        buttons.add(b00);buttons.add(b01);buttons.add(b02);
        buttons.add(b10);buttons.add(b11);buttons.add(b12);
        buttons.add(b20);buttons.add(b21);buttons.add(b22);
        for (Button button : buttons) {
            button.setStyle(Main.BTN_CSS_STYLE_GAME);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3 ; j++) {
                board[i][j]= TikModel.BASE_CHAR;
            }
        }
    }
    XOAI2 xoai2= new XOAI2();
    //private final XOModel xoModel= new XOModel(buttons);
    boolean isPlayer =true;
    public void onBtnClicked(ActionEvent actionEvent) {
        Button currentBtn= (Button) actionEvent.getSource();
        currentBtn.setDisable(true);
        String cuID= currentBtn.getId().substring(1,3);
        int row_col= Integer.parseInt(cuID);
        System.out.println(row_col/10+""+row_col%10);
        if(isPlayer) {
            currentBtn.setText("X");
            currentBtn.setTextFill(Color.GREEN);
            currentBtn.setFont(Font.font(30));
            board[row_col/10][row_col%10]= TikModel.player;
            isPlayer=false;
            checks(true);
        }else {
            currentBtn.setText("O");
            currentBtn.setFont(Font.font(30));
            currentBtn.setTextFill(Color.RED);
            board[row_col/10][row_col%10]= TikModel.opponent;
            isPlayer=true;
            checks(false);
        }




    }

    private void checks(boolean b) {
        winner= TikModel.evaluate(board);
        if(winner!=0){
            char winC=winner>0? TikModel.player: TikModel.opponent;
            gameOver=true;
            loadNewScreen("WINNER IS:"+winC ,true);



        }else if (!TikModel.isMovesLeft(board)) {
            gameOver = true;
            loadNewScreen("ITS A TIE BITCH",true);

        }
            //a condition that will activate if user chose to play against AI (for anyone who wants to add a pvp to the code)
        else if(b&&playAgainstAI) {
            initMove();
        }

    }

    private void initMove() {
        TikModel.Move move= findBestMoveResponse();
        Button temp= setButtonByBestMove(move);
        temp.fire();
    }

    private Button setButtonByBestMove(TikModel.Move move) {
        return buttons.get(((move.row*3)+move.col));
    }


    private TikModel.Move findBestMoveResponse() {
        return TikModel.findBestMove(board);
    }

    private static class XOAI2 extends AI {
        Button rndButton;


        public void setRndButton(Button rndButton) {
            this.rndButton = rndButton;
        }



        @Override
        public void response() {
            rndButton.fire();
        }
    }




    void loadNewScreen(String messege,boolean closeAppAfterMSG){

        GridPane gridPane=  new GridPane();
        Stage newStage= new Stage();
        newStage.setTitle("messege");
        Label label3 = new Label(messege);
        label3.setOnMouseEntered((MouseEvent e) -> {
            label3.setScaleX(1.5);
            label3.setScaleY(1.5);
        });

        label3.setOnMouseExited((MouseEvent e) -> {
            label3.setScaleX(1);
            label3.setScaleY(1);
        });



        label3.setWrapText(true);
        gridPane.add(label3,1,1);
        gridPane.setAlignment(Pos.CENTER);
        newStage.setScene(new Scene( gridPane,500, 500));
        newStage.show();
        if(closeAppAfterMSG)
            stage.close();
        }

}


