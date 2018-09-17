import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Server{
	public static void main(String[] args) throws IOException{
	    ServerSocket servsock = new ServerSocket(9999);
	    while (true) {
	    	Socket sock = servsock.accept();
	    	
	    	DataInputStream in = new DataInputStream(sock.getInputStream());
	    	String folder = in.readUTF();
	    	String filename = in.readUTF();	    	

			//transfer file	    	
	    	Path path = FileSystems.getDefault().getPath(folder, filename);
	    	byte[] mybytearray = new byte[1024];
	    	InputStream is = sock.getInputStream();
			BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(path));
			int bytesRead = is.read(mybytearray, 0, mybytearray.length);
			bos.write(mybytearray, 0, bytesRead);
			bos.close();
			//transfer complete



	    	
			sock.close();
	    }
	}
}