package servlets.game;

import game.rooms.Room;
import game.serverlevels.top.TopLevelGameServer;
import org.json.JSONArray;
import org.json.JSONObject;

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
    TopLevelGameServer topLevelGameServer;


    public GetRoomListServlet(TopLevelGameServer topLevelGameServer) {
        this.topLevelGameServer = topLevelGameServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Room> rooms = topLevelGameServer.getRoomsList();

        JSONArray roomsJsonArray = new JSONArray();
        Collection<Room> roomArray = rooms.values();
        for (Room room : roomArray) {
            roomsJsonArray.put(room.getJsonRoom());
        }
        JSONObject object = new JSONObject();
        object.put("status", HttpServletResponse.SC_OK);
        object.put("rooms", roomsJsonArray);
        resp.getWriter().println(object.toString());
    }
}
