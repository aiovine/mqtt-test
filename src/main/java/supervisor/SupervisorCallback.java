package supervisor;

import org.eclipse.paho.client.mqttv3.*;

import java.util.ArrayList;
import java.util.List;

public class SupervisorCallback implements MqttCallback {
    private MqttClient client;
    private List<String> rooms;

    public SupervisorCallback(MqttClient client) {
        this.client = client;
        this.rooms = new ArrayList<String>();
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String message = new String(mqttMessage.getPayload());
        System.out.println("Message received:\n\t"+ message);
        String responseTopic = "iot_data";

        String response = "";
        if (message.split("//")[0].equals("echo")) {
            String clientID = topic.split("/")[1];
            responseTopic = "iot_data/" + clientID + "/response";
            String repMessage = message.split("//")[1];
            response = "Repeated message: " + message.split("//")[1];
        } else if (message.split("//")[0].equals("createRoom")) {
            String clientID = topic.split("/")[1];
            responseTopic = "iot_data/" + clientID + "/response";
            String roomName = message.split("//")[1];
            if (!rooms.contains(roomName)) {
                createRoom(roomName);
                response = "responseCreateRoom//" + roomName + "//true";
                client.subscribe("roomName");
            } else {
                response = "responseCreateRoom//" + roomName + "//false";
            }
        } else if (message.split("//")[0].equals("joinRoom")) {
            String clientID = topic.split("/")[1];
            responseTopic = "iot_data/" + clientID + "/response";
            String roomName = message.split("//")[1];
            if (rooms.contains(roomName)) {
                response = "responseJoinRoom//" + roomName + "//true";
            } else {
                response = "responseJoinRoom//" + roomName + "//false";
            }
        } else {
            return;
        }
        MqttMessage mqttResponse = new MqttMessage();
        mqttResponse.setPayload(response.getBytes());
        client.publish(responseTopic, mqttResponse);
    }

    public void createRoom(String roomName) throws MqttException {
        rooms.add(roomName);
        client.subscribe(roomName);
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }
}
