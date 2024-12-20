package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Hotel;
import beans.RoomType;
import dao.HotelDAO;
import dao.RoomTypeDAO;

@WebServlet("/EditHotelServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,  // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class EditHotelServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIRECTORY = "hotel_images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        try {
            int hotelId = Integer.parseInt(idParam);
            HotelDAO hotelDAO = new HotelDAO();
            RoomTypeDAO roomTypeDAO = new RoomTypeDAO();

            Hotel hotel = hotelDAO.getHotelById(hotelId);
            List<RoomType> roomTypes = roomTypeDAO.getAllRoomTypes();

            if (hotel == null) {
                request.setAttribute("errorMessage", "Hôtel non trouvé.");
                response.sendRedirect("ListHotelsServlet");
                return;
            }

            request.setAttribute("hotel", hotel);
            request.setAttribute("roomTypes", roomTypes);
            request.getRequestDispatcher("editHotel.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListHotelsServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int hotelId = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String city = request.getParameter("city");
            String description = request.getParameter("description");
            int stars = Integer.parseInt(request.getParameter("stars"));

            String[] roomTypeIdsParam = request.getParameterValues("roomTypeIds");
            List<Integer> roomTypeIds = new ArrayList<>();
            if (roomTypeIdsParam != null) {
                for (String id : roomTypeIdsParam) {
                    roomTypeIds.add(Integer.parseInt(id));
                }
            }

            Hotel hotel = new Hotel();
            hotel.setId(hotelId);
            hotel.setName(name);
            hotel.setCity(city);
            hotel.setDescription(description);
            hotel.setStars(stars);

            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getSubmittedFileName(filePart);
                String imagePath = uploadImage(filePart, fileName, request);
                hotel.setImagePath(imagePath);
            } else {
                HotelDAO hotelDAO = new HotelDAO();
                Hotel existingHotel = hotelDAO.getHotelById(hotelId);
                hotel.setImagePath(existingHotel.getImagePath());
            }

            HotelDAO hotelDAO = new HotelDAO();
            hotelDAO.updateHotel(hotel, roomTypeIds);

            response.sendRedirect("ListHotelsServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la mise à jour : " + e.getMessage());
            doGet(request, response);
        }
    }

    private String uploadImage(Part filePart, String fileName, HttpServletRequest request)
            throws IOException {
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        String filePath = uploadPath + File.separator + uniqueFileName;

        try (InputStream input = filePart.getInputStream();
             FileOutputStream output = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }

        return UPLOAD_DIRECTORY + "/" + uniqueFileName;
    }

    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }
}
