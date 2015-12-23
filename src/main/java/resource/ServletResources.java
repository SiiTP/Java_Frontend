package resource;

/**
 * Created by ivan on 26.10.15.
 */
@SuppressWarnings("unused")
public class ServletResources implements Resource{
    private int minPasswordLength;
    private int webSocketIdleTimeMillisec;
    private String passwordRegexPattern;
    private String usernameRegexPattern;

    public int getMinPasswordLength() {
        return minPasswordLength;
    }

    public int getWebSocketIdleTimeMillisec() {
        return webSocketIdleTimeMillisec;
    }

    public String getPasswordRegexPattern() {
        return passwordRegexPattern;
    }

    public String getUsernameRegexPattern() {
        return usernameRegexPattern;
    }
}
