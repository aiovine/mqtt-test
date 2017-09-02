package client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by isz_d on 02/09/2017.
 */
public class ClientCallback implements MqttCallback {

    public void connectionLost(Throwable throwable) {

    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message received:\n\t"+ new String(mqttMessage.getPayload()) );
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
