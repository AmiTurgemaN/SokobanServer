package amit.turgeman.sokobanServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import amit.turgeman.application.AdminModel;
import model.data.beans.*;

public class SokobanClientHandler implements ClientHandler {

	@Override
	public void handleClient(Socket socket) {
		ObjectInputStream ois = null;
		PrintWriter writer = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			writer = new PrintWriter(socket.getOutputStream());
			
			Level level = (Level)ois.readObject();
			String name = level.getLevelName();
			String userName = level.getUserName();
			
			AdminModel.getInstance().addClient(userName, socket);
			
			String solution = getSolutionFromService(name,level.getLevelString());
			if (solution == null) {
				// call Strips ...
				// save the solution via the service
			}
			writer.println(solution);
			writer.flush();
			
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (writer != null)
					writer.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}		

	}
	
    private String getSolutionFromService(String name,String levelString) {
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
        	System.out.println("Unable to solve the level");
        }
        return null;
    }


}
