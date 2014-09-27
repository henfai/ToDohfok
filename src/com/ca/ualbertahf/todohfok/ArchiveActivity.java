package com.ca.ualbertahf.todohfok;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ArchiveActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive);
		ArcIO.inititalise(this.getApplicationContext());

		ListView listview =  (ListView) findViewById(R.id.archiveList);
		Collection<Event> todos = ArchiveSingle.getArchive().getEvent();
		final ArrayList<Event> archivelist = new ArrayList<Event>(todos);
		final ArrayAdapter<Event> archiveAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1,archivelist);
		listview.setAdapter(archiveAdapter);
		
		ArchiveSingle.getArchive().addListener(new Listener(){

			@Override
			public void update() {
				archivelist.clear();
				Collection<Event> arc = ArchiveSingle.getArchive().getEvent();
				archivelist.addAll(arc);
				archiveAdapter.notifyDataSetChanged();
			}
			
			
		});
		
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				final int finalPosition = position;
				Event arcEve = archivelist.get(finalPosition);
				///ArchiveSingle.getArchive().check(arcEve);
				
			}
			
			
		});
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				AlertDialog.Builder ad = new AlertDialog.Builder(ArchiveActivity.this);
				ad.setMessage("Delete "+ archivelist.get(position).toString()+"?");
				ad.setCancelable(true);
				final int finalPosition = position;
				ad.setPositiveButton("Delete", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event arcEve = archivelist.get(finalPosition);
						EventSingle.getEventList().deleteEvent(arcEve);
						
					}
					
				});
				//Crashing for some reason :(
				ad.setNeutralButton("UnArchive", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event arcEve = archivelist.get(finalPosition);
						EventSingle.getEventList().addEvent(arcEve);
						ArchiveSingle.getArchive().deleteEvent(arcEve);
					
						
					}
					
				});
				ad.setNegativeButton("AddToEmail", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event todo = archivelist.get(finalPosition);
						//emails.addTodo(todo);
						//TodoSingle.getEmails().addTodo(todo);
					}
					});
					
				ad.show();
				return false;
			}
			
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archive, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
