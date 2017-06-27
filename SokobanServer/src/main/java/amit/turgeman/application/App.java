package amit.turgeman.application;

import java.io.FileInputStream;

import amit.turgeman.sokobanServer.Server;
import amit.turgeman.sokobanServer.SokobanClientHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        final Server server = new Server(5551, new SokobanClientHandler());
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
        Scene scene = new Scene(root, 200, 200);
		stage.getIcons().add(new Image(new FileInputStream("resources/sokobanIcon.png")));
		stage.setTitle("Sokoban Server");
		stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
	}
}
