public class ClientTest {
    public static void main(String... args){	
		Client test = new Client("127.0.0.1"); // localhost
		while(true){  
			test.startRunning();
		}		
		//clientSocket = serverSocket.accept();	// waits until client start up, and request connection on the host and port of the server. 
    }
}
