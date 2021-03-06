package game.action.processor;

import game.action.gameaction.GameActionStrategy;
import game.action.gameaction.MoveActionStrategy;
import game.rooms.Room;
import game.server.GameServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import resource.ResourceFactory;
import resource.ResponseResources;

/**
 * Created by ivan on 30.11.15.
 */
public class MoveActionProcessor implements ActionProcessor {
    private final ResponseResources responseResources;
    private final GameServer gameServer;
    private static final Logger LOGGER = LogManager.getLogger(MoveActionProcessor.class);
    private final GameActionStrategy strategy;
    public MoveActionProcessor(GameServer server) {
        this.gameServer = server;
        responseResources =(ResponseResources) ResourceFactory.getResource("src/main/resources/data/responseCodes.json");
        strategy = new MoveActionStrategy(server);
    }

    @Override
    public JSONObject processMessage(JSONObject message,String httpSession) {
        boolean isOkGame = gameServer.isGameReady(httpSession);
        JSONObject responseMessage = new JSONObject();
        if(isOkGame) {
            strategy.processGameAction(message,httpSession);
            Room room = gameServer.getPlayerRoomBySession(httpSession);
            if (room != null) {
                if(room.isFinished()){
                    String winner = room.getWinner().getUsername();
                    gameServer.kickPlayer(httpSession);
                    LOGGER.info("winner in room " + room.getRoomName() + ':' + winner );
                    responseMessage.put("winner", winner);
                    responseMessage.put("status", responseResources.getWinnerMessageCode());
                }else {
                    responseMessage.put("players", room.getJsonRoomPlayers());
                    responseMessage.put("status", responseResources.getOk());
                    responseMessage.put("limitPlayers", room.getPlayersLimit());
                }
            }
        }else{
            responseMessage.put("status", responseResources.getRoomIsNotReadyCode());
            responseMessage.put("message", "Wait for your enemy!");
        }

        return responseMessage;
    }
}
