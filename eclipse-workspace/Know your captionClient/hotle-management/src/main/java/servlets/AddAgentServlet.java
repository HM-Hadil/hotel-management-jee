package servlets;

import jakarta.servlet.ServletException;
import beans.Users;
import dao.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AddAgentServlet
 */
@WebServlet("/AddAgentServlet")
public class AddAgentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAgentServlet() {
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
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        String role = request.getParameter("role");
	        String email = request.getParameter("email");

	        // Create a new Users object and set its fields
	        Users newUser = new Users(username, password, role, email);

	        // Create a UserDAO object and call the addAgent method
	        UserDAO userDAO = new UserDAO();
	        boolean isSuccess = userDAO.addAgent(newUser);

	        // If agent is successfully added, redirect to the listAgent page
	        if (isSuccess) {
	            response.sendRedirect("listAgent.jsp");  // Redirect to the list of agents
	        } else {
	            // If there is an error, redirect back to the add agent page with an error message
	            request.setAttribute("errorMessage", "Error while adding the agent.");
	            request.getRequestDispatcher("addAgent.jsp").forward(request, response);
	        }
	    }
}
