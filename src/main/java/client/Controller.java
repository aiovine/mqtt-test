package client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by isz_d on 02/09/2017.
 */
public class Controller {
    private MqttClient client;
    private String currentRoom;

    public Controller(MqttClient client) {
        this.client = client;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void handleCreateRoom(String responseText) throws MqttException {
        String result = responseText.split("//")[2];
        String roomName = responseText.split("//")[1];
        if (result.equals("true")) {
            client.unsubscribe("iot_data/" + client.getClientId() + "/response");
            client.subscribe(roomName);
            currentRoom = roomName;
            System.out.println("Now connected to room " + roomName);
        }
    }
}
