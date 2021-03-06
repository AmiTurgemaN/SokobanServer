package amit.turgeman.sokobanServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import amit.turgeman.application.AdminModel;
import model.data.beans.*;
import model.data.beans.hibernate.Game;
import model.data.beans.hibernate.GameManager;
import model.data.beans.hibernate.HibernateUtil;

public class SokobanClientHandler implements ClientHandler {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	@Override
	public void handleClient(Socket socket) {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			String inputString="";
			Level level=null;
			Object inputObject;
			while(true)
			{
				inputObject = ois.readObject();
				if(inputObject instanceof String)
				{
					inputString = (String)inputObject;
					if (inputString.startsWith("USERNAME"))
					{
						AdminModel.getInstance().addClient(inputString.replaceAll("USERNAME", ""), socket);
						continue;
					}
					else if (inputString.startsWith("REQUEST"))
					{	
						inputString=inputString.replaceAll("REQUEST", "");
						ArrayList<Game> recordsArrayList = HibernateUtil.getWorldWideRecordsAsArrayList();
						oos.writeObject(recordsArrayList);
						oos.flush();
						continue;
					}
					
				}
				else if(inputObject instanceof Level)
				{
					level = (Level)inputObject;
					String solution = getSolutionFromService(level.getLevelName(),level.getLevelString());
					oos.writeObject(solution);
					oos.flush();
					continue;
				}
				else if (inputObject instanceof Game)
				{	
					Game game = (Game)inputObject;
					GameManager gm = new GameManager(game);
					gm.addGame();
					continue;
				}
			}
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}

	private String getSolutionFromService(String name,String levelString) {
		System.out.println(name);
		String solution="";
		String url = "http://localhost:8080/SokobanWebService/webapi/solutions/" + name;    	
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(url);	
		Response response = webTarget.request(MediaType.TEXT_PLAIN).get(Response.class);        
		if (response.getStatus() == 200) {
			solution = response.readEntity(String.class);
			System.out.println("solution: " + solution);
			return solution;
		} 
		com.sun.jersey.api.client.Client webClient = com.sun.jersey.api.client.Client.create();
		WebResource webResource = webClient.resource(url);
		ClientResponse response2 = webResource.type("text/plain").post(ClientResponse.class, levelString);

		if (response2.getStatus() == 201) {
			solution = response2.getEntity(String.class);
			System.out.println("solution: " + solution);
			return solution;
		}
		else if(solution.equals("Unsolvable")) {        	
			System.out.println("Unable to solve level "+name);
		}
		System.out.println(solution);
		return solution;
	}


}
