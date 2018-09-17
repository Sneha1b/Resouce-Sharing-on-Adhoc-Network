import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Client{
	public static void main(String[] argv) throws Exception{
		
	    int portNumber = 9999;
	    String host = "127.0.0.1";

	    if (argv.length < 1) 
	    {
	    	System.out.println("Usage: java Client <host ip> \n");
	    }
	    else
	    {
	    	host = argv[0];
	    }

	    System.out.println("Now connecting to host=" + host + ", portNumber=" + portNumber);
		
		Socket sock = new Socket(host, portNumber);

		Scanner in = new Scanner(System.in);
    		DataInputStream din = new DataInputStream(sock.getInputStream());		
		DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
		
		String filename = in.nextLine();
		dos.writeUTF(filename);
		dos.flush();
		String out = din.readUTF();
		System.out.println(out);


		

		in.close();
		dos.close();
		sock.close();
	}
  
}
