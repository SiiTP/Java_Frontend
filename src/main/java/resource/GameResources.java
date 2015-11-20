package resource;

/**
 * Created by ivan on 26.10.15.
 */
@SuppressWarnings("unused")
public class GameResources implements Resource {
    private int maxRoomPlayingTimeInSec;
    private int gameFieldWidth;
    private int gameFieldHeight;
    private int defaultWinScore;
    private int maxPlayers;
    private int defaultSpeed;
    private int defaultPlayerRadius;
    private int millsToSeconds;
    private int defaultStopDirectionValue;

    public int getDefaultStopDirectionValue() {
        return defaultStopDirectionValue;
    }

    public int getToSeconds() {
        return millsToSeconds;
    }

    public int getDefaultPlayerRadius() {
        return defaultPlayerRadius;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public int getGameFieldWidth() {
        return gameFieldWidth;
    }

    public int getGameFieldHeight() {
        return gameFieldHeight;
    }

    public int getMaxRoomPlayingTimeInSec() {
        return maxRoomPlayingTimeInSec;
    }

    public int getDefaultWinScore() {
        return defaultWinScore;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
