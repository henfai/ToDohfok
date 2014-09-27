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
	
	
	
	public static String combineEmailChecked(ArrayList<Event> temp){
		String comboString = "";
		for (Event eEvent : temp) {
			if (eEvent.getEventText().contains("\u2573")){
				comboString = comboString + eEvent.getEventText() + "\n";
			}
		}	
		return comboString;
	}
	
	public static String combineEmailUnchecked(ArrayList<Event> temp){
		String comboString = "";
		for (Event eEvent : temp) {
		if (eEvent.getEventText().contains("\u2573") == false){
			comboString = comboString + eEvent.getEventText() + "\n";
			}
		}
		
		return comboString;
	}
	
	public String eventActCount(){
		int activeTotalCount = 0;
		int activeCheckedCount = 0;
		for (Event cEvent : eventList) {
			if (cEvent.toString().contains("\u2573")){
				activeCheckedCount++;
			}
			activeTotalCount++;
		}
		
		String combActive = String.valueOf(activeCheckedCount) + " " + String.valueOf(activeTotalCount);
		return combActive;
		
	}
	
	public void clear(){
		eventList = new ArrayList<Event>();
		notifyListen();
	}


}
