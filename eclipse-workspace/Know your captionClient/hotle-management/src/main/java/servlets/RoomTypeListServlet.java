package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import beans.RoomType;
import dao.RoomTypeDAO;

/**
 * Servlet implementation class RoomTypeListServlet
 */
@WebServlet("/RoomTypeListServlet")
public class RoomTypeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomTypeListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private RoomTypeDAO roomTypeDAO;

    @Override
    public void init() throws ServletException {
        roomTypeDAO = new RoomTypeDAO();
    }

    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<RoomType> rooms = roomTypeDAO.getAllRoomTypes(); // Méthode pour obtenir les chambres
        request.setAttribute("rooms", rooms);  // Passer l'attribut 'rooms' à la JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/room-type-list.jsp");
        dispatcher.forward(request, response);
    }
    
 
}
