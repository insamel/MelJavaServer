package client;
import request.PostSubmission;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private int action;
	private String author;
	private String tweet;
	private Date timestamp;

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
		action = System.in.read(); //reads a char.

		if(action == 49){		// Ascii Dec value of char '1' is 49.
			Scanner stringScanner = new Scanner(System.in);		// input string scanner using java Scanner class

			System.out.println("Please enter your username:");
			author = stringScanner.next();

			System.out.println("What do you want to talk about ? (120 characters)");
			tweet = stringScanner.next();

			timestamp = Calendar.getInstance().getTime();

			System.out.println("send post: ");
			System.out.println("timestamp \"" + timestamp +"\"");
			System.out.println("author: \"" + author +"\"");
			System.out.println("tweet: \"" + tweet +"\"");
			PostSubmission user = new PostSubmission(author, tweet, timestamp);				//wrapping in a PostSubmission class before sending to the network
		}else {
			//handling if it is a request
			System.out.println("Error. Action had wrong value. ");
			String action = String.valueOf(System.in.read()); //if else for other letters
		}

		PostSubmission user = new PostSubmission(author, tweet, timestamp);
		output.writeObject(user);
	}
}