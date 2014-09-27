package com.ca.ualbertahf.todohfok;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ArchiveList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1685788510449625327L;
	protected ArrayList<Event> arcList;
	protected transient ArrayList<Listener> listeners;

	
	public ArchiveList(){
		arcList = new ArrayList<Event>();
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
		return arcList;

	}
	
	public void addEvent(Event event){
		arcList.add(event);
		notifyListen();
	}
	

	public void cross(Event eventItem) {
	}

	public void deleteEvent(Event event){
		arcList.remove(event);
		notifyListen();
	}
	
	public void addListener(Listener l){
		getListeners().add(l);
	}
	
	public void removeListener(Listener l){
		getListeners().remove(l);
	}
	

	public void clear(){
		arcList = new ArrayList<Event>();
		notifyListen();
	}


}
