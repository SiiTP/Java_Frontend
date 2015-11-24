package game.gameaction;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Created by ivan on 25.10.15.
 */
public interface GameActionStrategy {
    void processGameAction(@NotNull JSONObject message,@NotNull String httpSession);
}
