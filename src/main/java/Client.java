import java.io.*;
import java.net.*;

public class Client {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String serverIP;
	private Socket connection;

	//constructor
	public Client(String host){
		serverIP = host;
	}

	//connect to server
	public void startRunning(){
		try{
			connectToServer();
			setupStreams();
		}catch(EOFException eofException){
			//showMessage("\n Client terminated the connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeConnection();
		}
	}

	//connect to server
	private void connectToServer() throws IOException{
	//	showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP), 4040);
	//	showMessage("Connection Established! Connected to: " + connection.getInetAddress().getHostName());
	}

	//set up streams
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	//	showMessage("\n The streams are now set up! \n");
	}

	//Close connection
	private void closeConnection(){
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

}
