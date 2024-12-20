package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import beans.Users;
import dao.UserDAO;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    public EditUserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        try {
            int userId = Integer.parseInt(id);
            Users user = userDAO.getUserById(userId);
            if (user != null) {
                request.setAttribute("agent", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editAgent.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("ListAgentServlet");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("listAgent.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les données du formulaire
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String role = request.getParameter("role");

            // Vérifier les données
            if (username != null && !username.isEmpty() &&
                email != null && !email.isEmpty() &&
                role != null && !role.isEmpty()) {

                // Créer un objet utilisateur mis à jour
                Users updatedUser = new Users();
                updatedUser.setId(id);
                updatedUser.setUsername(username);
                updatedUser.setEmail(email);
                updatedUser.setRole(role);

                // Mettre à jour l'utilisateur dans la base de données
                boolean isUpdated = userDAO.updateAgent(updatedUser);

                if (isUpdated) {
                    // Rediriger vers la liste des agents en cas de succès
                    response.sendRedirect("ListAgentServlet");
                } else {
                    // Afficher une erreur si la mise à jour a échoué
                    request.setAttribute("errorMessage", "Erreur lors de la mise à jour de l'utilisateur.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("editAgent.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                // Gérer les champs manquants
                request.setAttribute("errorMessage", "Tous les champs sont obligatoires.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("editAgent.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("ListAgentServlet");
        }
    }
}
