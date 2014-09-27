package com.ca.ualbertahf.todohfok;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class EmailList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1516066072919849529L;
	protected ArrayList<Event> mailList;
	protected transient ArrayList<Listener> listeners;

	
	public EmailList(){
		mailList = new ArrayList<Event>();
		listeners = new ArrayList<Listener>();
		
	}
	

	private void notifyListen() {
		for(Listener listener: listeners){
			listener.update();
		}
	}
	
	private ArrayList<Listener> getListeners() {
		if (listeners == null ) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	
	public Collection<Event> getEvent(){
		return mailList;

	}
	
	public void addEvent(Event event){
		mailList.add(event);
		notifyListen();
	}
	

	public void cross(Event eventItem) {
	}

	public void deleteEvent(Event event){
		mailList.remove(event);
		notifyListen();
	}
	
	public void addListener(Listener l){
		getListeners().add(l);
	}
	
	public void removeListener(Listener l){
		getListeners().remove(l);
	}
	

	public void clear(){
		mailList = new ArrayList<Event>();
		notifyListen();
	}


}
