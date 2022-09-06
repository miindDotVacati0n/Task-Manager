package controller.home;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomePageController {
    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleGeneralWorksButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("general");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า general ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleProjectButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("project");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า project ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleForwardButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("forward");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า forward ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleWeeklyButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("weekly");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า weekly ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleCategoryButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("category");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า category ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleAllWorkButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("allWorkTable");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า weekly ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleManualButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("manual");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า forward ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

//    public void handleSubmitAction(ActionEvent actionEvent) {
//    }
}
