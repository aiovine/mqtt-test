package client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import sun.misc.Request;

/**
 * Created by isz_d on 02/09/2017.
 */
public class ClientCallback implements MqttCallback {
    private MqttClient client;
    private Controller controller;
    private RequestManager requestManager;

    public ClientCallback(MqttClient c, Controller control, RequestManager requestManager) {
        this.client = c;
        this.controller = control;
        this.requestManager = requestManager;
    }

    public void connectionLost(Throwable throwable) {

    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String message = new String(mqttMessage.getPayload());
        System.out.println("Message received from topic " + s + ":\n\t"+ message);
        String[] messageSplit = message.split("//");
        String[] topicSplit = s.split("/");

        if (topicSplit[2].equals("response")) {
            //If the message is a response, and the RequestManager contains a matching request, execute the callback
            if (requestManager.getCallback(messageSplit[0]) != null) {
                requestManager.getCallback(messageSplit[0]).onResponse(message);
            }
        }
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
