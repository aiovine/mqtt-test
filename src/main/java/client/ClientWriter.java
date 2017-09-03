package client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Scanner;

/**
 * Created by isz_d on 02/09/2017.
 */
public class ClientWriter {
    private MqttClient client;
    private Controller controller;

    public ClientWriter(MqttClient client, Controller controller) throws MqttException {
        this.client = client;
        this.controller = controller;
        run();
    }

    private void run() throws MqttException {
        while (true) {
            String text = new Scanner(System.in).nextLine();
            MqttMessage message = new MqttMessage();
            message.setPayload(text.getBytes());

            String topic;
            if (controller.getCurrentRoom() == null) {
                topic = "iot_data/" + client.getClientId() + "/request";
            } else {
                topic = controller.getCurrentRoom();
            }

            client.publish(topic, message);
        }
    }
}
