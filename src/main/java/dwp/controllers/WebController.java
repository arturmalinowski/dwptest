package dwp.controllers;

import dwp.UserParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    private final UserParser userParser;

    @Autowired
    private WebController(UserParser userParser) {
        this.userParser = userParser;
    }

    @GetMapping("/london/users")
    public String getUsersInLondon() {
        return userParser.getUsersInLondon();
    }

    @GetMapping("/nearby")
    public String getUsersNearbyLondon(){
        return userParser.getUsersNearbyLondon();
    }
}
