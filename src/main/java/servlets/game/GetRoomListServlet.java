package servlets.game;

import game.rooms.Room;
import game.serverlevels.top.TopLevelGameServer;
import org.json.JSONArray;
import org.json.JSONObject;
import resource.ResourceFactory;
import resource.ResponseResources;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by ivan on 26.10.15.
 */
public class GetRoomListServlet extends HttpServlet {
    private TopLevelGameServer topLevelGameServer;
    private ResponseResources responseResources;

    public GetRoomListServlet(TopLevelGameServer topLevelGameServer) {
        responseResources =(ResponseResources) ResourceFactory.getResource("resources/data/responseCodes.json");
        this.topLevelGameServer = topLevelGameServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Room> rooms = topLevelGameServer.getRoomsList();
        JSONObject object = new JSONObject();
        JSONArray roomsJsonArray = new JSONArray();
        if(!rooms.isEmpty()) {
            Collection<Room> roomArray = rooms.values();
            for (Room room : roomArray) {
                roomsJsonArray.put(room.getJsonRoom());
            }
            object.put("status", responseResources.getOk());
            object.put("rooms", roomsJsonArray);
        }else{
            object.put("status", responseResources.getZeroPlayingRoomsNow());
            object.put("message", "No rooms");
        }
        resp.getWriter().println(object.toString());
    }
}
