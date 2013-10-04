package de.uniulm.bagception.btperipheryclient.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BluetoothService extends Service implements Runnable{
	public enum State {paused,searching,connected}
	private State state = State.paused;
	private boolean keepAlive=true;
	private BluetoothDiscovery btDiscovery;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		btDiscovery = new BluetoothDiscovery();
		lookForConnectableDevices();
		return super.onStartCommand(intent, flags, startId);
	}
	
	/* Service */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void run() {
		while(keepAlive){
			
			
		}
		
	}
	
	private void lookForConnectableDevices(){
		Log.d("Bluetooth","looking for devices");
		List<BluetoothDevice> devices = btDiscovery.getPairedDevicesWithBagceptionService();
		for (BluetoothDevice d:devices){
			Log.d("Bluetooth","available: "+d.getName());
		}
	}
}
