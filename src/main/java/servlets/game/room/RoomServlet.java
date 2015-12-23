package servlets.game.room;

import game.rooms.Room;
import game.server.GameServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
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
 * Created by ivan on 25.11.15.
 */
public class RoomServlet extends HttpServlet {
    private final GameServer gameServer;
    private final ResponseResources responseResources;
    private static final Logger LOGGER = LogManager.getLogger("ReqResp");
    public RoomServlet(GameServer gameServer) {
        responseResources =(ResponseResources) ResourceFactory.getResource("src/main/resources/data/responseCodes.json");
        this.gameServer = gameServer;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomName = req.getParameter("roomName");
        String password = req.getParameter("password");
        LOGGER.info(new MarkerManager.Log4jMarker("REQUEST"),"data: " + roomName + ' ' + password);
        String session = req.getSession().getId();
        JSONObject responseJSON = new JSONObject();
        if(roomName != null && !roomName.isEmpty()) {

            if (!gameServer.checkIfRoomExist(roomName)) {
                responseJSON.put("status", responseResources.getRoomAlreadyExist());
                responseJSON.put("message", "Room with name " + roomName + " already exist");
            } else {
                boolean auth = gameServer.isAuthorizedPlayer(session);
                if (auth) {
                    Room room = gameServer.createRoom(session, roomName, password);
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

        }else{
            responseJSON.put("status", responseResources.getNoRoomName());
            responseJSON.put("message", "you should provide room name");
        }
        resp.getWriter().println(responseJSON.toString());
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomName = req.getParameter("roomName");
        String password = req.getParameter("password");
        LOGGER.info(new MarkerManager.Log4jMarker("REQUEST"),"data: " + roomName + ' ' + password);
        String session = req.getSession().getId();
        JSONObject responseJSON = new JSONObject();
        if(gameServer.checkIfRoomExist(roomName)){
            responseJSON.put("status", responseResources.getNoSuchRoom());
            responseJSON.put("message", "Room with name "+roomName + " not exist");
        }else {
            if(!gameServer.isFullRoom(roomName)) {
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
            }else{
                responseJSON.put("status", responseResources.getRoomIsFull());
                responseJSON.put("message", "room is full");
            }
        }
        resp.getWriter().println(responseJSON.toString());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray roomsJsonArray = gameServer.getRoomsListJSON();
        JSONObject object = new JSONObject();
        if(roomsJsonArray != null){
            object.put("id",1);
            object.put("status", responseResources.getOk());
            object.put("rooms", roomsJsonArray);
        }else{
            object.put("status", responseResources.getZeroPlayingRoomsNow());
            object.put("message", "No rooms");
        }

        resp.getWriter().println(object.toString());
    }
}
