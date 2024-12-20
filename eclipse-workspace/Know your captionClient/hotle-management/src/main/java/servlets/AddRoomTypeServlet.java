package servlets;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

import beans.RoomType;
import dao.HotelDAO;
import dao.RoomTypeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AddRoomTypeServlet
 */
@WebServlet("/AddRoomTypeServlet")
public class AddRoomTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomTypeDAO roomTypeDAO;

    @Override
    public void init() throws ServletException {
        roomTypeDAO = new RoomTypeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer la liste des hôtels pour le formulaire
        HotelDAO hotelDAO = new HotelDAO();
        RequestDispatcher dispatcher = request.getRequestDispatcher("addRoomType.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les paramètres du formulaire
        String label = request.getParameter("label");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        double price = Double.parseDouble(request.getParameter("price"));

        // Création de l'objet RoomType
        RoomType roomType = new RoomType();
        roomType.setLabel(label);
        roomType.setCapacity(capacity);
        roomType.setPrice(price);

        // Ajouter RoomType à la base de données via DAO
        RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
        boolean success = roomTypeDAO.addRoomType(roomType);  // Supposons que vous utilisez un DAO pour l'ajout

        if (success) {
            response.sendRedirect("RoomTypeListServlet"); // Rediriger en cas de succès
        } else {
            response.sendRedirect("errorPage.jsp"); // Rediriger en cas d'erreur
        }
    }
}
