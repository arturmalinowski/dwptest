package dwp;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

@Service
public class RequestSender {

    private final static Logger LOGGER = Logger.getLogger(RequestSender.class.getName());

    private final HttpClient httpClient;

    @Autowired
    public RequestSender(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    String send(HttpGet request) {
        String response = "";

        try {
            HttpResponse httpResponse = httpClient.execute(request);
            response = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            LOGGER.severe(String.format("Couldn't execute the call: %s", e));
        }

        return response;
    }
}
