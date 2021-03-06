import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Client{
	public static void main(String[] argv) throws Exception{
		// The default port.
	    int portNumber = 10000;
	    // The default host.
	    String host = "127.0.0.1";

	    if (argv.length < 1) {
	    	System.out.println("Usage: java Client <host ip>");
	    }
	    else{
	    	host = argv[0];
	    }
	    System.out.println("Now connecting to MainServer at " + host + ":" + portNumber);
		Socket sock = new Socket(host, portNumber);

		Scanner in = new Scanner(System.in);
    	DataInputStream din = new DataInputStream(sock.getInputStream());		
		DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
		
		// System.out.println("Press 1:To use a service");
		// int choice = in.nextInt();
		dos.writeInt(1);
		dos.flush();
		System.out.println( din.readUTF() );
		int choice = in.nextInt();
		dos.writeInt(choice);
		dos.flush();

		String ip = din.readUTF();
		// if(ip.equals("Service not found")){
		// 	System.out.println(ip);
		// 	return;
		// }

		din.close();
		dos.close();
		sock.close();
		//End connection with mainserver

		//start connection with host
		portNumber = 9999;
		System.out.println("Now connecting to " + ip + ":" + portNumber);
		sock = new Socket(ip, portNumber);

    	din = new DataInputStream(sock.getInputStream());		
		dos = new DataOutputStream(sock.getOutputStream());
		
		String folder;
		folder = in.nextLine();
		folder = "";
		while(true){
			System.out.print("filename (\"exit\" to complete file transfer): ");
			String filename = in.nextLine();			
			// System.out.println(filename);
			dos.writeUTF(filename);
			dos.flush();
			if(filename.equals("exit")) break;

			//file transfer initialise
			Path path = FileSystems.getDefault().getPath(filename);
			int fileSize = (int) Files.size(path);
			dos.writeInt(fileSize);
			byte[] mybytearray = new byte[fileSize];
			BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path));
			bis.read(mybytearray, 0, mybytearray.length);
			OutputStream os = sock.getOutputStream();
			os.write(mybytearray, 0, mybytearray.length);
			os.flush();
			bis.close();
			//file transfer complete
		}

		while(true){
			System.out.print("command (q to quit): ");
			String command=in.nextLine();
			// System.out.println(command);
			dos.writeUTF(command);
			dos.flush();
			if(command.equals("q")){
				break;
			}
			else{
				while(true){
					String line=din.readUTF();
					if(line.equals("")) break;
					System.out.println(line);
				}
			}
		}

		in.close();
		din.close();
		dos.close();
		sock.close();
	}
  
}
