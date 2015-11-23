package service.account;

import dao.RoomDAO;
import game.rooms.Room;
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
    final RoomDAO dao;
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
    public RoomDataSet getRoomInfoByName(String name){
        Session session = ProjectDB.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        RoomDataSet roomDataSet = dao.getRoomInfoByName(name);
        session.getTransaction().commit();
        return roomDataSet;
    }
    public void kickPlayerFromRoom(String roomname,UserProfile profile){
        rooms.get(roomname).kickPlayer(profile);
    }
    public void setRooms(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

}
