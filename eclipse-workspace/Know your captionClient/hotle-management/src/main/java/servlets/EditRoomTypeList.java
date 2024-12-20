package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import beans.RoomType;
import dao.RoomTypeDAO;

/**
 * Servlet implementation class EditRoomTypeList
 */
@WebServlet("/EditRoomTypeList")
public class EditRoomTypeList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRoomTypeList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomIdParam = request.getParameter("id");
        if (roomIdParam != null && !roomIdParam.isEmpty()) {
            try {
                int roomId = Integer.parseInt(roomIdParam);
                RoomTypeDAO roomTypeDAO = new RoomTypeDAO();

                RoomType roomType = roomTypeDAO.getRoomById(roomId);
                if (roomType != null) {
                    request.setAttribute("roomType", roomType);
                    request.getRequestDispatcher("editRoomType.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Le type de chambre spécifié est introuvable.");
                    response.sendRedirect("RoomTypeListServlet");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID de type de chambre invalide.");
                response.sendRedirect("RoomTypeListServlet");
            }
        } else {
            response.sendRedirect("RoomTypeListServlet");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String label = request.getParameter("label");
        String capacityParam = request.getParameter("capacity");
        String priceParam = request.getParameter("price");

        if (idParam != null && label != null && capacityParam != null && priceParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                int capacity = Integer.parseInt(capacityParam);
                double price = Double.parseDouble(priceParam);

                RoomType roomType = new RoomType(id, label, capacity, price);
                RoomTypeDAO roomTypeDAO = new RoomTypeDAO();

                boolean isUpdated = roomTypeDAO.updateRoom(roomType);
                if (isUpdated) {
                    response.sendRedirect("RoomTypeListServlet");
                } else {
                    request.setAttribute("errorMessage", "Échec de la mise à jour du type de chambre.");
                    request.getRequestDispatcher("editRoomType.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Données invalides.");
                request.getRequestDispatcher("editRoomType.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Tous les champs sont obligatoires.");
            request.getRequestDispatcher("editRoomType.jsp").forward(request, response);
        }
    }

}
