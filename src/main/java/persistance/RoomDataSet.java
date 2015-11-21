package persistance;

import game.rooms.Room;
import game.rooms.RoomAbstractImpl;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by ivan on 20.11.15.
 */
@Entity(name="room")
public class RoomDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String password;
    @Column(nullable = false)
    private String roomName;
    @Column
    private LocalDateTime startTime;
    @Column
    private LocalDateTime finishTime;
    @Column
    private Integer playersLimit;
    @ManyToOne
    private UserProfile winner;


    public RoomDataSet(Room room) {
        if(room instanceof RoomAbstractImpl) {
            RoomAbstractImpl roomAbstract = (RoomAbstractImpl) room;
            password = roomAbstract.getPassword();
            roomName = roomAbstract.getRoomName();
            startTime = LocalDateTime.ofInstant(roomAbstract.getStartTime(), ZoneId.of("Europe/Moscow"));
            finishTime = LocalDateTime.ofInstant(roomAbstract.getFinishTime(), ZoneId.of("Europe/Moscow"));
            playersLimit = roomAbstract.getPlayersLimit();
            winner = roomAbstract.getWinner();
        }
    }

    public RoomDataSet() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoomName() {
        return roomName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setPlayersLimit(Integer playersLimit) {
        this.playersLimit = playersLimit;
    }

    public Long getId() {
        return id;
    }


    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getPlayersLimit() {
        return playersLimit;
    }

    public UserProfile getWinner() {
        return winner;
    }

    public void setWinner(UserProfile winner) {
        this.winner = winner;
    }
}
