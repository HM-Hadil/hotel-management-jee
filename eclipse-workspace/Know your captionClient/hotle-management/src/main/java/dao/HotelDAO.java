package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import beans.DatabaseConnection;
import beans.Hotel;
import beans.RoomType;

public class HotelDAO {

    private static final String INSERT_HOTEL = "INSERT INTO hotel (name, city, description, stars, imagePath) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_HOTELS = "SELECT * FROM hotel";
    private static final String SELECT_HOTEL_BY_ID = "SELECT * FROM hotel WHERE id = ?";
    private static final String INSERT_HOTEL_ROOM_TYPE = "INSERT INTO hotel_room_types (hotel_id, room_type_id) VALUES (?, ?)";
    

    public int addHotel(Hotel hotel, List<Integer> roomTypeIds) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Insert hotel
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HOTEL, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, hotel.getName() != null ? hotel.getName() : "");
                preparedStatement.setString(2, hotel.getCity() != null ? hotel.getCity() : "");
                preparedStatement.setString(3, hotel.getDescription() != null ? hotel.getDescription() : "");
                preparedStatement.setInt(4, hotel.getStars());
                preparedStatement.setString(5, hotel.getImagePath());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Creating hotel failed, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int hotelId = generatedKeys.getInt(1);
                        
                        // Associate room types
                        if (roomTypeIds != null && !roomTypeIds.isEmpty()) {
                            associateRoomTypes(connection, hotelId, roomTypeIds);
                        }

                        connection.commit();
                        return hotelId;
                    } else {
                        throw new SQLException("Creating hotel failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void associateRoomTypes(Connection connection, int hotelId, List<Integer> roomTypeIds) throws SQLException {
        String deleteExisting = "DELETE FROM hotel_room_types WHERE hotel_id = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteExisting)) {
            deleteStmt.setInt(1, hotelId);
            deleteStmt.executeUpdate();
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HOTEL_ROOM_TYPE)) {
            for (Integer roomTypeId : roomTypeIds) {
                preparedStatement.setInt(1, hotelId);
                preparedStatement.setInt(2, roomTypeId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }





    // Supprimer un hôtel
    public boolean deleteHotel(int hotelId) throws SQLException {
        String sql = "DELETE FROM hotel WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, hotelId);
            return preparedStatement.executeUpdate() > 0;
        }
    }
    
    
    // Récupérer tous les hôtels
    public List<Hotel> getAllHotels() throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotel";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Hotel hotel = new Hotel();
                hotel.setId(resultSet.getInt("id"));
                hotel.setName(resultSet.getString("name"));
                hotel.setCity(resultSet.getString("city"));
                hotel.setDescription(resultSet.getString("description"));
                hotel.setStars(resultSet.getInt("stars"));
                hotel.setImagePath(resultSet.getString("imagePath"));
                hotels.add(hotel);
            }
        }
        return hotels;
    }

    // Récupérer un hôtel par son ID
    public Hotel getHotelById(int hotelId) throws SQLException {
        Hotel hotel = null;

        String sql = "SELECT * FROM hotel WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, hotelId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    hotel = new Hotel();
                    hotel.setId(resultSet.getInt("id"));
                    hotel.setName(resultSet.getString("name"));
                    hotel.setCity(resultSet.getString("city"));
                    hotel.setDescription(resultSet.getString("description"));
                    hotel.setStars(resultSet.getInt("stars"));
                    hotel.setImagePath(resultSet.getString("imagePath"));

                    // Retrieve associated room types
                    List<RoomType> roomTypes = getRoomTypesForHotel(hotelId);
                    hotel.setRoomTypes(roomTypes);
                    return hotel;
                }
            }
        }
        return null;
    }

    // Get the room types for a specific hotel
    private List<RoomType> getRoomTypesForHotel(int hotelId) throws SQLException {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT rt.id, rt.label, rt.capacity, rt.price FROM roomtype rt " +
                     "JOIN hotel_room_types hrt ON rt.id = hrt.room_type_id " +
                     "WHERE hrt.hotel_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, hotelId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    RoomType roomType = new RoomType();
                    roomType.setId(resultSet.getInt("id"));
                    roomType.setLabel(resultSet.getString("label"));
                    roomType.setCapacity(resultSet.getInt("capacity"));
                    roomType.setPrice(resultSet.getDouble("price"));
                    roomTypes.add(roomType);
                }
            }
        }
        return roomTypes;
    }
    public void updateHotel(Hotel hotel, List<Integer> roomTypeIds) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Update hotel details
            String updateSql = "UPDATE hotel SET name = ?, city = ?, stars = ?, description = ?" +
                             (hotel.getImagePath() != null ? ", imagePath = ?" : "") +
                             " WHERE id = ?";
                             
            try (PreparedStatement stmt = connection.prepareStatement(updateSql)) {
                int paramIndex = 1;
                stmt.setString(paramIndex++, hotel.getName());
                stmt.setString(paramIndex++, hotel.getCity());
                stmt.setInt(paramIndex++, hotel.getStars());
                stmt.setString(paramIndex++, hotel.getDescription());
                
                if (hotel.getImagePath() != null) {
                    stmt.setString(paramIndex++, hotel.getImagePath());
                }
                
                stmt.setInt(paramIndex, hotel.getId());
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("La mise à jour de l'hôtel a échoué, aucune ligne modifiée.");
                }

                // Update room type associations
                if (roomTypeIds != null) {
                    associateRoomTypes(connection, hotel.getId(), roomTypeIds);
                }

                connection.commit();
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public Hotel getHotelByIdWithRoomTypes(int hotelId) throws SQLException {
        Hotel hotel = null;

        String sqlHotel = "SELECT * FROM hotel WHERE id = ?";
        String sqlRoomTypes = 
            "SELECT rt.id, rt.label, rt.capacity, rt.price " +
            "FROM roomtype rt " +
            "JOIN hotel_room_types hrt ON rt.id = hrt.room_type_id " +
            "WHERE hrt.hotel_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmtHotel = connection.prepareStatement(sqlHotel);
             PreparedStatement stmtRoomTypes = connection.prepareStatement(sqlRoomTypes)) {

            // Récupérer les détails de l'hôtel
            stmtHotel.setInt(1, hotelId);
            try (ResultSet rsHotel = stmtHotel.executeQuery()) {
                if (rsHotel.next()) {
                    hotel = new Hotel();
                    hotel.setId(rsHotel.getInt("id"));
                    hotel.setName(rsHotel.getString("name"));
                    hotel.setCity(rsHotel.getString("city"));
                    hotel.setDescription(rsHotel.getString("description"));
                    hotel.setStars(rsHotel.getInt("stars"));
                    hotel.setImagePath(rsHotel.getString("imagePath"));
                }
            }

            // Récupérer les types de chambres associés
            if (hotel != null) {
                stmtRoomTypes.setInt(1, hotelId);
                try (ResultSet rsRoomTypes = stmtRoomTypes.executeQuery()) {
                    List<RoomType> roomTypes = new ArrayList<>();
                    while (rsRoomTypes.next()) {
                        RoomType roomType = new RoomType();
                        roomType.setId(rsRoomTypes.getInt("id"));
                        roomType.setLabel(rsRoomTypes.getString("label"));
                        roomType.setCapacity(rsRoomTypes.getInt("capacity"));
                        roomType.setPrice(rsRoomTypes.getDouble("price"));
                        roomTypes.add(roomType);
                    }
                    hotel.setRoomTypes(roomTypes);
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception (par exemple, loguer ou re-thrower l'exception)
            throw new SQLException("Erreur lors de la récupération des détails de l'hôtel et des types de chambres", e);
        }

        return hotel;
    }

    // Méthode pour récupérer les villes distinctes
    public List<String> getDistinctCities() throws SQLException {
        List<String> cities = new ArrayList<>();
        String sql = "SELECT DISTINCT city FROM hotel ORDER BY city";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                cities.add(resultSet.getString("city"));
            }
        }
        return cities;
    }
    
    public List<Hotel> searchHotels(String hotelName, String city, Integer stars, Double minPrice, Double maxPrice, String roomType) throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT DISTINCT h.* FROM hotel h " +
            "LEFT JOIN hotel_room_types hrt ON h.id = hrt.hotel_id " +
            "LEFT JOIN roomtype rt ON hrt.room_type_id = rt.id " +
            "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        // Filtres de recherche
        if (hotelName != null && !hotelName.trim().isEmpty()) {
            sql.append(" AND LOWER(h.name) LIKE ?");
            params.add("%" + hotelName.toLowerCase().trim() + "%");
        }

        if (city != null && !city.trim().isEmpty()) {
            sql.append(" AND h.city = ?");
            params.add(city);
        }

        if (stars != null) {
            sql.append(" AND h.stars = ?");
            params.add(stars);
        }

        if (roomType != null && !roomType.trim().isEmpty()) {
            sql.append(" AND rt.label = ?");
            params.add(roomType);
        }

        if (minPrice != null) {
            sql.append(" AND rt.price >= ?");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql.append(" AND rt.price <= ?");
            params.add(maxPrice);
        }

        sql.append(" ORDER BY h.name");

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            // Ajouter les paramètres
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Hotel hotel = new Hotel();
                    hotel.setId(resultSet.getInt("id"));
                    hotel.setName(resultSet.getString("name"));
                    hotel.setCity(resultSet.getString("city"));
                    hotel.setDescription(resultSet.getString("description"));
                    hotel.setStars(resultSet.getInt("stars"));
                    hotel.setImagePath(resultSet.getString("imagePath"));
                    
                    // Récupérer les types de chambres
                    hotel.setRoomTypes(getRoomTypesForHotel(hotel.getId()));
                    
                    hotels.add(hotel);
                }
            }
        }
        return hotels;
    }

    // Méthodes précédentes restent identiques...

    // Méthode pour obtenir la plage de prix
    public double[] getPriceRange() throws SQLException {
        double[] priceRange = new double[2];
        String sql = "SELECT MIN(price) as min_price, MAX(price) as max_price FROM roomtype";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                priceRange[0] = resultSet.getDouble("min_price");
                priceRange[1] = resultSet.getDouble("max_price");
            }
        }
        return priceRange;
    }

    // Méthode pour obtenir les étoiles disponibles
    public List<Integer> getAvailableStars() throws SQLException {
        List<Integer> stars = new ArrayList<>();
        String sql = "SELECT DISTINCT stars FROM hotel ORDER BY stars";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                stars.add(resultSet.getInt("stars"));
            }
        }
        return stars;
    }

    // Méthode pour récupérer les types de chambres distincts
    public List<String> getDistinctRoomTypes() throws SQLException {
        List<String> roomTypes = new ArrayList<>();
        String sql = "SELECT DISTINCT label FROM roomtype ORDER BY label";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                roomTypes.add(resultSet.getString("label"));
            }
        }
        return roomTypes;
    }
 
}