package dwp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestSender {

    private final RestTemplate restTemplate;

    @Autowired
    public RequestSender(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    ResponseEntity<String> send(String request) {
        return restTemplate.getForEntity(request, String.class);
    }
}
