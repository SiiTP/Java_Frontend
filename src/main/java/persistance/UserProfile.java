package persistance;

import game.rooms.Room;
import game.user.GameProfile;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import javax.persistence.*;

/**
 * Created by ivan on 21.09.15.
 */
@NamedQueries({
        @NamedQuery(name = "userByName",query = "from user u where u.username = :username"),
        @NamedQuery(name = "isAvailable",query = "from user u where u.username =:username"),
        @NamedQuery(name = "getTopPlayers",query = "select p.scoreCount, u.username from player p join p.user u order by p.scoreCount desc")
})
@Entity(name="user")
public class UserProfile {
    @SuppressWarnings("unused")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 128)
    private String username;
    @Column(nullable = false,length = 128)
    private String password;

    @OneToOne(mappedBy = "user")
    @Cascade(CascadeType.ALL)
    private PlayerDataSet player;
    @Transient
    private final GameProfile gameProfile;
    @Transient
    private Room currentroom;
    @Column
    private boolean isAuthorized = false;

    public UserProfile(@NotNull String name,@NotNull String pass) {
        this.username = name;
        this.password = pass;
        gameProfile = new GameProfile();
        currentroom = null;
    }
    public UserProfile(){
        gameProfile = new GameProfile();
    }
    public boolean checkPassword(@Nullable String pass){
        return pass != null && pass.equals(this.password);
    }

    @NotNull
    public String getUsername() {
        return username;
    }
    @Nullable
    public Room getCurrentroom() {
        return currentroom;
    }

    public void setCurrentroom(@Nullable Room currentroom) {
        this.currentroom = currentroom;
    }

    public JSONObject getJson(){
        JSONObject object = gameProfile.getJSON();
        object.put("name",username);
        return object;
    }
    @NotNull
    public GameProfile getGameProfile(){
        return gameProfile;
    }

    public void setPlayer(PlayerDataSet player) {
        this.player = player;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public Long getId() {
        return id;
    }

    public PlayerDataSet getPlayerDataSet() {
        return player;
    }
}
