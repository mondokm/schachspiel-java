package schachspiel.util;

/**
 * Classes implementing this interface can get notified when a message is received
 * @author milan
 *
 */
public interface NetworkListener {

	/**
	 * Gest called when a message is received
	 * @param message The message
	 */
	public void messageReceived(Message message);
	
}
