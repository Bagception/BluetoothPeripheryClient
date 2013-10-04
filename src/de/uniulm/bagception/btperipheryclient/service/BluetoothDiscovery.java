package de.uniulm.bagception.btperipheryclient.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.uniulm.bagception.bluetooth.BagceptionBTServiceInterface;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

public class BluetoothDiscovery implements BagceptionBTServiceInterface{
	private BluetoothAdapter bluetoothDevice = null;
	private List<Void> devicesRunningBagceptionService = Collections.synchronizedList(new ArrayList<Void>());

	public BluetoothDiscovery() {
		reInit();
	}
	
	public void reInit(){
		bluetoothDevice = BluetoothAdapter.getDefaultAdapter();
		devicesRunningBagceptionService.clear();
	}
	
	private BluetoothAdapter getAdapter(){
		if (bluetoothDevice == null){
			reInit();
		}
		return bluetoothDevice;
	}
	
	public void scanForNewDevices(){
		getAdapter().startDiscovery();
	}
	
	public List<BluetoothDevice> getPairedDevicesWithBagceptionService(){
		List<BluetoothDevice> ret = new ArrayList<BluetoothDevice>();
		Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
	    for (BluetoothDevice device : pairedDevices) {
	    	for (ParcelUuid uuid:device.getUuids()){	    		
	    		if (uuid.toString().equals(BT_UUID)){
	    			try {
	    				BluetoothSocket tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(BT_UUID));
	    				tmp.connect();
	    				tmp.close();
						ret.add(device);
					} catch (IOException e) {
						e.printStackTrace();
					}
	    				    	    			
	    		}
	    	}
	    }
    	
    	return ret;
	}
	
}
