
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Servermain{
	public static Map <String,String> database = new HashMap<String,String>();
	public static void main(String[] args){

	    try{
		ServerSocket servsock = new ServerSocket(9999);
		
    		
	    	while(true){
	    		Socket clientSocket = servsock.accept();
			String ip = clientSocket.getInetAddress().getHostAddress();
			int p = clientSocket.getPort();
			String a = ip + ":" + p;
			
			PrintStream os = new PrintStream(clientSocket.getOutputStream());			
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			String b = in.readUTF();
			String[] use=b.split(" ");

			if(use[0].equals("remove"))
			{
			database.remove(a);
			}
			else
			{
				if(use[0].equals("server"))
					database.put(a,use[1]);
				else	
				{	
					for (Map.Entry<String,String> entry : database.entrySet()) 
			        	System.out.println(database.get(use[0]));
			 
				}       
			}


	    	}
	    } catch(IOException e){
	    		System.out.println(e);
	    }
	}
}
