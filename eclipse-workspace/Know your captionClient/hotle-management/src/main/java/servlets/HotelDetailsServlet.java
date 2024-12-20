package servlets;

import beans.Hotel;
import dao.HotelDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/HotelDetailsServlet")
public class HotelDetailsServlet extends HttpServlet {
    private HotelDAO hotelDAO;

    @Override
    public void init() {
        hotelDAO = new HotelDAO(); // Assurez-vous que le DAO est configuré correctement
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null) {
            int hotelId = Integer.parseInt(idParam);
            try {
                // Récupérer l'hôtel avec ses types de chambres associés
                Hotel hotel = hotelDAO.getHotelByIdWithRoomTypes(hotelId);
                request.setAttribute("hotel", hotel);
                // Transférer l'objet à la page JSP
                request.getRequestDispatcher("hotelDetails.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du chargement des détails de l'hôtel.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID d'hôtel manquant.");
        }
    }
}
