/* 	
 	ToDohfok: Simple todo android app
 	
	Copyright (C) 2014  Henry Fok <hfok@ualberta.ca>, Abram Hindle <abram.hindle@softwareprocess.es>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/



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
import com.ca.ualbertahf.ToDoListhfok.R;



public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		///everything initialises on start
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("My Todo List");
		EveIO.inititalise(this.getApplicationContext());
		ArcIO.inititalise(this.getApplicationContext());
		EmIO.inititalise(this.getApplicationContext());
		ListView listview =  (ListView) findViewById(R.id.eventList);
		Collection<Event> Events = EventSingle.getEventList().getEvent();
		final ArrayList<Event> Eventslist = new ArrayList<Event>(Events);
		final ArrayAdapter<Event> EventsAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, Eventslist);
		listview.setAdapter(EventsAdapter);
		
		///singleton
		EventSingle.getEventList().addListener(new Listener(){

			@Override
			public void update() {
				Eventslist.clear();
				Collection<Event> Events = EventSingle.getEventList().getEvent();
				Eventslist.addAll(Events);
				EventsAdapter.notifyDataSetChanged();
			}
			
			
		});
		///click listener
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				final int finalPosition = position;
				Event event = Eventslist.get(finalPosition);
				EventSingle.getEventList().check(event);
				}

			
		});
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
				ad.setMessage("Choose an action for"+ Eventslist.get(position).toString());
				ad.setCancelable(true);
				final int finalPosition = position;
				ad.setNegativeButton("Delete", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event Event = Eventslist.get(finalPosition);
						EventSingle.getEventList().deleteEvent(Event);
						
					}
					
				});
				//Crashing for some reason :(
				ad.setPositiveButton("Archive", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event Event = Eventslist.get(finalPosition);
						EventSingle.getEventList().deleteEvent(Event);
						ArchiveSingle.getArchive().addEvent(Event);
					
						
					}
					
				});
				ad.setNeutralButton("Add To pending Email", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {;
						Event email = Eventslist.get(finalPosition);
						EmailSingle.getEmail().addEvent(email);						
					}
					});
					
				ad.show();
				return true;
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	///menu items 
	public void archive(MenuItem menu) {
		Toast.makeText(this, "Acessing Archive", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MainActivity.this, ArchiveActivity.class);
		startActivity(intent);
	}

	public void stats(MenuItem menu) {
		Toast.makeText(this, "Accessing Stats", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MainActivity.this, StatsActivity.class);
		startActivity(intent);
	}
	///dialog alerts for more options
	public void Email(MenuItem menu) {
		AlertDialog.Builder abc = new AlertDialog.Builder(MainActivity.this);
		abc.setMessage("Choose an action");
		abc.setCancelable(true);
		abc.setNegativeButton("Email All Active", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent send = new Intent(Intent.ACTION_SEND);
				send.setType("message/rfc822");
				///send.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});	
				send.putExtra(Intent.EXTRA_SUBJECT, "My Todo List");
				send.putExtra(Intent.EXTRA_TEXT   , "Todo List:\n_________\n\n");
				try {
				    startActivity(Intent.createChooser(send, "Send mail..."));
				    Toast.makeText(MainActivity.this, "This feature is coming soon!!!", Toast.LENGTH_LONG).show();
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
															
			}										
		});
		abc.setPositiveButton("Email All Checked", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent send = new Intent(Intent.ACTION_SEND);
				send.setType("message/rfc822");
				send.putExtra(Intent.EXTRA_SUBJECT, "My Todo List");
				send.putExtra(Intent.EXTRA_TEXT   , "Todo List:\n_________\n\n");
				try {
				    startActivity(Intent.createChooser(send, "Send mail..."));
				    Toast.makeText(MainActivity.this, "This feature is coming soon!!!", Toast.LENGTH_LONG).show();
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
				
			}										
		});
		
		abc.setNeutralButton("Email All Unchecked", new OnClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent send = new Intent(Intent.ACTION_SEND);
				send.setType("message/rfc822");
				///send.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});	
				send.putExtra(Intent.EXTRA_SUBJECT, "My Todo List");
				send.putExtra(Intent.EXTRA_TEXT   , "Todo List:\n_________\n\n");
				try {
				    startActivity(Intent.createChooser(send, "Send mail..."));
				    Toast.makeText(MainActivity.this, "This feature is coming soon!!!", Toast.LENGTH_LONG).show();
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		abc.show();
		return;

	}
	
	public void pending(MenuItem menu) {
		Toast.makeText(this, "Accessing pending emails", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MainActivity.this, EmailActivity.class);
		startActivity(intent);
	}
	///adds events from the text box to the list
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
