package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dao.UserDAO;
/**
 * Servlet implementation class DeleteAgentServlet
 */
@WebServlet("/DeleteAgentServlet")
public class DeleteAgentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAgentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the agent ID from the request
        String agentIdStr = request.getParameter("id");
        if (agentIdStr != null && !agentIdStr.isEmpty()) {
            try {
                int agentId = Integer.parseInt(agentIdStr);

                // Call the UserDAO to delete the agent
                UserDAO userDAO = new UserDAO();
                boolean isDeleted = userDAO.deleteAgent(agentId);

                // Redirect based on whether the deletion was successful
                if (isDeleted) {
                    response.sendRedirect("ListAgentServlet"); // Redirect to list of agents
                } else {
                    response.sendRedirect("ListAgentServlet"); // Could add an error message if deletion fails
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("ListAgentServlet"); // Invalid ID format
            }
        } else {
            response.sendRedirect("ListAgentServlet"); // No ID provided
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
