package microblog.kitsch.business.domain;

import java.io.Serializable;

/**
 * 
 */
public class InstantMessage implements Serializable {
	// Variables
	// Instance Variables
	private static final long serialVersionUID = -7589628667660924102L;
	
	private String sender;
	private String receiver;
	private String message;
	
	// Constructor
	public InstantMessage(String sender, String receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

	//Methods
	@Override
	public String toString() {
		return "InstantMessage [sender=" + sender + ", receiver=" + receiver + ", message=" + message + "]";
	}
	
	// Getters
	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getMessage() {
		return message;
	}
	
	// Setters
	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
