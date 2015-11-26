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
@SuppressWarnings({"FieldCanBeLocal", "unused"})
@NamedQueries({
        @NamedQuery(name = "roomInfoByName",query = "from room r where r.roomName=:name")
})
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
            Instant roomStartTime = roomAbstract.getStartTime();
            if(roomStartTime == null){
                roomStartTime = Instant.now();
            }
            startTime = LocalDateTime.ofInstant(roomStartTime, ZoneId.of("Europe/Moscow"));
            Instant roomFinistTime = roomAbstract.getFinishTime();
            if(roomFinistTime == null){
                roomFinistTime = Instant.now();
            }
            finishTime = LocalDateTime.ofInstant(roomFinistTime, ZoneId.of("Europe/Moscow"));
            playersLimit = roomAbstract.getPlayersLimit();
            winner = roomAbstract.getWinner();
        }
    }

    public RoomDataSet() {
    }


    public UserProfile getWinner() {
        return winner;
    }

}
