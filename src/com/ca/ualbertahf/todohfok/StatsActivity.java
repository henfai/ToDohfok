package com.ca.ualbertahf.todohfok;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StatsActivity extends Activity {
	
	Collection<Event> arc = ArchiveSingle.getArchive().getEvent();
	Collection<Event> events = EventSingle.getEventList().getEvent();
	final ArrayList<Event> arclist = new ArrayList<Event>(arc);
	final ArrayList<Event> eventlist = new ArrayList<Event>(events);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		String activeMix = String.valueOf(EventSingle.getEventList().eventActCount());
		String [] activeSplit = activeMix.split(" ");
		
		String activeTotal = activeSplit[1];
		String activeChecked = activeSplit[0];
		
		
		int aTotal = Integer.parseInt(activeTotal);
		int aChecked = Integer.parseInt(activeChecked);
		
		String activeUnchecked = String.valueOf(aTotal-aChecked);
		
		String archiveTotal = String.valueOf(ArchiveSingle.getArchive().eventArcCount());
		
		String [] archiveSplit = archiveTotal.split(" ");
		
		String arcTotal = archiveSplit[0];
		String arcChecked = archiveSplit[1];
		
		int arcTotalNum = Integer.parseInt(arcTotal);
		int arcTotalCheck = Integer.parseInt(arcChecked);
		
		int arcTotalUnchecked = arcTotalNum - arcTotalCheck;
		
		int gTotal = aTotal + arcTotalNum;
		
		String arcUn = String.valueOf(arcTotalUnchecked);
		
		String grandTotal = String.valueOf(gTotal);
		TextView title = (TextView) findViewById(R.id.titleStats);
		

		TextView stats1 = (TextView) findViewById(R.id.gTotalText); stats1.setText("Grand Total \u2192" + grandTotal);
		//TextView stats2 = (TextView) findViewById(R.id.gTotalCount); stats2.setText(grandTotal);

		TextView stats3 = (TextView) findViewById(R.id.arcTotalText); stats3.setText("Archive Total \u2192 " + arcTotal);
		//TextView stats4 = (TextView) findViewById(R.id.arcTotalCount); stats4.setText();
		
		TextView stats2 = (TextView) findViewById(R.id.arcCheck1); stats2.setText(arcChecked + " \u2190 Checked Archive");
		//TextView stats4 = (TextView) findViewById(R.id.arcTotalCount); stats4.setText();
		
		TextView stats4 = (TextView) findViewById(R.id.arcUncheck); stats4.setText(arcUn + " \u2190 Unchecked Archive");
		//TextView stats4 = (TextView) findViewById(R.id.arcTotalCount); stats4.setText();
		
		TextView stats5 = (TextView) findViewById(R.id.actTolText); stats5.setText(activeTotal + " \u2190 Active Total");
		//TextView stats6 = (TextView) findViewById(R.id.actTolCount); stats6.setText();
		
		TextView stats7 = (TextView) findViewById(R.id.actCheckText); stats7.setText("Checked Total \u2192 " + activeChecked);
		//TextView stats8 = (TextView) findViewById(R.id.actCheckCount); stats8.setText();
		
		TextView stats9 = (TextView) findViewById(R.id.actUncheckText); stats9.setText("Uncheck Total \u2190 " + activeUnchecked);
		//TextView stats0 = (TextView) findViewById(R.id.actUncheckCount); stats0.setText(); */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
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
