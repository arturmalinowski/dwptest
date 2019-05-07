package dwp.controllers;

import dwp.UserParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    private final UserParser userParser;

    @Autowired
    private WebController(UserParser userParser) {
        this.userParser = userParser;
    }

    @GetMapping(value = "/london/users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUsersInLondon() {
        return new ResponseEntity<>(userParser.getUsersInLondon(), HttpStatus.OK);
    }

    @GetMapping(value = "/nearby",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUsersNearbyLondon(){
        return new ResponseEntity<>(userParser.getUsersNearbyLondon(), HttpStatus.OK);
    }
}
