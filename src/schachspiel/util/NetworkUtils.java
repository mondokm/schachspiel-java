package schachspiel.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {

	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket socket;
	ServerSocket serverSocket;
	
	List<NetworkListener> listeners=new ArrayList<NetworkListener>();
	
	public void setupClient(String host, int port) {
		try {
			socket=new Socket(host,port);
			oos=new ObjectOutputStream(socket.getOutputStream());
			ois=new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void closeAll() {
			try {
				if(ois!=null) ois.close();
				if(oos!=null) oos.close();
				if(socket!=null) socket.close();
				if(serverSocket!=null) serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public void sendMessage(Message message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class ObjectReceiver extends Thread{
		public void run() {
			while(true) {
				try {
					Object o=NetworkUtils.this.ois.readObject();
					if(o!=null) {
						for(NetworkListener listener: NetworkUtils.this.listeners) listener.messageReceived((Message) o);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addListener(NetworkListener listener) {
		listeners.add(listener);
	}
	
	public void startListening() {
		ObjectReceiver receiver=new ObjectReceiver();
		receiver.start();
	}
	
}
