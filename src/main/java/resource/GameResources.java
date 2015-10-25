package resource;

/**
 * Created by ivan on 26.10.15.
 */
public class GameResources implements Resource {
    private int maxRoomPlayingTimeInSec;
    private int gameFieldWidth;
    private int gameFieldHeight;
    private int defaultWinScore;
    private int maxPlayers;

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
