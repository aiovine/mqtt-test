package supervisor;

import org.eclipse.paho.client.mqttv3.*;

import java.util.ArrayList;
import java.util.List;

public class SupervisorCallback implements MqttCallback {
    private MqttClient client;
    private SupervisorController controller;


    public SupervisorCallback(MqttClient client) {
        this.client = client;
        this.controller = new SupervisorController(client);
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String message = new String(mqttMessage.getPayload());
        System.out.println("Message received:\n\t"+ message);
        String responseTopic;

        String[] topicSplit = topic.split("/");

        if (topicSplit[0].equals("iot_data")) {
            //Request
            String clientID = topic.split("/")[1];
            String response = controller.handleRequest(clientID, message);
            responseTopic = "iot_data/" + clientID + "/response";

            MqttMessage mqttResponse = new MqttMessage();
            mqttResponse.setPayload(response.getBytes());
            client.publish(responseTopic, mqttResponse);
        } else {
            return;
        }
    }


    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }
}
