package supervisor;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isz_d on 03/09/2017.
 */
public class SupervisorController {
    private List<String> rooms;
    private MqttClient client;

    public SupervisorController(MqttClient client) {
        this.rooms = new ArrayList<String>();
        this.client = client;
    }

    public String handleRequest(String clientID, String message) throws MqttException {
        String response = "";
        String[] messageSplit = message.split("//");

        if (message.split("//")[0].equals("echo")) {
            String repMessage = message.split("//")[1];
            response = "Repeated message: " + message.split("//")[1];
        } else if (message.split("//")[0].equals("createRoom")) {
            String roomName = message.split("//")[1];
            if (!rooms.contains(roomName)) {
                createRoom(roomName);
                response = "responseCreateRoom//" + roomName + "//true";
                client.subscribe("roomName");
            } else {
                response = "responseCreateRoom//" + roomName + "//false";
            }
        } else if (message.split("//")[0].equals("joinRoom")) {
            String roomName = message.split("//")[1];
            if (rooms.contains(roomName)) {
                response = "responseJoinRoom//" + roomName + "//true";
            } else {
                response = "responseJoinRoom//" + roomName + "//false";
            }
        } else {
            return "";
        }

        return response;
    }

    private void createRoom(String roomName) throws MqttException {
        rooms.add(roomName);
        client.subscribe(roomName);
    }
}
