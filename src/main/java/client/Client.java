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
        Controller c = new Controller(client);
        client.setCallback(new ClientCallback(client, c));
        client.connect();
        client.subscribe("iot_data/" + client.getClientId());
        MqttMessage message = new MqttMessage();
        message.setPayload("Client is connected".getBytes());
        client.publish("iot_data/" + client.getClientId(), message);

        ClientWriter cw = new ClientWriter(client, c);
    }
}
