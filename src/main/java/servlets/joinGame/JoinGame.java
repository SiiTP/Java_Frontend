package servlets.joinGame;

import exceptions.RoomFullException;
import game.rooms.RoomFFA;
import game.serverLevels.top.TopLevelGameServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ivan on 02.10.15.
 */
public class JoinGame extends HttpServlet {
    private TopLevelGameServer gameServer;

    public JoinGame(TopLevelGameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomName = req.getParameter("roomName");
        String password = req.getParameter("password");
        String session = req.getSession().getId();
        boolean auth = gameServer.isAuthorizedPlayer(session);
        RoomFFA room = null;
        try {
            if (auth) {
                room = gameServer.joinRoom(roomName, password, session);
            }
            if (room != null) {
                resp.getWriter().println(room.getJson());
            } else {
                resp.getWriter().println("something went wrong!");
            }
        }catch (RoomFullException e) {
            e.printStackTrace();
            resp.getWriter().println(e.getMessage());
        }
    }
}
