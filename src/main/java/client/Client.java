package client;
import request.PostSubmission;
import data.Post;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private String action;
	private String author;
	private String tweet;
	private Date timestamp;
	private Scanner stringScanner;
	private String action2;

	//constructor
	public Client(){
	}

    public static void main(String... args) throws Exception {

		Client client = new Client();
		client.startRunning();
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
	private void streamSetup() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());

		doSomething();		// setup done. Continue to where stuff happens.
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

	private void doSomething() throws IOException {
		System.out.println("1: Write a tweet");
		System.out.println("2: Read all tweets");
		System.out.println("x: Exit");
		System.out.println("What do you want to do?");
		stringScanner = new Scanner(System.in);
		action = stringScanner.next(); //reads a char.

		if(action.equals("1")){		// Ascii Dec value of char '1' is 49.
			

			System.out.println("Please enter your username:");
			author = stringScanner.next();

			System.out.println("What do you want to talk about ? (120 characters)");
			tweet = stringScanner.next();

			timestamp = Calendar.getInstance().getTime();

			System.out.println("send post: ");
			System.out.println("timestamp \"" + timestamp +"\"");
			System.out.println("author: \"" + author +"\"");
			System.out.println("tweet: \"" + tweet +"\"");
		}else {
			//handling if it is a request
			System.out.println("Error. Action had wrong value. ");
			action2 = String.valueOf(System.in.read()); //WHAT IS THIS FOR??
		}
		Post post = new Post(author, tweet, timestamp);				//wrapping in a Post class before sending to the network

		PostSubmission user = new PostSubmission(post);
		output.writeObject(user);
	}
}
