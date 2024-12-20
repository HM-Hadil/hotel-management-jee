package beans;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private int id;
    private String name;
    private String city;
    private String description;
    private int stars;
    private String imagePath;

    // Liste des types de chambres
    private List<RoomType> roomTypes;

    
    // Ajouter un type de chambre à la liste
    public void addRoomType(RoomType roomType) {
        if (this.roomTypes == null) {
            this.roomTypes = new ArrayList<>();
        }
        this.roomTypes.add(roomType);
    }
    // Getters et Setters
    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }

	public Hotel() {
		super();
	}

	public Hotel(int id, String name, String city, String description, int stars, String imagePath,
			List<RoomType> roomTypes) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.description = description;
		this.stars = stars;
		this.imagePath = imagePath;
		this.roomTypes = roomTypes;
	}
	  // Constructeur simplifié pour les opérations de base
    public Hotel(int id, String name, String city, int stars) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "Hotel{name='" + name + "', city='" + city + "', description='" + description + "', stars=" + stars + ", imagePath='" + imagePath + "'}";
    }
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
