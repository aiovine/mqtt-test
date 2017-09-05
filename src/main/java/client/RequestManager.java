package client;

import sun.misc.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by isz_d on 05/09/2017.
 */
public class RequestManager {
    public Map<String, RequestManagerCallback> requests;

    public RequestManager() {
        this.requests = new HashMap<String, RequestManagerCallback>();
    }

    public void request(String requestName, RequestManagerCallback callback) {
        requests.put(requestName, callback);
    }

    public RequestManagerCallback getCallback(String requestName) {
        return requests.get(requestName);
    }
}
