package de.uniulm.bagception.btperipheryclient.service;

import java.util.List;

import de.uniulm.bagception.btperipheryclient.bt.discovery.BluetoothDiscovery;
import de.uniulm.bagception.btperipheryclient.bt.discovery.BluetoothDiscoveryActor;
import de.uniulm.bagception.btperipheryclient.bt.discovery.BluetoothDiscoveryReactor;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BluetoothService extends Service implements Runnable, BluetoothDiscoveryReactor{
	public enum State {notInitialized,paused,searching,connected}
	private State state = State.notInitialized;
	private boolean keepAlive=true;
	private BluetoothDiscovery btDiscovery;
	private BluetoothDiscoveryActor btActor;
    private final IBinder mBinder = new MyBinder();

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (state == State.notInitialized){
			btDiscovery = new BluetoothDiscovery();
			btActor = new BluetoothDiscoveryActor(this);
			state=State.paused;
			btActor.register(this);
		}else{
			
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		btActor.unregister(this);
		super.onDestroy();
	}
	
	/* Service */
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	

	@Override
	public void run() {
		while(keepAlive){
			
			
		}
		
	}
	
	public State getState(){
		return state;
	}
	
	public List<BluetoothDevice> getConnectableAndPairedDevices(){
		Log.d("Bluetooth","looking for devices");
		List<BluetoothDevice> devices = btDiscovery.getPairedDevicesWithBagceptionService();
		for (BluetoothDevice d:devices){
			Log.d("Bluetooth","available: "+d.getName());
		}
		return devices;
	}
	
	public void scanForNewDevices(){
		btDiscovery.scanForNewDevices();
	}
	
	public class MyBinder extends Binder {
	    public BluetoothService getService() {
	      return BluetoothService.this;
	    }
	  }

	@Override
	public void onDeviceFound(BluetoothDevice d) {
		// TODO Auto-generated method stub
		
	}
}
