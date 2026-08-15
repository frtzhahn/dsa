import java.io.Serializable;

public class Data implements Serializable {
	private static final long serialVersionUID = 1L;

	// table categories
	private String id;
	private String name;
	private int quantity;
	private double price;

	// constructor for user inputs
	public Data(String id, String name, int quantity, double price) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	// getters/setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// table row layout
	public String toTableRow() {
		return String.format("| %-7s | %-30s | %-8d | %-10.2f |", id, name, quantity, price);
	}

	// update row display layout
	@Override
	public String toString() {
		return String.format("[ID: %s | NAME: %s | QUANTITY: %d | PRICE: ₱%.2f]", id, name, quantity, price);
	}
}
