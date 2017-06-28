package amit.turgeman.application;

import java.io.FileInputStream;

import amit.turgeman.sokobanServer.Server;
import amit.turgeman.sokobanServer.SokobanClientHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        final Server server = new Server(5555, new SokobanClientHandler());
        try {
        	new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						server.runServer();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
        	}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
        Scene scene = new Scene(root, 250, 250);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.getIcons().add(new Image(new FileInputStream("resources/admin.png")));
		stage.setTitle("Sokoban Server");
		stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
	}
}
