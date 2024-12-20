package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.DatabaseConnection;
import beans.Users;

public class UserDAO {
    // Requête SQL pour insérer un utilisateur
    private static final String INSERT_USER_SQL = "INSERT INTO Users (username, password, role, email) VALUES (?, ?, ?, ?)";

    /**
     * Enregistre un utilisateur dans la base de données.
     * @param user L'utilisateur à enregistrer
     * @return true si l'insertion a réussi, false sinon
     */
    public boolean registerUser(Users user) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL)) {

            // Associer les paramètres à la requête SQL
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getEmail());

            // Exécuter la requête
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Retourne true si au moins une ligne est insérée

        } catch (SQLException e) {
            // Afficher l'erreur dans les logs
            System.err.println("Erreur lors de l'insertion de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private static final String LOGIN_USER_SQL = "SELECT * FROM Users WHERE username = ? AND password = ?";

    public Users login(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOGIN_USER_SQL)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Users user = new Users();
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    user.setEmail(resultSet.getString("email"));
                    return user; // Retourne l'utilisateur trouvé
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Retourne null si l'utilisateur n'est pas trouvé
    }
    
    private static final String INSERT_AGENT_SQL = "INSERT INTO Users (username, password, role, email) VALUES (?, ?, ?, ?)";

    public boolean addAgent(Users user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AGENT_SQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setString(4, user.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Return true if at least one row was affected (i.e., the agent was added)
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false in case of any errors
        }
    }
    
    private static final String SELECT_ALL_AGENTS = "SELECT * FROM users WHERE role = 'AGENT'";

    public List<Users> getAllAgents() {
        List<Users> agents = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AGENTS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getInt("id")); // Assurez-vous que la colonne "id" existe dans la table
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));
                agents.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }

    private static final String DELETE_AGENT_SQL = "DELETE FROM Users WHERE id = ?";

    public boolean deleteAgent(int agentId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_AGENT_SQL);
            statement.setInt(1, agentId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0; // If rows are deleted, return true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static final String UPDATE_AGENT_SQL = "UPDATE Users SET username = ?, email = ?, role = ? WHERE id = ?";

    public boolean updateAgent(Users agent) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_AGENT_SQL);
            statement.setString(1, agent.getUsername());
            statement.setString(2, agent.getEmail());
            statement.setString(3, agent.getRole());
            statement.setInt(4, agent.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // If rows are updated, return true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

    public Users getUserById(int id) {
        Users user = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
