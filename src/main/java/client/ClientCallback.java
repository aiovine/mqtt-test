package client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by isz_d on 02/09/2017.
 */
public class ClientCallback implements MqttCallback {
    private MqttClient client;
    private Controller controller;

    public ClientCallback(MqttClient c, Controller control) {
        this.client = c;
        this.controller = control;
    }

    public void connectionLost(Throwable throwable) {

    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String message = new String(mqttMessage.getPayload());
        System.out.println("Message received from topic " + s + ":\n\t"+ message);

        if (message.startsWith("responseCreateRoom")
                || message.startsWith("responseJoinRoom")) {
            controller.handleCreateRoom(message);
        }
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
