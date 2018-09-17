import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Client{
	public static void main(String[] argv) throws Exception{
		
		Socket sock = new Socket("127.0.0.1", 9999);

		Scanner in = new Scanner(System.in);
		DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
		
		System.out.print("folder path: ");
		String folder = in.nextLine();
		System.out.print("filename: ");
		String filename = in.nextLine();
		dos.writeUTF(folder);
		dos.writeUTF(filename);

		

		//file transfer initialise
		Path path = FileSystems.getDefault().getPath(folder, filename);
		byte[] mybytearray = new byte[(int) Files.size(path)];
		BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path));
		bis.read(mybytearray, 0, mybytearray.length);
		OutputStream os = sock.getOutputStream();
		os.write(mybytearray, 0, mybytearray.length);
		os.flush();
		bis.close();
		//file transfer complete


		in.close();
		dos.close();
		sock.close();
	}
  
}