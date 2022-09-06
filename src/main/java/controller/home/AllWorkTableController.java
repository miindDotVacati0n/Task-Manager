package controller.home;

import com.github.saacsos.FXRouter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.works.*;
import services.*;

import java.io.IOException;
import java.util.ArrayList;

public class AllWorkTableController {
    private DataList dataListFor ;
    private DataSource workDataSourceFor ;
    private ObservableList<ForwardWorks> forwardWorksObservableList ;

    private DataList dataListGen;
    private DataSource workDataSourceGen;
    private ObservableList<GeneralWorks> generalWorksObservableList ;

    private DataList dataListPro;
    private DataSource workDataSourcePro;
    private ObservableList<Project> projectObservableList ;

    private DataList dataListWeek ;
    private DataSource workDataSourceWeek ;
    private ObservableList<WeeklyWorks> weeklyWorksObservableList ;

    private DataList dataListCat ;
    private DataSource workDataSourceCat ;
    private ObservableList<Category> categoryObservableList ;

    //---------------------------------------------------------------------------------------------//

    @FXML TableView<ForwardWorks> forwardTableView;
    @FXML TableView<GeneralWorks> generalTableView;
    @FXML TableView<Project> projectTableView;
    @FXML TableView<WeeklyWorks> weeklyTableView;
    @FXML TableView catTableView;
    @FXML TextField searchBar;

    //---------------------------------------------------------------------------------------------//

    public void initialize() {
        Platform.runLater((Runnable)new Runnable() {
            @Override
            public void run() {
                workDataSourceFor = new ForwardWorksFileDataSource("data", "forwardWorks.csv");
                dataListFor = workDataSourceFor.getData();
                showForwardData();

                workDataSourceGen = new GeneralWorkFileDataSource("data", "generalWorks.csv");
                dataListGen = workDataSourceGen.getData();
                showGeneralWorksData();

                workDataSourcePro = new ProjectWorkFileDataSource("data", "projectWorks.csv");
                dataListPro = workDataSourcePro.getData();
                showProjectData();

                workDataSourceWeek = new WeeklyWorksFileDataSource("data", "weeklyWorks.csv");
                dataListWeek = workDataSourceWeek.getData();
                showWeeklyData();

                workDataSourceCat = new CategoryFileDataSource("data", "category.csv");
                dataListCat = workDataSourceCat.getData();
                showCategoryData();
            }

        });

    }

    ////----------------------------------------------------------------------------------/////

    private void showForwardData() {
        forwardTableView.getColumns().clear();
        forwardWorksObservableList = FXCollections.observableArrayList(dataListFor.getForwardWorksArrayList());
        forwardTableView.setItems(forwardWorksObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Work name", "field:name"));
        configs.add(new StringConfiguration("title:Project leader", "field:forwardLeader"));
        configs.add(new StringConfiguration("title:Day Assign", "field:madeDate"));
        configs.add(new StringConfiguration("title:Time Assign", "field:startDate"));
        configs.add(new StringConfiguration("title:Priority", "field:priority"));
        configs.add(new StringConfiguration("title:Status", "field:status"));
        configs.add(new StringConfiguration("title:Category", "field:category"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            forwardTableView.getColumns().add(col);
        }
    }

    private void showGeneralWorksData() {
        generalTableView.getColumns().clear();
        generalWorksObservableList = FXCollections.observableArrayList(dataListGen.getGeneralWorkArrayList());
        generalTableView.setItems(generalWorksObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Work name", "field:name"));
        configs.add(new StringConfiguration("title:Do Date", "field:madeDate"));
        configs.add(new StringConfiguration("title:Time Start", "field:startDate"));
        configs.add(new StringConfiguration("title:Time Finish", "field:lastDate"));
        configs.add(new StringConfiguration("title:Priority", "field:priority"));
        configs.add(new StringConfiguration("title:Status", "field:status"));
        configs.add(new StringConfiguration("title:Category", "field:category"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            generalTableView.getColumns().add(col);
        }
    }

    private void showProjectData() {
        projectTableView.getColumns().clear();
        projectObservableList = FXCollections.observableArrayList(dataListPro.getProjectArrayList());
        projectTableView.setItems(projectObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Project name", "field:name"));
        configs.add(new StringConfiguration("title:Project leader", "field:projectLeader"));
        configs.add(new StringConfiguration("title:Day do", "field:startDate"));
        configs.add(new StringConfiguration("title:Day finish", "field:lastDate"));
        configs.add(new StringConfiguration("title:Priority", "field:priority"));
        configs.add(new StringConfiguration("title:Status", "field:status"));
        configs.add(new StringConfiguration("title:Category", "field:category"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            projectTableView.getColumns().add(col);
        }
    }

    private void showWeeklyData() {
        weeklyTableView.getColumns().clear();
        weeklyWorksObservableList = FXCollections.observableArrayList(dataListWeek.getWeeklyWorksArrayList());
        weeklyTableView.setItems(weeklyWorksObservableList);
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Work name", "field:name"));
        configs.add(new StringConfiguration("title:Day", "field:date"));
        configs.add(new StringConfiguration("title:Time Start", "field:startDate"));
        configs.add(new StringConfiguration("title:Time Finish", "field:lastDate"));
        configs.add(new StringConfiguration("title:Priority", "field:priority"));
        configs.add(new StringConfiguration("title:Status", "field:status"));
        configs.add(new StringConfiguration("title:Category", "field:category"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            weeklyTableView.getColumns().add(col);
        }
    }

    private void showCategoryData() {
        catTableView.getColumns().clear();
        categoryObservableList = FXCollections.observableArrayList(dataListCat.getCategoryArrayList());
        catTableView.setItems(categoryObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Category", "field:categoryName"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.prefWidthProperty().bind(catTableView.widthProperty().multiply(1.0));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            catTableView.getColumns().add(col);

        }
    }

    //------------------------------------------------------------------------------------------//

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("homepage");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า homepage ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
