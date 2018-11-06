import java.io.*;
import java.net.*;
import java.awt.*; // for creating interfaces
import java.awt.event.*;
import javax.swing.*; // also interface

public class Server extends JFrame{ 	// extends GUI

	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;

	// constructor
	public Server() {
		super("Server started...");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			}
		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300,150);
		setVisible(true);
	}

	// setup of server
	public void startRunning(){
		try{
			server = new ServerSocket(4040, 100);	// portnumber, 100 can wait at the port (called backlog)
			while(true){	// look forever!
				try{
					waitForConnection();
					setupStreams();
					whileChatting();					
				}catch(EOFException eofException){	// when someone ends the connection, the following message will be displayed. 
					showMessage("\n Server ended the connection, lol.");
				}finally{
					closeCrap();
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	// wait for connection and displaying of connection information
	private void waitForConnection() throws IOException{
		showMessage("Waiting for someone to connect... \n");
		connection = server.accept(); // accepts if connection and creates socket
	showMessage("\n You are connected.");
	//	showMessage("You are connected to: " + connection.getInetAdress().getHostName());
	}

	// Get stream to send and receive data
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();	
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are now setup. \n");
	}

	// during chatting
	private void whileChatting() throws IOException{
		String message = "You are now connected.";
		sendMessage(message);
		ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("\n wut did user send??");
			}
		}while(!message.equals("CLIENT - END"));
	}	

	// to close streams and sockets after use
	private void closeCrap(){
		showMessage("\n Connection terminated. ");
		ableToType(false);
		try{
			output.close();	// closes stream io
			input.close();
			connection.close();	// closes socket to not waste memory
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	// sends message to client
	private void sendMessage(String message){
		try{
			output.writeObject("SERVER - "+ message);
			output.flush();	// flush leftover bytes
			showMessage("\nSERVER - " + message);	// show what I just wrote
		}catch(IOException ioException){
			chatWindow.append("\n ERROR: DUDE I CANT SEND");
		}
	}

	// Updates chat window
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(text);
				}
			}
		);
	}

	// Let user type into box
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(tof);
				}
			}
		);		
	}
}
