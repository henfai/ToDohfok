package com.ca.ualbertahf.todohfok;


import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4942213761754334191L;
	protected String eventItem;
	
	public Event (String eventItem){
		this.eventItem = eventItem;
	}
	
	public String getEventText() {
		return eventItem;
		
	}
	public String toString(){
		return getEventText();
	}




}
