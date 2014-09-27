package com.ca.ualbertahf.todohfok;

import java.io.IOException;

public class ArchiveSingle {
	private static ArchiveList arcList = null;

	
	static public ArchiveList getArchive(){
		if (arcList == null){
			try{
				arcList = ArcIO.getIO().loadArcs();
				arcList.addListener(new Listener(){
					@Override
					public void update() {
						saveArc();
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
		return arcList;
	}
	
	static public void saveArc(){
		try{
			ArcIO.getIO().saveArcs(getArchive());
		}catch (IOException e){
			e.printStackTrace();
			throw new RuntimeException("could not deserialize");
		}
	}
	
	public void addArc(Event arc) {
		getArchive().addEvent(arc);
		
	}
	
}
	
