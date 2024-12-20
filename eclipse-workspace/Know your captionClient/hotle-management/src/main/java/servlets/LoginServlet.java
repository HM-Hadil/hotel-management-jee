package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import beans.Users;
import dao.UserDAO;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
	   protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");

	        UserDAO userDAO = new UserDAO();
	        Users user = userDAO.login(username, password);

	        if (user != null) {
	            // Stocker les informations de l'utilisateur dans la session
	            HttpSession session = request.getSession();
	            session.setAttribute("user", user);
	            session.setAttribute("adminName", user.getUsername()); 
	            session.setAttribute("agentName", user.getUsername());


	            // Rediriger vers la page appropriée selon le rôle
	            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
	                response.sendRedirect("adminDashboard.jsp");
	            } else if ("AGENT".equalsIgnoreCase(user.getRole())) {
	                response.sendRedirect("AgentDashboard.jsp");
	            } else {
	                response.sendRedirect("visitorDashboard.jsp");
	            }
	        }
 else {
	            // Retourner à la page de connexion avec un message d'erreur
	            request.setAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect !");
	            request.getRequestDispatcher("login.jsp").forward(request, response);
	        }
	    }

}
