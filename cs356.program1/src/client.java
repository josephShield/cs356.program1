import java.io.*;
import java.net.*;


public class client {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		Socket sock = null;
		PrintWriter out = null; // output stream which will be attached to the socket
		BufferedReader in = null; // input stream which will be attached to the socket
		
		try{
			sock = new Socket("venice.cs.utexas.edu", 35600);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
		}catch (UnknownHostException e){
			System.err.println("Can't find host: venice.cs.utexas.edu");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Can't get an I/O connection to host: venice.cs.utexas.edu");
			System.exit(1);
		}
		String my_ip = InetAddress.getLocalHost().getHostAddress();
		int my_port = sock.getLocalPort();
		
		out.println("ex0 128.83.120.202-35600 "+ my_ip + "-" + my_port + " 5555 J.A.Shield\n"); //Send first request
		
		//**Read responses**
		String firstResponse = in.readLine(); // receive first awk 
		String secondResponse = in.readLine(); // receive second awk

		
		System.out.println(firstResponse + "*");
		System.out.println(secondResponse+ "**");
		
		// Split the server's second response so we can use the userID and serverID
		String[] pieces = secondResponse.split("\\s+");
		if(pieces.length != 4 || !pieces[0].equals("OK")){ // If we don't get OK or there is some other problem, exit.
			System.err.println("Cannot continue due to improper server response.");
			System.exit(1);
		}
		
		int incServID = Integer.parseInt(pieces[3]) + 1; // Get the server ID, convert to an int, then increment 
		
		out.println("ex0 " + pieces[1] + " " + incServID + "\n"); // Send awk
		
		String thirdResponse = in.readLine(); // receive third awk
		System.out.println(thirdResponse + "***");
		
		
		
		out.close();
		in.close();
		sock.close();
		System.out.println("Done.");
	}

}
