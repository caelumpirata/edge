package com.edge.application.views.BACNET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.event.DeviceEventAdapter;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.service.unconfirmed.WhoIsRequest;
import com.serotonin.bacnet4j.transport.Transport;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.constructed.SequenceOf;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import com.serotonin.bacnet4j.type.primitive.Unsigned16;
import com.serotonin.bacnet4j.util.RequestUtils;

public class ConnectionBacnet {
	
	static LocalDevice localDevice;
	public ConnectionBacnet(String broadcast_ip)
	{
		IpNetwork network = new IpNetwork(broadcast_ip,47808);
		Transport transport = new Transport(network);
		localDevice = new LocalDevice(123, transport);
		try {
			
			
				 localDevice.initialize();
				 localDevice.getEventHandler().addListener(new Listener());
				 localDevice.sendGlobalBroadcast(new WhoIsRequest());
			
			
				 Thread.sleep(6000);
			
			
		}catch(Exception eb){
				System.out.println("==Connection Exception===="+eb);
				//Notification.show("==Connection Exception===="+eb, 3000, Position.MIDDLE);
				
		}
	}
	static class Listener extends DeviceEventAdapter {
	
	@Autowired
    @Override
    public void iAmReceived(RemoteDevice d) {
   try {
	   System.out.println("-----iAmReceived--------");
            Address a = new Address(new Unsigned16(0), new OctetString(new byte[] { (byte) 0xc0, (byte) 0xa8, 0x1,
                    0x5, (byte) 0xba, (byte) 0xc0 }));

            @SuppressWarnings("unchecked")
			List<ObjectIdentifier> oids = ((SequenceOf<ObjectIdentifier>)
            						 RequestUtils.sendReadPropertyAllowNull(
            						localDevice, d, d.getObjectIdentifier(), 
            						PropertyIdentifier.objectList)).getValues();
//           System.out.println(oids); // all tree
           
           
           ControllerBacnet.localDevice=localDevice;
           ControllerBacnet.d=d;
           ControllerBacnet.oids=oids;
           
            
          
        }
        catch (BACnetException e) {
            e.printStackTrace();
        }
			}
}

}
