package servlets.game;

import game.server.GameServer;
import org.json.JSONArray;
import org.json.JSONObject;
import resource.ResourceFactory;
import resource.ResponseResources;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ivan on 26.10.15.
 */
public class GetRoomListServlet extends HttpServlet {
    private final GameServer gameServer;
    private final ResponseResources responseResources;

    public GetRoomListServlet(GameServer gameServer) {
        responseResources =(ResponseResources) ResourceFactory.getResource("data/responseCodes.json");
        this.gameServer = gameServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray roomsJsonArray = gameServer.getRoomsListJSON();
        JSONObject object = new JSONObject();
        if(roomsJsonArray != null){
            object.put("status", responseResources.getOk());
            object.put("rooms", roomsJsonArray);
        }else{
            object.put("status", responseResources.getZeroPlayingRoomsNow());
            object.put("message", "No rooms");
        }
        resp.getWriter().println(object.toString());
    }
}
