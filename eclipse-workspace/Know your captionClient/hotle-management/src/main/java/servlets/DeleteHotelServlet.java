package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import dao.HotelDAO;

/**
 * Servlet implementation class DeleteHotelServlet
 */
@WebServlet("/DeleteHotelServlet")
public class DeleteHotelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteHotelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hotelIdParam = request.getParameter("id");
        if (hotelIdParam != null && !hotelIdParam.isEmpty()) {
            try {
                int hotelId = Integer.parseInt(hotelIdParam);
                HotelDAO hotelDAO = new HotelDAO();

                boolean isDeleted = hotelDAO.deleteHotel(hotelId);

                if (isDeleted) {
                    request.setAttribute("successMessage", "Hôtel supprimé avec succès.");
                } else {
                    request.setAttribute("errorMessage", "Échec de la suppression de l'hôtel. L'hôtel peut ne pas exister.");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID d'hôtel invalide.");
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Une erreur est survenue lors de la suppression de l'hôtel : " + e.getMessage());
            }
        } else {
            request.setAttribute("errorMessage", "ID d'hôtel manquant.");
        }

        // Redirection vers la liste des hôtels
        request.getRequestDispatcher("ListHotelsServlet").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
