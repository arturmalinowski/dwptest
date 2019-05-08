package dwp;

import com.google.gson.Gson;
import dwp.configuration.DwpProperties;
import dwp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserParser {

    private static final Gson GSON = new Gson();
    private final RequestSender requestSender;
    private final DwpProperties properties;

    @Autowired
    UserParser(RequestSender requestSender, DwpProperties properties) {
        this.requestSender = requestSender;
        this.properties = properties;
    }

    public ResponseEntity<String> getUsersInLondon() {
        ResponseEntity<String> response = requestSender.send(properties.getLondonUsersUrl());

        if(response.getStatusCode().equals(HttpStatus.OK)) {
            return response;
        }
        throw new RuntimeException("Request failed.");
    }

    public ResponseEntity<String> getUsersNearbyLondon() {
        ResponseEntity<String> response = requestSender.send(properties.getNearbyUsersUrl());
        if(response.getStatusCode().equals(HttpStatus.OK)) {
            User[] usersArray = GSON.fromJson(response.getBody(), User[].class);

            List<User> users = Arrays.stream(usersArray).filter(this::isNearbyLondon).collect(Collectors.toList());

            return new ResponseEntity<>(GSON.toJson(users), HttpStatus.OK);
        }
        throw new RuntimeException("Request failed.");
    }

    private boolean isNearbyLondon(User user) {
        return user.getLatitude() < 38 && user.getLatitude() > -9
                && user.getLongitude() < 114 && user.getLongitude() > -85;
    }
}
