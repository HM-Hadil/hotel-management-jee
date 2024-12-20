package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import beans.Hotel;
import dao.HotelDAO;
/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HotelDAO hotelDAO = new HotelDAO();
        try {
            List<Hotel> hotels = hotelDAO.getAllHotels();  // Récupère la liste des hôtels
            if (hotels != null && !hotels.isEmpty()) {
                request.setAttribute("hotels", hotels);  // Transmet la liste à la JSP
            } else {
                request.setAttribute("errorMessage", "Aucun hôtel trouvé pour le moment");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur de récupération des hôtels");
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);  // Transmet la requête à la JSP
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
