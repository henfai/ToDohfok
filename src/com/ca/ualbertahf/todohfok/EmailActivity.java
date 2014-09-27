package com.ca.ualbertahf.todohfok;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import com.ca.ualbertahf.ToDoListhfok.R;

public class EmailActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
		setTitle("My pending email");
		EmIO.inititalise(this.getApplicationContext());
		ListView listview =  (ListView) findViewById(R.id.pendingEmail);
		Collection<Event> email = EmailSingle.getEmail().getEvent();
		final ArrayList<Event> emaillist = new ArrayList<Event>(email);
		final ArrayAdapter<Event> emailAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1,emaillist);
		listview.setAdapter(emailAdapter);
		
		ArchiveSingle.getArchive().addListener(new Listener(){

			@Override
			public void update() {
				emaillist.clear();
				Collection<Event> arc = ArchiveSingle.getArchive().getEvent();
				emaillist.addAll(arc);
				emailAdapter.notifyDataSetChanged();
			}
			
			
		});
		
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				AlertDialog.Builder ad = new AlertDialog.Builder(EmailActivity.this);
				ad.setMessage("Choose an action for"+ emaillist.get(position).toString());
				ad.setCancelable(true);
				final int finalPosition = position;
				ad.setPositiveButton("Delete", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Event emEve = emaillist.get(finalPosition);
						EmailSingle.getEmail().deleteEvent(emEve);
						
					}
					
				});
				ad.setNegativeButton("Cancel", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
					});
					
				ad.show();
				return true;
			}
			
		});
	}
	
	public void sending(View v){
		Intent send = new Intent(Intent.ACTION_SEND);
		send.setType("message/rfc822");
		///send.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});	
		send.putExtra(Intent.EXTRA_SUBJECT, "My Todo List");
		send.putExtra(Intent.EXTRA_TEXT   , "ToDo: \n_______\n");
		try {
		    startActivity(Intent.createChooser(send, "Send mail..."));
		    Toast.makeText(EmailActivity.this, "This feature is coming soon!!!", Toast.LENGTH_LONG).show();
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(EmailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
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
