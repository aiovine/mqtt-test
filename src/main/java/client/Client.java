package client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import supervisor.SupervisorCallback;

import java.util.Scanner;

/**
 * Created by isz_d on 02/09/2017.
 */
public class Client {
    public static void main(String[] args) throws MqttException {
        MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
        RequestManager rm = new RequestManager();
        Controller c = new Controller(client, rm);

        client.setCallback(new ClientCallback(client, c, rm));
        client.connect();
        client.subscribe("iot_data");
        client.subscribe("iot_data/" + client.getClientId() + "/response");
        MqttMessage message = new MqttMessage();
        message.setPayload("Client is connected".getBytes());
        client.publish("iot_data/" + client.getClientId(), message);

        ClientWriter cw = new ClientWriter(client, c, rm);
    }
}
