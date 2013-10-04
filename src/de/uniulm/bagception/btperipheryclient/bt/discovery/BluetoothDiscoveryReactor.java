package de.uniulm.bagception.btperipheryclient.bt.discovery;

import android.bluetooth.BluetoothDevice;
import de.philipphock.android.lib.Reactor;

public interface BluetoothDiscoveryReactor extends Reactor{

	public void onDeviceFound(BluetoothDevice d);
}
