package com.ca.ualbertahf.todohfok;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class EventSingle {
	private static EventList todolist = null;
	///private static EventList emaillist = null;
	
	static public EventList getEventList(){
		if (todolist == null){
			try{
				todolist = EveIO.getIO().loadEvents();
				todolist.addListener(new Listener(){
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
		return todolist;
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


