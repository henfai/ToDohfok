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

public class ArcIO {
	
	Context context;
	static final String arcPref = "ArchiveList";
	static final String arcKey = "archiveList";
	static private ArcIO arcIO = null;
	
	public ArcIO(Context context) {
		this.context = context;
	}

	public static void inititalise(Context context){
		if(arcIO== null){
			if (context== null){
				throw new RuntimeException("Missing Context");
			}
			arcIO = new ArcIO(context);
		}
	}

	
	public void saveArcs(ArchiveList str) throws IOException{
		SharedPreferences settings = context.getSharedPreferences(arcPref, Context.MODE_PRIVATE);
		Editor edit = settings.edit();
		edit.putString(arcKey, arcToString(str));
		edit.commit();
		
	}
	
	private String arcToString(ArchiveList str) throws IOException {
		ByteArrayOutputStream byteout = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(byteout);
		oos.writeObject(str);
		oos.close();
		byte bytes[] = byteout.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
		
		
	}
	
	
	public ArchiveList loadArcs() throws StreamCorruptedException, IOException, ClassNotFoundException{
		SharedPreferences settings = context.getSharedPreferences(arcPref, Context.MODE_PRIVATE);
		String storeData = settings.getString(arcKey, "");
		if(storeData.equals("")){
			return new ArchiveList();
		}else{
			return arcFromString(storeData);
		}
		
	}

	private ArchiveList arcFromString(String data) throws StreamCorruptedException, IOException, ClassNotFoundException {
		ByteArrayInputStream byting = new ByteArrayInputStream(Base64.decode(data, Base64.DEFAULT));
		ObjectInputStream ios = new ObjectInputStream(byting);
		return (ArchiveList) ios.readObject();
	}

	public static ArcIO getIO() {
		if (arcIO == null){
			throw new RuntimeException("Did not Initialise");
		}
		return arcIO;
		}
	}


