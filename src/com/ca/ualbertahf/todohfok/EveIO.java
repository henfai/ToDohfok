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

public class EveIO {
	
	Context context;
	static final String evPref = "EventList";
	static final String evKey = "eventList";
	static private EveIO eveIO = null;
	
	public EveIO(Context context) {
		this.context = context;
	}

	public static void inititalise(Context context){
		if(eveIO== null){
			if (context== null){
				throw new RuntimeException("Missing Context");
			}
			eveIO = new EveIO(context);
		}
	}
	
	public EventList loadEvents() throws StreamCorruptedException, IOException, ClassNotFoundException{
		SharedPreferences settings = context.getSharedPreferences(evPref, Context.MODE_PRIVATE);
		String data = settings.getString(evKey, "");
		if(data.equals("")){
			return new EventList();
		}else{
			return eventFromString(data);
		}
		
	}
	
	public void saveEvents(EventList tdl) throws IOException{
		SharedPreferences settings = context.getSharedPreferences(evPref, Context.MODE_PRIVATE);
		Editor edit = settings.edit();
		edit.putString(evKey, eventToString(tdl));
		edit.commit();
		
	}
	
	private String eventToString(EventList tdl) throws IOException {
		ByteArrayOutputStream bytout = new ByteArrayOutputStream();
		ObjectOutputStream objout = new ObjectOutputStream(bytout);
		objout.writeObject(tdl);
		objout.close();
		byte bytes[] = bytout.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
		
		
	}

	private EventList eventFromString(String data) throws StreamCorruptedException, IOException, ClassNotFoundException {
		ByteArrayInputStream bytin = new ByteArrayInputStream(Base64.decode(data, Base64.DEFAULT));
		ObjectInputStream objIn = new ObjectInputStream(bytin);
		return (EventList) objIn.readObject();
	}

	public static EveIO getIO() {
		if (eveIO == null){
			throw new RuntimeException("NotInit");
		}
		return eveIO;
		}
	}


