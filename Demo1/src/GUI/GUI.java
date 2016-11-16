
package GUI;
import Database.DB;
import Database.DBOperations;
import Database.User;
import Database.Utilities;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GUI 
{
    public HBox makeTitleBox()
    {
       HBox titleBox=new HBox();
            
       Text title=new Text("Demo1");
       title.setFont(Font.font ("Verdana",20));
       title.setFill(Color.BLACK);
       titleBox.getChildren().add(title);
       
       return titleBox;
    }
    
    public TabPane makeTabPane()
    {
        TabPane tabMaster=new TabPane();
       
        Tab tabRegister=new Tab();
        tabRegister.setText("Register");
        GridPane registerBox= makeRegisterBox();
        tabRegister.setContent(registerBox);
        
        Tab tabLogin=new Tab();
        tabLogin.setText("Login");
        GridPane loginBox=makeLoginBox();
        tabLogin.setContent(loginBox);
        
        tabMaster.getTabs().addAll(tabRegister,tabLogin);
        
        return tabMaster;
    }
    
    public GridPane makeRegisterBox()
    {
        GridPane registerGrid=new GridPane();
        
        registerGrid.setPadding(new Insets(10,20,30,40));
        
        Text firstName = new Text("Firstname: ");
        Text lastName = new Text("Lastname: ");
        Text email = new Text("Email: ");
        Text password=new Text("Password");
        Text pos = new Text("Career: ");
        
        TextField firstnameInput=new TextField();
        TextField lastnameInput=new TextField();
        TextField emailInput=new TextField();
        TextField passwordInput=new TextField();
        TextField posInput=new TextField();
        
        Button Save= new Button("Submit");
        Button checkConnect=new Button("Check Connection");
        
        checkConnect.setOnAction(e->
        {
            DB d=new DB();
            
            
           
        });
        
        Save.setOnAction(e->
        {
            System.out.println("Inserted in table Users");
            String first=firstnameInput.getText();
            String last=lastnameInput.getText();
            String mail=emailInput.getText();
            String pass=passwordInput.getText();
            
            
            User u=new User(last,first,pass,mail);
            DBOperations d =new DBOperations();
                d.registerUser(u);
            
  
        });
        
        registerGrid.add(firstName, 0, 0);
        registerGrid.add(firstnameInput,1,0);
        registerGrid.add(lastName,0,1);
        registerGrid.add(lastnameInput,1,1);
        registerGrid.add(email,0,2);
        registerGrid.add(emailInput,1,2);
        registerGrid.add(password,0,3);
        registerGrid.add(passwordInput,1,3);
        registerGrid.add(pos,0,4);
        registerGrid.add(posInput,1,4);
        
        
        registerGrid.add(Save,0,5);
        registerGrid.add(checkConnect,1,5);
        
        
        return registerGrid;
    }
    
    public GridPane makeLoginBox()
    {
        GridPane loginGrid=new GridPane();
        
        loginGrid.setPadding(new Insets(10,20,30,40));
        
        Text emailLogin=new Text("Email: ");
        Text passwordLogin=new Text("Password: ");
        
        TextField emailLoginInput=new TextField();
        TextField passwordLoginInput=new TextField();
        
        Button submit=new Button("Submit");
        
        loginGrid.add(emailLogin,0,0);
        loginGrid.add(emailLoginInput,1,0);
        loginGrid.add(passwordLogin,0,1);
        loginGrid.add(passwordLoginInput,1,1);
        loginGrid.add(submit,0,2);
        return loginGrid;
        
    }

        
}
