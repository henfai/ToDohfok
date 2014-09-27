package com.ca.ualbertahf.todohfok;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class EmIO {
	
	Context context;
	static final String emPref = "EmailList";
	static final String emKey = "emailList";
	static private EmIO emIO = null;
	
	public EmIO(Context context) {
		this.context = context;
	}

	public static void inititalise(Context context){
		if(emIO== null){
			if (context== null){
				throw new RuntimeException("Missing Context");
			}
			emIO = new EmIO(context);
		}
	}
	
	public EmailList loadEmails() throws StreamCorruptedException, IOException, ClassNotFoundException{
		SharedPreferences settings = context.getSharedPreferences(emPref, Context.MODE_PRIVATE);
		String data = settings.getString(emKey, "");
		if(data.equals("")){
			return new EmailList();
		}else{
			return eventFromString(data);
		}
		
	}
	
	private EmailList eventFromString(String data) throws StreamCorruptedException, IOException, ClassNotFoundException {
		ByteArrayInputStream bytin = new ByteArrayInputStream(Base64.decode(data, Base64.DEFAULT));
		ObjectInputStream objIn = new ObjectInputStream(bytin);
		return (EmailList) objIn.readObject();
	}

	
	public void saveEmails(EmailList str) throws IOException{
		SharedPreferences settings = context.getSharedPreferences(emPref, Context.MODE_PRIVATE);
		Editor edit = settings.edit();
		edit.putString(emKey, eventToString(str));
		edit.commit();
		
	}
	
	private String eventToString(EmailList str) throws IOException {
		ByteArrayOutputStream bytout = new ByteArrayOutputStream();
		ObjectOutputStream objout = new ObjectOutputStream(bytout);
		objout.writeObject(str);
		objout.close();
		byte bytes[] = bytout.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
		
		
	}

	
	public static EmIO getIO() {
		if (emIO == null){
			throw new RuntimeException("NotInit");
		}
		return emIO;
		}
	}


