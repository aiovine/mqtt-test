package client;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by isz_d on 05/09/2017.
 */
public interface RequestManagerCallback {
    public void onResponse(String message) throws MqttException;
}
