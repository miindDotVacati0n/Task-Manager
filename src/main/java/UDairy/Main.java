package UDairy;

import com.github.saacsos.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);
        //Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        //primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 300, 275));
        //primaryStage.show();
        FXRouter.bind(this, primaryStage, "UDairyApplication", 1280, 1024);
        configRoute();
        FXRouter.goTo("homepage");
    }

    private static void configRoute() {
        FXRouter.when("home", "home.fxml");
        FXRouter.when("creator", "creator.fxml", 1280, 950);
        FXRouter.when("homepage", "homepage/homepage.fxml", 1280, 950);
        FXRouter.when("general", "homepage/workingSpace/general.fxml",1280, 950);
        FXRouter.when("project", "homepage/workingSpace/project.fxml",1280, 950);
        FXRouter.when("weekly", "homepage/workingSpace/weekly.fxml",1280 , 800);
        FXRouter.when("forward", "homepage/workingSpace/forward.fxml",1280, 800);
        FXRouter.when("allWorkTable", "homepage/workingSpace/allWorkTable.fxml", 1280, 960);
        FXRouter.when("category", "homepage/workingSpace/category.fxml", 1280, 960);
        FXRouter.when("manual", "homepage/manual.fxml",500,300);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
