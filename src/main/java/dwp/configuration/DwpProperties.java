package dwp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DwpProperties {

    @Value("${london.users.url}")
    private String londonUsersUrl;

    @Value("${nearby.users.url}")
    private String nearbyUsersUrl;

    public String getLondonUsersUrl() {
        return londonUsersUrl;
    }

    public String getNearbyUsersUrl() {
        return nearbyUsersUrl;
    }
}
