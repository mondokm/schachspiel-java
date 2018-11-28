package schachspiel.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for communicating with the other player
 * @author milan
 *
 */
public class NetworkUtils {

	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket socket;
	ServerSocket serverSocket;
	
	ObjectReceiver receiver;
	
	List<NetworkListener> listeners=new ArrayList<NetworkListener>();
	
	/**
	 * Sets up communication in client mode
	 * @param host The host address
	 * @param port The host port
	 */
	public void setupClient(String host, int port) {
		try {
			socket=new Socket(host,port);
			oos=new ObjectOutputStream(socket.getOutputStream());
			ois=new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets up communication in server mode
	 * @param port The port
	 */
	public void setupServer(int port) {
		try {
			serverSocket=new ServerSocket(port);
			socket=serverSocket.accept();
			oos=new ObjectOutputStream(socket.getOutputStream());
			ois=new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes all sockets and streams
	 */
	public void closeAll() {
			try {
				if(ois!=null) ois.close();
				if(oos!=null) oos.close();
				if(socket!=null) socket.close();
				if(serverSocket!=null) serverSocket.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		
	}
	
	/**
	 * Sends a message to the other player
	 * @param message The message
	 */
	public void sendMessage(Message message) {
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	public class ObjectReceiver extends Thread{
		private volatile boolean listening=false;
		public void terminate() {
			listening=false;
		}
		public void run() {
			listening=true;
			while(listening) {
				try {
					Object o=NetworkUtils.this.ois.readObject();
					if(o!=null) {
						for(NetworkListener listener: NetworkUtils.this.listeners) listener.messageReceived((Message) o);
					}
				} catch (Exception e) {
//					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * Registers a new listener
	 * @param listener The listener
	 */
	public void addListener(NetworkListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Starts receiving messages
	 */
	public void startListening() {
		receiver=new ObjectReceiver();
		receiver.start();
	}
	
	/**
	 * Stops receiving messages
	 */
	public void stopListening() {
		receiver.terminate();
	}
	
}
