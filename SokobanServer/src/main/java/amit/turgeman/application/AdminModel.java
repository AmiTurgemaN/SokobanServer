package amit.turgeman.application;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminModel {
	private Map<String, Socket> connectedClients =
			new HashMap<String, Socket>();
	
	private static final AdminModel instance = new AdminModel();
	public static AdminModel getInstance() {
		return instance;
	}
	
	public void addClient(String userName, Socket socket) {
		connectedClients.put(userName, socket);
	}
	
	public List<String> getClients() {
		List<String> clients = new ArrayList<>();
		for (String client : connectedClients.keySet())
			clients.add(client);
		return clients;
	}
	
	public void disconnectClient(String userName) {
		Socket socket = connectedClients.get(userName);
		try {
			socket.close();
			connectedClients.remove(userName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void disconnectAllClients() {
		for(Socket s : connectedClients.values())
		{
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		connectedClients.clear();
	}
}
