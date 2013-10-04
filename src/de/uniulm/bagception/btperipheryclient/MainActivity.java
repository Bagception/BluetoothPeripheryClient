package de.uniulm.bagception.btperipheryclient;

import de.uniulm.bagception.btperipheryclient.service.BluetoothService;
import de.uniulm.bagception.ui.BluetoothDeviceArrayAdapter;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private BluetoothService btService;
	private BluetoothDeviceArrayAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter = new BluetoothDeviceArrayAdapter(this);
		ListView pairedDevicesListView = (ListView) findViewById(R.id.pairedOnlineDevices);
		pairedDevicesListView.setAdapter(adapter);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Lifecycle
	
	@Override
	protected void onResume() {
		Intent btServiceIntent = new Intent(this,BluetoothService.class);
		bindService(btServiceIntent,btConnection,Service.START_STICKY);
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unbindService(btConnection);
		super.onPause();
	}


	//binder
	ServiceConnection btConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			btService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			btService = ((BluetoothService.MyBinder) service).getService();
					 
			
		}
	};
}
