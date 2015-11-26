package servlets.game;

import game.server.GameServer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by ivan on 25.11.15.
 */
public class ScoreServlet extends HttpServlet {
    GameServer server;

    public ScoreServlet(GameServer server) {
        this.server = server;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer limit = null;
        JSONArray array = new JSONArray();
        PrintWriter writer = resp.getWriter();
        try {
            try {
                limit = Integer.parseInt(req.getParameter("limit"));
            } catch (NullPointerException e) {
                limit = 10;
            }
            System.out.println("=================================");
            System.out.println(limit);
            List list = server.getTopPlayers(limit);
            for(Object o : list){
                Object[] objects = (Object[])o;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("score",objects[0]);
                jsonObject.put("username",objects[1]);
                array.put(jsonObject);
            }
            if (writer != null) {
                writer.println(array.toString());
            }
        }catch (NumberFormatException exc){
            exc.printStackTrace();
        }

    }
}
