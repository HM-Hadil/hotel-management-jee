package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import beans.DatabaseConnection;
import beans.Hotel;
import beans.RoomType;
public class RoomTypeDAO {
	 private static final String INSERT_ROOM_TYPE = "INSERT INTO roomtype (label, capacity, price) VALUES (?, ?, ?)";
	    private static final String SELECT_ALL_ROOM_TYPES = "SELECT * FROM roomtype";
	    private static final String ASSOCIATE_ROOM_TYPE_WITH_HOTEL = "UPDATE roomtype SET hotel_id = ? WHERE id = ?";

	    public boolean addRoomType(RoomType roomType) {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	            String sql = "INSERT INTO roomtype (label, capacity, price) VALUES (?, ?, ?)";
	            PreparedStatement preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, roomType.getLabel());
	            preparedStatement.setInt(2, roomType.getCapacity());
	            preparedStatement.setDouble(3, roomType.getPrice());

	            int rowsAffected = preparedStatement.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    private static final String SELECT_ALL_ROOMTYPES = "SELECT * FROM roomtype";

	    public List<RoomType> getAllRoomTypes() {
	        List<RoomType> roomTypes = new ArrayList<>();
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROOMTYPES);
	             ResultSet resultSet = preparedStatement.executeQuery()) {

	            while (resultSet.next()) {
	                RoomType roomType = new RoomType();
	                roomType.setId(resultSet.getInt("id"));
	                roomType.setLabel(resultSet.getString("label"));
	                roomType.setCapacity(resultSet.getInt("capacity"));
	                roomType.setPrice(resultSet.getDouble("price"));
	                roomTypes.add(roomType);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return roomTypes;
	    
	}
	    public void associateWithHotel(int roomTypeId, int hotelId) {
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(ASSOCIATE_ROOM_TYPE_WITH_HOTEL)) {
	            preparedStatement.setInt(1, hotelId);
	            preparedStatement.setInt(2, roomTypeId);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public void deleteRoomById(int roomId) {
	        String sql = "DELETE FROM roomtype WHERE id = ?";

	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, roomId);
	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public boolean updateRoom(RoomType room) {
	        String sql = "UPDATE roomtype SET label = ?, capacity = ?, price = ? WHERE id = ?";

	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, room.getLabel());
	            stmt.setInt(2, room.getCapacity());
	            stmt.setDouble(3, room.getPrice());
	            stmt.setInt(4, room.getId());

	            stmt.executeUpdate();
	            int rowsAffected = stmt.executeUpdate();
	            
	            // Return true if the update was successful
	            return rowsAffected > 0;
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        } }
	    public RoomType getRoomById(int roomId) {
	        String sql = "SELECT * FROM roomtype WHERE id = ?";
	        RoomType room = null;

	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, roomId);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                room = new RoomType(
	                    rs.getInt("id"),
	                    rs.getString("label"),
	                    rs.getInt("capacity"),
	                    rs.getDouble("price")
	                );
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return room;
	    }

}
