package amit.turgeman.application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DashboardController implements Initializable {

    @FXML
    private Button button;
    
    @FXML
    private Label label;

    @SuppressWarnings("rawtypes")
	@FXML
    private ListView myListView;
    private AdminModel model;

    protected ListProperty<String> listProperty = new SimpleListProperty<>();

    @FXML
    private void handleButtonAction(ActionEvent event) {
    	updateList();
    }

	@SuppressWarnings("unchecked")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
      
    	model = AdminModel.getInstance();      
        myListView.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(model.getClients()));
    }
    
    private void updateList() {
    	listProperty.set(FXCollections.observableArrayList(model.getClients()));
    }
}