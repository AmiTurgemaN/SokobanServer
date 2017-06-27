package amit.turgeman.sokobanServer;

import java.net.Socket;

public interface ClientHandler {
	public void handleClient(Socket socket);
}
