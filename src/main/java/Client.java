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

    public static void main(String... args){	
		Client client = new Client("127.0.0.1"); // localhost. IP always routing back to our own computer. 
		while(true){  
			client.startRunning();
		}
		//clientSocket = serverSocket.accept();	// waits until client start up, and request connection on the host and port of the server. 
    }

	//connect to server
	public void startRunning(){
		try{
			connectToServer();
			streamSetup();
		}catch(EOFException eofException){
			//showMessage("\n Client terminated the connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			close();
		}
	}

	//connect to server
	private void connectToServer() throws IOException{
		connection = new Socket(InetAddress.getByName(serverIP), 4040);
	}

	//setup of IO streams
	private void streamSetup() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	//	showMessage("\n The streams are now set up! \n");
	}

	//Close connection
	private void close(){
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

}
