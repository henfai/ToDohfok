package com.ca.ualbertahf.todohfok;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import com.ca.ualbertahf.ToDoListhfok.R;

public class ArchiveActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive);
		ArcIO.inititalise(this.getApplicationContext());
		setTitle("My archived todo list");
		ListView listview =  (ListView) findViewById(R.id.archiveList);
		Collection<Event> events = ArchiveSingle.getArchive().getEvent();
		final ArrayList<Event> archivelist = new ArrayList<Event>(events);
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
				Event event = archivelist.get(finalPosition);
				ArchiveSingle.getArchive().check(event);
				
			}
			
			
		});
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				AlertDialog.Builder ad = new AlertDialog.Builder(ArchiveActivity.this);
				ad.setMessage("Choose an action for"+ archivelist.get(position).toString());
				ad.setCancelable(true);
				final int finalPosition = position;
				ad.setNegativeButton("Delete", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event arcEve = archivelist.get(finalPosition);
						EventSingle.getEventList().deleteEvent(arcEve);
						
					}
					
				});
				//Crashing for some reason :(
				ad.setNeutralButton("Unarchive", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event arcEve = archivelist.get(finalPosition);
						EventSingle.getEventList().addEvent(arcEve);
						ArchiveSingle.getArchive().deleteEvent(arcEve);
					
						
					}
					
				});
				ad.setNegativeButton("Add To pending Email", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event email = archivelist.get(finalPosition);
						EmailSingle.getEmail().addEvent(email);
					}
					});
					
				ad.show();
				return true;
			}
			
		});
	}
	
	public void Email(MenuItem menu) {
		AlertDialog.Builder abc = new AlertDialog.Builder(ArchiveActivity.this);
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
				    Toast.makeText(ArchiveActivity.this, "This feature is coming soon!!!", Toast.LENGTH_LONG).show();
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(ArchiveActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
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
				    Toast.makeText(ArchiveActivity.this, "This feature is coming soon!!!", Toast.LENGTH_LONG).show();
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(ArchiveActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
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
				    Toast.makeText(ArchiveActivity.this, "This feature is coming soon!!!", Toast.LENGTH_LONG).show();
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(ArchiveActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		abc.show();
		return;

	}
	
	public void pending(MenuItem menu) {
		Toast.makeText(this, "Accessing pending emails", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(ArchiveActivity.this, EmailActivity.class);
		startActivity(intent);
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
