package servlets.joingame;

import game.rooms.Room;
import game.serverlevels.top.TopLevelGameServer;
import org.json.JSONObject;
import resource.ResourceFactory;
import resource.ResponseResources;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ivan on 02.10.15.
 */
public class CreateGame extends HttpServlet {
    private TopLevelGameServer topLevelGameServer;
    private ResponseResources responseResources;
    public CreateGame(TopLevelGameServer topLevelGameServer) {
        responseResources =(ResponseResources) ResourceFactory.getResource("resources/data/responseCodes.json");
        this.topLevelGameServer = topLevelGameServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomName = req.getParameter("roomName");
        String password = req.getParameter("password");
        String session = req.getSession().getId();
        if(roomName != null) {
            JSONObject responseJSON = new JSONObject();
            if (topLevelGameServer.checkIfRoomExist(roomName)) {
                responseJSON.put("status", responseResources.getRoomAlreadyExist());
                responseJSON.put("message", "Room with name " + roomName + " already exist");
            } else {
                boolean auth = topLevelGameServer.isAuthorizedPlayer(session);
                if (auth) {
                    Room room = topLevelGameServer.createRoom(session, roomName, password);
                    if (room != null) {
                        responseJSON.put("status", responseResources.getOk());
                        responseJSON.put("message", "successful creating room");
                    } else {
                        responseJSON.put("status", responseResources.getUserAlreadyInRoom());
                        responseJSON.put("message", "you are already in room");
                    }
                } else {
                    responseJSON.put("status", responseResources.getNotAuthorized());
                    responseJSON.put("message", "you are not authorized");
                }
            }
            resp.getWriter().println(responseJSON.toString());
        }

    }
}
