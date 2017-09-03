package supervisor;

import org.eclipse.paho.client.mqttv3.*;

public class Supervisor {
    public static void main(String[] args) throws MqttException {
        MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
        client.setCallback(new SupervisorCallback(client));
        client.connect();
        client.subscribe("iot_data/#");
        MqttMessage message = new MqttMessage();
        message.setPayload("Supervisor is connected".getBytes());
        client.publish("iot_data", message);
    }
}
