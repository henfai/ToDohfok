package com.ca.ualbertahf.todohfok;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import android.R.string;

public class EventList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8549089458087045764L;
	protected ArrayList<Event> eventList;
	protected transient ArrayList<Listener> listeners;

	
	public EventList(){
		eventList = new ArrayList<Event>();
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
		return eventList;

	}
	
	public void addEvent(Event event){
		eventList.add(event);
		notifyListen();
	}

	public void deleteEvent(Event event){
		eventList.remove(event);
		notifyListen();
	}
	
	public void check(Event event) {
		if (event.toString().contains("\u2573")){
			int index1 = eventList.indexOf(event);
			eventList.remove(event);
			Event event2 = new Event(event.getEventText().replace("\u2573  ", ""));
			eventList.add(index1,event2);
			notifyListen();
		}
		else{
			int index1 = eventList.indexOf(event);
			eventList.remove(event);
			Event event2 = new Event ("\u2573  " + event.getEventText());
			eventList.add(index1,event2);
			notifyListen();
		}
		
	}
	
	public void addListener(Listener l){
		getListeners().add(l);
	}
	
	public void removeListener(Listener l){
		getListeners().remove(l);
	}
	

	public void clear(){
		eventList = new ArrayList<Event>();
		notifyListen();
	}


}
