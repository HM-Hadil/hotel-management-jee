package beans;

public class RoomType {
    private int id;
    private String label;
    private int capacity;
    private double price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	 @Override
	    public String toString() {
	        return "RoomType{" +
	               "id=" + id +
	               ", label='" + label + '\'' +
	               ", capacity='" + capacity + '\'' +
	               ", price=" + price +
	               '}';
	    }
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public RoomType(int id, String label, int capacity, double price) {
		super();
		this.id = id;
		this.label = label;
		this.capacity = capacity;
		this.price = price;
	}

	public RoomType() {
		super();
	}

    
}
