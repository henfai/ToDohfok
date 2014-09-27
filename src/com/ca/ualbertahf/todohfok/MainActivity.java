package com.ca.ualbertahf.todohfok;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EveIO.init(this.getApplicationContext());
		//ArcIO.init(this.getApplicationContext());


		ListView listview =  (ListView) findViewById(R.id.eventList);
		Collection<Event> Events = EventSingle.getEventList().getEvent();
		final ArrayList<Event> Eventslist = new ArrayList<Event>(Events);
		final ArrayAdapter<Event> EventsAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, Eventslist);
		listview.setAdapter(EventsAdapter);
		

		EventSingle.getEventList().addListener(new Listener(){

			@Override
			public void update() {
				Eventslist.clear();
				Collection<Event> Events = EventSingle.getEventList().getEvent();
				Eventslist.addAll(Events);
				EventsAdapter.notifyDataSetChanged();
			}
			
			
		});
		
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				final int finalPosition = position;
				Event Event = Eventslist.get(finalPosition);
				///EventSingle.getEventList().check(Event);
				
			}
			
		});
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
				ad.setMessage("Delete "+ Eventslist.get(position).toString()+"?");
				ad.setCancelable(true);
				final int finalPosition = position;
				ad.setPositiveButton("Delete", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event Event = Eventslist.get(finalPosition);
						EventSingle.getEventList().deleteEvent(Event);
						
					}
					
				});
				//Crashing for some reason :(
				ad.setNeutralButton("Archive", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event Event = Eventslist.get(finalPosition);
						EventSingle.getEventList().deleteEvent(Event);
						///ArchiveSingle.getArchive().addEvent(Event);
					
						
					}
					
				});
				ad.setNegativeButton("AddToEmail", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event Event = Eventslist.get(finalPosition);
						///EventSingle.getEmails().addEvent(Event);
						
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void archive(MenuItem menu) {
		Toast.makeText(this, "Archive", Toast.LENGTH_SHORT).show();
		////Intent intent = new Intent(MainActivity.this, ArchiveActivity.class);
		////startActivity(intent);
	}

	public void summarize(MenuItem menu) {
		//Toast.makeText(this, "summarize", Toast.LENGTH_SHORT).show();
		///Intent intent = new Intent(MainActivity.this, SummarizeActivity.class);
		///startActivity(intent);
	}
	
	public void Email(MenuItem menu) {
		///Intent intent = new Intent(this, EmailActivity.class);
		///startActivity(intent);
	}

	public void addEvent(View v){
		//Toast.makeText(this, "Add Event",Toast.LENGTH_SHORT).show();
		EventSingle ts = new EventSingle();
		EditText newEventText = (EditText) findViewById(R.id.eventText);
		if ((newEventText.getText().toString().trim().length()) == 0 ){
			Toast.makeText(this, "Please enter a Event",Toast.LENGTH_SHORT).show();
		}else{
			ts.addEvent(new Event(newEventText.getText().toString()));
		}
		newEventText.setText("");
		
	}
}