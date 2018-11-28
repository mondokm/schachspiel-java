package schachspiel.util;

import java.io.Serializable;

/**
 * A message to be sent to the other player
 * @author milan
 *
 */
public class Message implements Serializable{

	/**
	 * The type of the message
	 * @author milan
	 *
	 */
	public enum Type {
		/**
		 * Sent if the player makes a step
		 */
		STEP, 
		/**
		 * Sent if the player wins the game
		 */
		WINNER, 
		/**
		 * Sent in the beginning of the game to let the client know its side
		 */
		SIDE, 
		/**
		 * Sent if the player closes the game
		 */
		CLOSED};
	
	private String string;
	private Type type;
	
	/**
	 * Creates a message with the given type and text
	 * @param type The type of the message
	 * @param string The text of the message
	 */
	public Message(Type type, String string) {
		this.type=type;
		this.string=string;
	}
	
	/**
	 * Creates a message with the given type
	 * @param type The type of the message
	 */
	public Message(Type type) {
		this.type=type;
	}
	
	/**
	 * Returns the type of the message
	 * @return The type of the message
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Returns the text of the message
	 * @return The text of the message
	 */
	public String getString() {
		return string;
	}
	
}
