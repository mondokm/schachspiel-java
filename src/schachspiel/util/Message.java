package schachspiel.util;

import java.io.Serializable;

public class Message implements Serializable{

	public enum Type {STEP, WINNER, SIDE};
	
	private String string;
	private Type type;
	
	public Message(Type type, String string) {
		this.type=type;
		this.string=string;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getString() {
		return string;
	}
	
}
