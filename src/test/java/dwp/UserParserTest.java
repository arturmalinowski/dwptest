package dwp;

import dwp.configuration.DwpProperties;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserParserTest {

    private final String SINGLE_USER_RESPONSE = getStringFrom("json/single_user_response.json");
    private final String MULTIPLE_USERS_RESPONSE = getStringFrom("json/multiple_users_response.json");
    private final String EXPECTED_RESPONSE = getStringFrom("json/expected_response.json");
    private static final String SOME_URL = "SOME_URL";

    private UserParser underTest;
    private RequestSender requestSender;
    private DwpProperties properties;

    @Before
    public void setUp() {
        requestSender = mock(RequestSender.class);
        properties = mock(DwpProperties.class);

        underTest = new UserParser(requestSender, properties);
    }

    @Test
    public void canGetUsersInLondon() {
        when(requestSender.send(any())).thenReturn(new ResponseEntity<>(SINGLE_USER_RESPONSE, HttpStatus.OK));
        when(properties.getLondonUsersUrl()).thenReturn(SOME_URL);

        ResponseEntity<String> response = underTest.getUsersInLondon();

        assertThat(response.getBody(), is(SINGLE_USER_RESPONSE));
    }

    @Test
    public void canGetUsersNearbyLondon() {
        when(requestSender.send(any())).thenReturn(new ResponseEntity<>(MULTIPLE_USERS_RESPONSE, HttpStatus.OK));
        when(properties.getNearbyUsersUrl()).thenReturn(SOME_URL);

        ResponseEntity<String> response = underTest.getUsersNearbyLondon();

        assertThat(response.getBody(), is(EXPECTED_RESPONSE));
    }

    @Test(expected = RuntimeException.class)
    public void throwsExceptionWhenCouldntGetResponseForUsersInLondon() {
        when(requestSender.send(any())).thenReturn(new ResponseEntity<>("", HttpStatus.BAD_REQUEST));
        when(properties.getNearbyUsersUrl()).thenReturn(SOME_URL);

        underTest.getUsersNearbyLondon();
    }

    @Test(expected = RuntimeException.class)
    public void throwsExceptionWhenCouldntGetResponseForUsersNearby() {
        when(requestSender.send(any())).thenReturn(new ResponseEntity<>("", HttpStatus.BAD_REQUEST));
        when(properties.getLondonUsersUrl()).thenReturn(SOME_URL);

        underTest.getUsersInLondon();
    }

    private String getStringFrom(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        String fileString = "";
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            try {
                fileString = new String(Files.readAllBytes(new File(resource.getFile()).toPath()), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileString;
        }
    }
}