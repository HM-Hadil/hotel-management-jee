package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dao.UserDAO;
import beans.Users;
/**
 * Servlet implementation class UpdateAgentServlet
 */
@WebServlet("/UpdateAgentServlet")
public class UpdateAgentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAgentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        Users agent = new Users(username, null, role, email);  // Don't modify password
        agent.setId(id);

        UserDAO userDAO = new UserDAO();
        boolean isUpdated = userDAO.updateAgent(agent);

        if (isUpdated) {
            response.sendRedirect("listAgent.jsp");  // Redirect to list page if updated successfully
        } else {
            request.setAttribute("errorMessage", "Error while updating the agent.");
            request.getRequestDispatcher("editAgent.jsp").forward(request, response);
        }
    }
}
