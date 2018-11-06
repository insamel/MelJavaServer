import java.io.*;
import java.net.*;

public class Server {
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;

	// constructor
	public void Server(){
	}	

	// setup of server
	public void startRunning(){
		try{
			server = new ServerSocket(4040, 100);	// portnumber, 100 can wait at the port (called backlog)
			while(true){	// look forever!
				try{
					connection = server.accept();	// accepts if connection and creates socket
					setupStreams();					
				}catch(EOFException eofException){	// when someone ends the connection, the following message will be displayed. 
				}finally{
					closeAll();
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	// Get stream to send and receive data
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();	
		input = new ObjectInputStream(connection.getInputStream());
	}

	// to close streams and sockets after use
	private void closeAll(){
		try{
			output.close();	// closes stream i&o
			input.close();
			connection.close();	// closes socket to not waste memory
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

}
