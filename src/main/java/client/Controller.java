package client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by isz_d on 02/09/2017.
 */
public class Controller {
    private MqttClient client;
    private String currentRoom;
    private RequestManager requestManager;

    public Controller(MqttClient client, RequestManager manager) {
        this.client = client;
        this.requestManager = manager;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void sendRequest(String text) throws MqttException {
        String topic;
        MqttMessage message = new MqttMessage();
        message.setPayload(text.getBytes());

        if (currentRoom == null) {
            //Send to the service channel
            topic = "iot_data/" + client.getClientId() + "/request";
            String[] messageSplit = text.split("//");

            //Handle requests
            if (messageSplit[0].equals("createRoom")
                    || messageSplit[0].equals("joinRoom")
                    ) {
                requestManager.request(messageSplit[0], new RequestManagerCallback() {
                    //Called when the response arrives
                    public void onResponse(String message) throws MqttException {
                        String result = message.split("//")[2];
                        String roomName = message.split("//")[1];
                        if (result.equals("true")) {
                            changeRoom(roomName);
                        }
                    }
                });
            } else if (messageSplit[0].equals("echo")) {
                requestManager.request(messageSplit[0], new RequestManagerCallback() {
                    public void onResponse(String message) throws MqttException {
                        System.out.println("Called echo callback");
                    }
                });
            }
        } else {
            //Send message to the room
            topic = currentRoom;
        }

        client.publish(topic, message);
    }

    public void changeRoom(String roomName) throws MqttException {
        client.unsubscribe("iot_data/" + client.getClientId() + "/response");
        client.subscribe(roomName);
        currentRoom = roomName;
        System.out.println("Now connected to room " + roomName);
    }
}
