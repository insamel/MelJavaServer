package server;

import java.io.*;
import java.net.*;

public class BlogServer {
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;

	// constructor
	public void BlogServer(){
	}

	public static void main(String[] args) {
		BlogServer newServer = new BlogServer();			// Creates an object 'newServer', which will later hold all connections (?)
		newServer.startRunning();							// Start running 'server'. 
	}	

	// setup of server
	public void startRunning(){
		try{
			server = new ServerSocket(4040, 100);		// portnumber, 100 connection can wait at the port (called backlog)
			while(true){								// look forever for new connections
				try{
					connection = server.accept();	 	// accepts if connection is possible and creates socket
					streamSetup();					
				}catch(EOFException eofException){		// when someone ends the connection, the following message will be displayed. 
					System.out.print("Connection ended.");
				}finally{
					close();							// Method to close output, input and connection. 
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	// Get stream to send and receive data
	private void streamSetup() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());	// creates object 'ObjectOutputStream'. From documentation about getOutputStream(): This method returns an OutputStream where the data can be written. 
		output.flush();													// cleans the stream, if all bytes were not correctly send. 
		input = new ObjectInputStream(connection.getInputStream());		// creates object 'ObjectInputStream'. From documentation about getInputStream(): This method returns an InputStream representing the data. 
	}

	// to close streams and sockets when connection is ended
	private void close(){
		try{
			output.close();							// closes stream i&o
			input.close();
			connection.close();						// closes socket to not waste memory
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
