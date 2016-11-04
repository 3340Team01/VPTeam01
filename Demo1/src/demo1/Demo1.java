/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/

package demo1;
import GUI.GUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author E
 */
public class Demo1 extends Application {
    
    @Override
    public void start(Stage stage) 
    {
       BorderPane bp =new BorderPane();
       
       GUI g=new GUI();
       
       HBox titleBox=g.makeTitleBox();
       TabPane tabs=g.makeTabPane();
       bp.setTop(titleBox);
       bp.setCenter(tabs);
       Scene scene = new Scene(bp, 500, 500);
        
        stage.setTitle("Demo1");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
