import java.io.*;
import java.net.*;


public class client {
	private static int userNum = (int) (Math.random() * 9000);
	/**
	 * @param args Should be a single input either 0 or 1 depending on which excersize should be run.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if(args[0].equals("0")){
			ex0();
		}else if(args[0].equals("1")){
			ex1();
		}else{
			System.out.println("Error, invalid arguments. Please enter either 0 or 1.");
			System.exit(1);
		}
		
	}
	
	//This is the method to run exercise one, where the client acts like a server
	private static void ex1() throws IOException {
		// TODO Do some of the same things as in ex0() but also have a while loop checking the input from the 
		// server to see if it matches (regEx) the required properly formated awk string.
		Socket sockLeft = null;
		ServerSocket sockRight = null;
		PrintWriter out = null; // output stream which will be attached to the socket
		BufferedReader in = null; // input stream which will be attached to the socket
		
		//Initialize the client socket (sockLeft)
		try{
			sockLeft = new Socket("venice.cs.utexas.edu", 35600);
			out = new PrintWriter(sockLeft.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sockLeft.getInputStream()));
		}catch (UnknownHostException e){
			System.err.println("Can't find host: venice.cs.utexas.edu");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Can't get an I/O connection to host: venice.cs.utexas.edu");
			System.exit(1);
		}
		//Initialize the server socket (sockRight)
		try{
			sockRight = new ServerSocket(0);
		}catch(IOException e){
			System.out.println("Could not find a free port. Aborting....");
		    System.exit(-1);
		}
		
		String my_ip = InetAddress.getLocalHost().getHostAddress();
		int my_port = sockRight.getLocalPort(); // Now the port we send is for the SERVER socket, so that the venice server will connect to our serverSocket
		
		out.println("ex1 128.83.120.202-35600 "+ my_ip + "-" + my_port + " " + userNum + " J.A.Shield\n"); //Send first request
		//**Read responses**
		String firstResponse = in.readLine(); // receive first awk 
		String secondResponse = in.readLine(); // receive second awk
		
		//Wait for connection to serversocket
		
		
	}

	//This is the method to run exercise zero, the 4 way handshake.
	public static void ex0() throws IOException{
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
		
		out.println("ex0 128.83.120.202-35600 "+ my_ip + "-" + my_port + " " + userNum + " J.A.Shield\n"); //Send first request
		
		//**Read responses**
		String firstResponse = in.readLine(); // receive first awk 
		String secondResponse = in.readLine(); // receive second awk

		
		System.out.println(firstResponse + "*");
		System.out.println(secondResponse+ "**");
		
		// Split the server's second response so we can use the userID and serverID
		String[] pieces = secondResponse.split("\\s+");
		if(pieces.length != 4 || !pieces[0].equals("OK")){ // If we don't get OK or there is some other problem, exit.
			System.err.println("Cannot continue due to improper server response.");
			out.close();
			in.close();
			sock.close();
			System.exit(1);
		}
		
		int incServID = Integer.parseInt(pieces[3]) + 1; // Get the server ID, convert to an int, then increment 
		
		out.println("ex0 " + pieces[1] + " " + incServID + "\n"); // Send awk
		
		String thirdResponse = in.readLine(); // receive third awk
		System.out.println(thirdResponse + "***");
		
		if(!thirdResponse.split("\\s+")[9].equals("OK")){
			System.err.println("Server response not 'OK'. Error! Aborting");
			out.close();
			in.close();
			sock.close();
			System.exit(1);
		}
		
		
		out.close();
		in.close();
		sock.close();
		System.out.println("Done.");
	}

}
