package game.rooms;

import resource.GameResources;
import resource.ResourceFactory;

import java.time.Instant;

/**
 * Created by ivan on 14.10.15.
 */
public abstract class RoomAbstractImpl implements Room  {
    private String password;
    private final String roomName;
    private Instant startTime;
    private Instant finishTime;
    private Integer playersLimit;
    private final Integer scoreLimit;
    public RoomAbstractImpl(String roomName) {
        GameResources gameResources =(GameResources) ResourceFactory.getResource("resources/data/game.json");
        this.roomName = roomName;
        playersLimit = gameResources.getMaxPlayers();
        scoreLimit = gameResources.getDefaultWinScore();
    }
    public RoomAbstractImpl(String roomName, String roomPassword) {
        this(roomName);
        this.password = roomPassword;
    }

    public int getPlayersLimit() {
        return playersLimit;
    }


    public Instant getStartTime() {
        return startTime;
    }
    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isRoomHasPass() {
        return password != null;
    }
    public void setPlayersLimit(int playersLimit){
        this.playersLimit = playersLimit;
    }

    public Instant getFinishTime() {
        return finishTime;
    }

    @Override
    public String getRoomName() {
        return roomName;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Integer getScoreLimit() {
        return scoreLimit;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

}
