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
 * Servlet implementation class DeleteTypeRoomServlet
 */
@WebServlet("/DeleteTypeRoomServlet")
public class DeleteTypeRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RoomTypeDAO roomTypeDAO = new RoomTypeDAO();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteTypeRoomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("deleteRoom".equals(action)) {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            roomTypeDAO.deleteRoomById(roomId);
            response.sendRedirect("room-type-list.jsp"); // Rediriger après suppression
        }
    }


}
