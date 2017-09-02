package supervisor;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SupervisorCallback implements MqttCallback {
    private MqttClient client;

    public SupervisorCallback(MqttClient client) {
        this.client = client;
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String message = new String(mqttMessage.getPayload());
        System.out.println("Message received:\n\t"+ message);

        if (message.startsWith("//echo")) {
            String response = "Repeated message: " + message.substring(6);
            MqttMessage mqttResponse = new MqttMessage();
            mqttResponse.setPayload(response.getBytes());
            client.publish("iot_data", mqttResponse);
        }
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }
}
