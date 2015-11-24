package persistance;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by ivan on 20.11.15.
 */
@Entity(name="player")
public class PlayerDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long scoreCount = 0L;
    @OneToOne
    private UserProfile user;
    @OneToMany(mappedBy = "winner")
    private Set<RoomDataSet> winRooms;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Long scoreCount) {
        this.scoreCount = scoreCount;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public Set<RoomDataSet> getWinRooms() {
        return winRooms;
    }

    public void setWinRooms(Set<RoomDataSet> winRooms) {
        this.winRooms = winRooms;
    }
}
