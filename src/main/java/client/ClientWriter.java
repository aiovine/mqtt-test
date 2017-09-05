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
    private RequestManager requestManager;

    public ClientWriter(MqttClient client, Controller controller, RequestManager requestManager) throws MqttException {
        this.client = client;
        this.controller = controller;
        this.requestManager = requestManager;
        run();
    }

    private void run() throws MqttException {
        while (true) {
            String text = new Scanner(System.in).nextLine();
            controller.sendRequest(text);
        }
    }
}
