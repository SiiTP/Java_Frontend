package service.account;

import dao.RoomDAO;
import game.rooms.Room;
import game.rooms.RoomFFA;
import game.user.GameProfile;
import persistance.UserProfile;
import org.hibernate.Session;
import persistance.ProjectDB;
import persistance.RoomDataSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 20.11.15.
 */
public class RoomService {
    private Map<String,Room> rooms;
    RoomDAO dao;
    public RoomService(){
        dao = new RoomDAO();
        rooms = new HashMap<>();
    }
    public boolean isAvailable(String roomname){
        return rooms.containsKey(roomname);
    }
    public Room getRoomByName(String roomname){
        return rooms.get(roomname);
    }
    public boolean isAnyRoomExist(){
        return !rooms.isEmpty();
    }
    public Collection<Room> getRooms(){
        return rooms.values();
    }
    public void addRoom(String name,Room room){
        rooms.put(name,room);
    }
    public void clear(){
        rooms = new HashMap<>();

    }
    public void finishRoom(String roomname){
        RoomDataSet dataSet = new RoomDataSet(rooms.get(roomname));
        Session session = ProjectDB.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        dao.saveRoomInfo(dataSet);
        session.getTransaction().commit();
        rooms.remove(roomname);
    }

    public void setRooms(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

    public static void main(String[] args) {
        AccountService service1 = new AccountService();
        UserProfile p = new UserProfile("test2","test2");
        UserProfile p2 = new UserProfile("test1","test1");
        service1.addUser(p);
        service1.addUser(p2);
        RoomService service = new RoomService();
        RoomFFA ffa = new RoomFFA("test","test");
        GameProfile gameProfile = p.getGameProfile();
        gameProfile.setScore(23);
        GameProfile gameProfile1 = p2.getGameProfile();
        gameProfile1.setScore(5351);
        ffa.addUser(p);
        ffa.addUser(p2);
        service.addRoom("test",ffa);
        service.finishRoom("test");
    }
}
