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
        client.setCallback(new ClientCallback());
        client.connect();
        client.subscribe("iot_data");
        MqttMessage message = new MqttMessage();
        message.setPayload("Client is connected".getBytes());
        client.publish("iot_data", message);

        while (true) {
            String text = new Scanner(System.in).nextLine();
            message = new MqttMessage();
            message.setPayload(text.getBytes());
            client.publish("iot_data", message);
        }
    }
}
