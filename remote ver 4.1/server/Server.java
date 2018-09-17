import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Server{
	private static final int maxClientsCount = 10;
	private static final clientThread[] threads = new clientThread[maxClientsCount];

	public static void main(String[] args){
	    try{
	    	ServerSocket servsock = new ServerSocket(9999);
	    	while(true){
	    		Socket clientSocket = servsock.accept();
		        int i = 0;
		        for (i = 0; i < maxClientsCount; i++) {
		        	if (threads[i] == null) {
		        		(threads[i] = new clientThread(clientSocket, threads)).start();
		            	break;
		          	}
		        }
		        if (i == maxClientsCount) {
		        	PrintStream os = new PrintStream(clientSocket.getOutputStream());
			        os.println("Server too busy. Try later.");
			        os.close();
			        clientSocket.close();
		        }
	    	}
	    } catch(IOException e){
	    		System.out.println(e);
	    }
	}
}
