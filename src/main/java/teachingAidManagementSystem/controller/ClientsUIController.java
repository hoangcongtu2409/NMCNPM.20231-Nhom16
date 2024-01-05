package teachingAidManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import teachingAidManagementSystem.App;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientsUIController implements Initializable {
    @FXML
    private AnchorPane mainWindow;
    @FXML
    private VBox allClientsBox;
    private List<HBox> tripleFrameList;
    private List<AnchorPane> frameList;

    @FXML
    public void switchToHome() throws IOException {
        App.setRoot("homeUI");
    }
    @FXML
    public void switchToManage() throws IOException {
        App.setRoot("manageUI");
    }
    @FXML
    public void switchToDevices() throws IOException {
        App.setRoot("devicesUI");
    }
    @FXML
    public void switchToClients() throws IOException {
        App.setRoot("clientsUI");
    }
    @FXML
    public void switchToProfile() throws IOException {
        App.setRoot("profileUI");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void addFrame() {
        int i = frameList.size();
        if (i % 3 == 0) {
            double h = allClientsBox.getPrefHeight();
            h += 300;
            allClientsBox.setPrefHeight(h);
            HBox hBox = new HBox();
            hBox.setPrefHeight(270);
            hBox.setSpacing(46);
            allClientsBox.getChildren().add(hBox);
        }

        int j = tripleFrameList.size();
        HBox hBox = tripleFrameList.get(j + 1);
        AnchorPane pane = new AnchorPane();
        pane.setPrefWidth(316);
        Label nameLabel = new Label();
    }
}
