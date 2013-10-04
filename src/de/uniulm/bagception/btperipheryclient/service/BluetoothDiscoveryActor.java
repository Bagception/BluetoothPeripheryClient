package de.uniulm.bagception.btperipheryclient.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.util.Log;
import de.philipphock.android.lib.BroadcastActor;
import de.uniulm.bagception.bluetooth.BagceptionBTServiceInterface;

public class BluetoothDiscoveryActor extends BroadcastActor<BluetoothDiscoveryReactor> implements BagceptionBTServiceInterface{

	private List<BluetoothDevice> foundDevices = Collections.synchronizedList(new ArrayList<BluetoothDevice>());
	
	public BluetoothDiscoveryActor(BluetoothDiscoveryReactor reactor) {
		super(reactor);
	}

	@Override
	public void register(Activity a) {
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    filter.addAction(BluetoothDevice.ACTION_UUID);
	    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
	    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	    a.registerReceiver(this, filter);		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
	     if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
	    	 //device found
	    	 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	    	 foundDevices.add(device);
	    	 
	     }else if(intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
	    	 //device discovery finished
	    	 for (BluetoothDevice d:foundDevices){
	    		 d.fetchUuidsWithSdp();
	    	 }
	    	 
	     }else if(intent.getAction().equals(BluetoothDevice.ACTION_UUID)){
	    	 //UUIDs fetched from one device
	    	 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	    	 
	         Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
	         for(Parcelable p:uuidExtra){
	        	 Log.d("Bluetooth","UUID found: "+p.toString());
	        	if (p.toString().equals(BT_UUID)){
	        		deviceFound(device);
	        	}
	         }
	         
	    	 
	         
	     }else if(intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)){
	    	 //device discovery started
	    	foundDevices.clear();
	    }		
	}
	
	private void deviceFound(BluetoothDevice device){
		
	}

}
