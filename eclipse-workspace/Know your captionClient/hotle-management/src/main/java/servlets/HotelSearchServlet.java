package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import beans.Hotel;
import dao.HotelDAO;

@WebServlet("/HotelSearchServlet")
public class HotelSearchServlet extends HttpServlet {
    private HotelDAO hotelDAO;

    @Override
    public void init() throws ServletException {
        hotelDAO = new HotelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Récupérer les paramètres de recherche
            String hotelName = request.getParameter("hotelName");
            String city = request.getParameter("city");
            String starsParam = request.getParameter("stars");
            String minPriceParam = request.getParameter("minPrice");
            String maxPriceParam = request.getParameter("maxPrice");
            String roomType = request.getParameter("roomType");

            // Conversion des paramètres
            Integer stars = parseInteger(starsParam);
            Double minPrice = parseDouble(minPriceParam);
            Double maxPrice = parseDouble(maxPriceParam);

            // Récupérer les listes pour les filtres
            List<String> cities = hotelDAO.getDistinctCities();
            List<Integer> availableStars = hotelDAO.getAvailableStars();
            List<String> roomTypes = hotelDAO.getDistinctRoomTypes();
            double[] priceRange = hotelDAO.getPriceRange();

            List<Hotel> hotels;

            // Vérifier si des paramètres de recherche sont présents
            boolean hasSearchParams = isNotEmpty(hotelName) || isNotEmpty(city) || stars != null ||
                                       minPrice != null || maxPrice != null || isNotEmpty(roomType);

            if (hasSearchParams) {
                // Effectuer la recherche avec les critères
                hotels = hotelDAO.searchHotels(hotelName, city, stars, minPrice, maxPrice, roomType);
            } else {
                // Aucun paramètre de recherche, afficher tous les hôtels
                hotels = hotelDAO.getAllHotels();
            }

            // Définir les attributs pour la vue
            request.setAttribute("hotels", hotels);
            request.setAttribute("cities", cities);
            request.setAttribute("roomTypes", roomTypes);
            request.setAttribute("availableStars", availableStars);
            request.setAttribute("minPrice", priceRange[0]);
            request.setAttribute("maxPrice", priceRange[1]);

            // Conserver les paramètres de recherche pour le formulaire
            request.setAttribute("searchHotelName", hotelName);
            request.setAttribute("searchCity", city);
            request.setAttribute("searchStars", stars);
            request.setAttribute("searchMinPrice", minPrice);
            request.setAttribute("searchMaxPrice", maxPrice);
            request.setAttribute("searchRoomType", roomType);

            // Rediriger vers la page de résultats
            request.getRequestDispatcher("/hotels.jsp").forward(request, response);

        } catch (SQLException e) {
            log("Erreur SQL lors de la recherche des hôtels", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erreur lors de la recherche des hôtels. Veuillez réessayer plus tard.");
        } catch (Exception e) {
            log("Erreur inattendue", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Une erreur inattendue est survenue. Veuillez réessayer plus tard.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Méthode utilitaire pour convertir une chaîne en Integer.
     */
    private Integer parseInteger(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            log("Erreur de conversion en entier : " + value, e);
            return null;
        }
    }

    /**
     * Méthode utilitaire pour convertir une chaîne en Double.
     */
    private Double parseDouble(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            log("Erreur de conversion en double : " + value, e);
            return null;
        }
    }

    /**
     * Vérifie si une chaîne n'est ni null ni vide.
     */
    private boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
