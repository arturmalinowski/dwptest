package dwp;

import dwp.configuration.DwpProperties;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserParserTest {

    private static final String SINGLE_USER_RESPONSE = "{\n" +
            "    \"id\": 135,\n" +
            "    \"first_name\": \"Mechelle\",\n" +
            "    \"last_name\": \"Boam\",\n" +
            "    \"email\": \"mboam3q@thetimes.co.uk\",\n" +
            "    \"ip_address\": \"113.71.242.187\",\n" +
            "    \"latitude\": -6.5115909,\n" +
            "    \"longitude\": 105.652983\n" +
            "  }";
    private static final String MULTILE_USERS_RESPONSE = "[\n" +
            "  {\n" +
            "    \"id\": 135,\n" +
            "    \"first_name\": \"Mechelle\",\n" +
            "    \"last_name\": \"Boam\",\n" +
            "    \"email\": \"mboam3q@thetimes.co.uk\",\n" +
            "    \"ip_address\": \"113.71.242.187\",\n" +
            "    \"latitude\": -6.5115909,\n" +
            "    \"longitude\": 195.652983\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 688,\n" +
            "    \"first_name\": \"Tiffi\",\n" +
            "    \"last_name\": \"Colbertson\",\n" +
            "    \"email\": \"tcolbertsonj3@vimeo.com\",\n" +
            "    \"ip_address\": \"141.49.93.0\",\n" +
            "    \"latitude\": 37.13,\n" +
            "    \"longitude\": -84.08\n" +
            "  } " +
            "]";
    private static final String EXPECTED_RESPONSE = "[{" +
            "\"id\":\"688\"," +
            "\"first_name\":\"Tiffi\"," +
            "\"last_name\":\"Colbertson\"," +
            "\"email\":\"tcolbertsonj3@vimeo.com\"," +
            "\"ip_address\":\"141.49.93.0\"," +
            "\"latitude\":37.13," +
            "\"longitude\":-84.08" +
            "}]";

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
        when(requestSender.send(any())).thenReturn(SINGLE_USER_RESPONSE);

        String response = underTest.getUsersInLondon();

        assertThat(response, is(SINGLE_USER_RESPONSE));
    }

    @Test
    public void canGetUsersNearbyLondon() {
        when(requestSender.send(any())).thenReturn(MULTILE_USERS_RESPONSE);

        String response = underTest.getUsersNearbyLondon();

        assertThat(response, is(EXPECTED_RESPONSE));
    }
}