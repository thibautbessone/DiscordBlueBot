package bluebot.UI;

import bluebot.MainBot;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * @file MainUI.java
 * @author Blue
 * @version 0.1
 * @brief UI main class
 */

public class MainUI extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/bluebotscene.fxml"));
        primaryStage.setTitle("BlueBot");
        primaryStage.setScene(new Scene(root, 200, 180));
        primaryStage.getIcons().add(new Image("img/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        primaryStage.setIconified(true);
                    }
                }
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                primaryStage.setIconified(true);
                event.consume();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        if(args.length >= 1) { //Added one parameter for the public instance
            if(args[0].equals("cmd")) {
                new MainBot(); //Runs command-line app
                System.out.println("Command-line app started");
            } else {
                System.out.println("Invalid option - use the cmd option to run in command-line mode.\n" +
                        "Usage : java -jar BlueBot.jar cmd");
            }
            if(args.length == 2) {
                if(args[1].equals("public")) {
                    MainBot.setPublicInstance(true); //Clearly not the best, but I'm the only one running the public instance, otherwise a map<arg, argActionObject> like the commands maps
                }
            }

        } else {
            System.out.println("JavaFX app started");
            launch(args); //Runs GUI
        }
    }
}
