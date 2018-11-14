package client;
import request.PostSubmission;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;

	//constructor
	public Client(){
	}

    public static void main(String... args) throws Exception {

		Client client = new Client(); 
		while(true){

			client.startRunning();

		}
    }

	//connect to server
	public void startRunning(){
		try{
			connectToServer();
			streamSetup();
		}catch(EOFException eofException){
			System.out.println("\n Client terminated the connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			close();
		}
	}

	//connect to server
	private void connectToServer() throws IOException{
		connection = new Socket("localhost", 4040);
	}

	//setup of IO streams
	private void streamSetup() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());

		String author = "Author_clientside";
		String tweet = "Tweet clientside";
		Date timestamp = Calendar.getInstance().getTime();
		PostSubmission user = new PostSubmission(author, tweet, timestamp);
		output.writeObject(user);

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