package servlets.joinGame;

import game.rooms.Room;
import game.serverLevels.TopLevelGameServer;
import service.AccountService;

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

    public CreateGame(TopLevelGameServer topLevelGameServer) {
        this.topLevelGameServer = topLevelGameServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomName = req.getParameter("roomName");
        String password = req.getParameter("password");
        String session = req.getSession().getId();
        boolean auth = topLevelGameServer.isCorrectPlayer(session, roomName);
        Room room = null;
        if (auth){
            room = topLevelGameServer.createRoom(session,roomName,password);
            if(room != null) {
                resp.getWriter().println(room.getJsonRoom());
            }
        }else{
            resp.getWriter().println("wrooooong!");
        }

    }
}
