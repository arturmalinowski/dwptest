package dwp;

import com.google.gson.Gson;
import dwp.configuration.DwpProperties;
import dwp.domain.User;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String getUsersInLondon() {
        HttpGet httpGet = new HttpGet(properties.getLondonUsersUrl());

        return requestSender.send(httpGet);
    }

    public String getUsersNearbyLondon() {
        HttpGet httpGet = new HttpGet(properties.getNearbyUsersUrl());
        String response = requestSender.send(httpGet);

        User[] usersArray = GSON.fromJson(response, User[].class);

        List<User> users = Arrays.stream(usersArray).filter(this::isNearbyLondon).collect(Collectors.toList());

        return GSON.toJson(users);
    }

    private boolean isNearbyLondon(User user) {
        return user.getLatitude() < 38 && user.getLatitude() > -9
                && user.getLongitude() < 114 && user.getLongitude() > -85;
    }
}
