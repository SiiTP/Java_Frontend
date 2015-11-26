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
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by ivan on 25.11.15.
 */
public class ScoreServlet extends HttpServlet {
    private final GameServer server;
    private final ResponseResources responseResources;
    public ScoreServlet(GameServer server) {
        this.server = server;
        responseResources =(ResponseResources) ResourceFactory.getResource("data/responseCodes.json");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer limit;
        JSONArray array = new JSONArray();
        PrintWriter writer = resp.getWriter();
        try {
            limit = Integer.parseInt(req.getParameter("limit"));
        } catch (NullPointerException | NumberFormatException e) {
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
        JSONObject responseJson = new JSONObject();
        responseJson.put("scores", array);
        responseJson.put("status", responseResources.getOk());
        if (writer != null) {
            writer.println(responseJson.toString());
        }

    }
}
