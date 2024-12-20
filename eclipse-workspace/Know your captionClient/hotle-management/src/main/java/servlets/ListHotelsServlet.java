package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import beans.Hotel;
import dao.HotelDAO;

@WebServlet(urlPatterns = {"/ListHotelsServlet", "/hotel_images/*"})
public class ListHotelsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIRECTORY = "hotel_images";
    private HotelDAO hotelDAO;

    @Override
    public void init() throws ServletException {
        hotelDAO = new HotelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        // Si le chemin commence par /hotel_images/, c'est une requête d'image
        if (request.getRequestURI().contains("/hotel_images/")) {
            serveImage(request, response);
            return;
        }
        
        // Sinon, c'est une requête pour la liste des hôtels
        try {
            List<Hotel> hotels = hotelDAO.getAllHotels();
            request.setAttribute("hotels", hotels);
            
            // Pour le debug
            System.out.println("Nombre d'hôtels récupérés : " + hotels.size());
            for (Hotel hotel : hotels) {
                System.out.println("Hôtel: " + hotel.getName() + ", Image: " + hotel.getImagePath());
            }
            
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Une erreur est survenue lors de la récupération des hôtels: " + e.getMessage());
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/listHotels.jsp").forward(request, response);
    }

    private void serveImage(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String imagePath = request.getRequestURI().substring(
            request.getContextPath().length() + 1
        );
        
        // Obtenir le chemin absolu du répertoire d'images
        String applicationPath = request.getServletContext().getRealPath("");
        File imageFile = new File(applicationPath + File.separator + imagePath);
        
        System.out.println("Tentative d'accès à l'image: " + imageFile.getAbsolutePath());
        
        // Vérifier si le fichier existe
        if (!imageFile.exists()) {
            System.out.println("Image non trouvée: " + imageFile.getAbsolutePath());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Définir le type de contenu
        String contentType = getServletContext().getMimeType(imageFile.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);
        response.setContentLength((int) imageFile.length());

        // Envoyer l'image
        try (FileInputStream in = new FileInputStream(imageFile);
             OutputStream out = response.getOutputStream()) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi de l'image: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}