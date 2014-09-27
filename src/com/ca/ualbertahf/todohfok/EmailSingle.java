package com.ca.ualbertahf.todohfok;

import java.io.IOException;

public class EmailSingle {
	private static EmailList emailList = null;
	
	static public EmailList getEmail(){
		if (emailList == null){
			try{
				emailList = EmIO.getIO().loadEmails();
				emailList.addListener(new Listener(){
					@Override
					public void update() {
						saveEmail();
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
		return emailList;
	}

	static public void saveEmail(){
		try{
			EmIO.getIO().saveEmails(getEmail());
		}catch (IOException e){
			e.printStackTrace();
			throw new RuntimeException("could not deserialize");
		}
	}
	
	public void addEmail(Event arc) {
		getEmail().addEvent(arc);
		
	}
	
}
	
