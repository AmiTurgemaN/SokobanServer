package amit.turgeman.application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class DashboardController implements Initializable {

	public DashboardController() {
	}

	@SuppressWarnings("rawtypes")
	@FXML
	private ListView myListView;
	private AdminModel model;

	protected ListProperty<String> listProperty = new SimpleListProperty<>();

	@FXML
	private void disconnectClient(ActionEvent event) {
		if(myListView.getSelectionModel().getSelectedIndex()!=-1)
		{
			String selectedName = myListView.getSelectionModel().getSelectedItem().toString();
			model.disconnectClient(selectedName);
			updateList();
		}
	}

	@FXML
	private void refresh(ActionEvent event) {
		updateList();
	}

	@FXML
	private void disconnectAllClients(ActionEvent event) {
		model.disconnectAllClients();
		updateList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		model = AdminModel.getInstance();      
		myListView.itemsProperty().bindBidirectional(listProperty);
		listProperty.set(FXCollections.observableArrayList(model.getClients()));

	}

	private void updateList() {
		listProperty.set(FXCollections.observableArrayList(model.getClients()));
	}
}