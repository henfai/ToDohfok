package com.ca.ualbertahf.todohfok;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class EventSingle {
	private static EventList eventList = null;
	///private static EventList emaillist = null;
	
	static public EventList getEventList(){
		if (eventList == null){
			try{
				eventList = EveIO.getIO().loadEvents();
				eventList.addListener(new Listener(){
					@Override
					public void update() {
						saveEvent();
					}
				});
			}catch (ClassNotFoundException e){
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize StudentList from StudentListManager");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize StudentList from StudentListManager");
			}
		}
		return eventList;
	}
	
	static public void saveEvent(){
		try{
			EveIO.getIO().saveEvents(getEventList());
		}catch (IOException e){
			e.printStackTrace();
			throw new RuntimeException("could not deserialize");
		}
	}
	
	public void addEvent(Event event) {
		getEventList().addEvent(event);
		
	}
	
	///public void set(ArrayList<Event> emails){
	///	getEmails().set(emails);
	///}

	
	///static public TodoList getEmails(){
///		if (emaillist != null){
///			return emaillist;
///		}else{
///			emaillist = new TodoList();
///		}
///		return emaillist;
///		}
	}


