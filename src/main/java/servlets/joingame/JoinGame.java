package servlets.joingame;

import game.rooms.Room;
import game.server.GameServer;
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
public class JoinGame extends HttpServlet {
    private final GameServer gameServer;
    private final ResponseResources responseResources;
    public JoinGame(GameServer gameServer) {
        responseResources =(ResponseResources) ResourceFactory.getResource("data/responseCodes.json");
        this.gameServer = gameServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomName = req.getParameter("roomName");
        String password = req.getParameter("password");
        String session = req.getSession().getId();
        JSONObject responseJSON = new JSONObject();
        if(gameServer.checkIfRoomExist(roomName)){
            responseJSON.put("status", responseResources.getNoSuchRoom());
            responseJSON.put("message", "Room with name "+roomName + " not exist");
        }else {
            boolean auth = gameServer.isAuthorizedPlayer(session);
            if (auth) {
                Room room = gameServer.joinRoom(roomName, password, session);
                if (room != null) {
                    responseJSON.put("status", responseResources.getOk());
                    responseJSON.put("message", "successful join room");
                } else {
                    responseJSON.put("status", responseResources.getWrongUsernameOrPassword());
                    responseJSON.put("message", "wrong username or password");
                }
            } else {
                responseJSON.put("status", responseResources.getNotAuthorized());
                responseJSON.put("message", "you are not authorized");
            }
        }
        resp.getWriter().println(responseJSON.toString());
    }
}
