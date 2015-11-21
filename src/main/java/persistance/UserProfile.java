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
        @NamedQuery(name = "isAvailable",query = "from user u where u.username =:username")
})
@Entity(name="user")
public class UserProfile {
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
    @Transient
    private boolean isAuthorized;

    public UserProfile(@NotNull String name,@NotNull String pass) {
        this.username = name;
        this.password = pass;
        gameProfile = new GameProfile();
        currentroom = null;
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

    @NotNull
    public String getPassword() {
        return password;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PlayerDataSet getPlayer() {
        return player;
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
}
