package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Hotel;
import beans.RoomType;
import dao.HotelDAO;
import dao.RoomTypeDAO;

@WebServlet("/AddHotelServlet")
@MultipartConfig // Nécessaire pour gérer les fichiers envoyés
public class AddHotelServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddHotelServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
        List<RoomType> roomTypes = roomTypeDAO.getAllRoomTypes();

        if (roomTypes.isEmpty()) {
            System.out.println("No room types found!");
        }

        request.setAttribute("roomTypes", roomTypes);
        request.getRequestDispatcher("addHotel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("hotelName");
        String city = request.getParameter("city");
        String description = request.getParameter("description");
        String starsString = request.getParameter("stars");
        String[] roomTypeIds = request.getParameterValues("roomTypes");

        if (name == null || city == null || description == null || 
            starsString == null || roomTypeIds == null || 
            name.trim().isEmpty() || city.trim().isEmpty() || 
            description.trim().isEmpty() || starsString.trim().isEmpty() || 
            roomTypeIds.length == 0) {

            request.setAttribute("errorMessage", "All fields are required and at least one room type must be selected.");
            doGet(request, response);
            return;
        }

        List<Integer> roomTypeIdList = new ArrayList<>();
        for (String id : roomTypeIds) {
            try {
                roomTypeIdList.add(Integer.parseInt(id.trim()));
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid room type selection.");
                doGet(request, response);
                return;
            }
        }

        int stars;
        try {
            stars = Integer.parseInt(starsString.trim());
            if (stars < 1 || stars > 5) {
                throw new IllegalArgumentException("Stars must be between 1 and 5");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format for stars.");
            doGet(request, response);
            return;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            doGet(request, response);
            return;
        }

        Hotel hotel = new Hotel();
        hotel.setName(sanitizeInput(name));
        hotel.setCity(sanitizeInput(city));
        hotel.setDescription(sanitizeDescription(description));
        hotel.setStars(stars);

        Part filePart = request.getPart("image");
        String imagePath = null;
        if (filePart != null && filePart.getSize() > 0) {
            try {
                imagePath = handleFileUpload(filePart);
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error uploading image: " + e.getMessage());
                doGet(request, response);
                return;
            }
        }
        hotel.setImagePath(imagePath);

        HotelDAO hotelDAO = new HotelDAO();
        try {
            int hotelId = hotelDAO.addHotel(hotel, roomTypeIdList);
            response.sendRedirect("ListHotelsServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            doGet(request, response);
        }
    }

    // Method to sanitize user input
    private String sanitizeInput(String input) {
        return input == null ? null : input.trim().replaceAll("[<>]", ""); // Simple sanitization
    }

    // Method to sanitize descriptions
    private String sanitizeDescription(String description) {
        return description == null ? null : description.trim().replaceAll("[<>]", ""); // Example sanitization
    }

    // Method to handle file uploads
    private String handleFileUpload(Part filePart) throws IOException {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

        String uploadDir = getServletContext().getRealPath("/uploads");
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        String filePath = uploadDir + File.separator + uniqueFileName;
        filePart.write(filePath);

        return "uploads/" + uniqueFileName; // Return relative path
    }
}
