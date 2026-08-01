import java.util.*;

public class pos {

	public static final String end = "\u001B[0m", yellow = "\u001B[33m",
			green = "\u001B[32m", blue = "\u001B[34m", red = "\u001B[31m";

	static class Product {
		String code, name;
		double price;
		int stock;

		Product(String code, String name, double price, int stock) {
			this.code = code;
			this.name = name;
			this.price = price;
			this.stock = stock;
		}
	}

	static class CartItem {
		Product product;
		int quantity;

		CartItem(Product product, int quantity) {
			this.product = product;
			this.quantity = quantity;
		}

		double getLineTotal() {
			return product.price * quantity;
		}
	}

	static class Inventory {
		Map<String, Product> products = new LinkedHashMap<>();

		void addProduct(String code, String name, double price, int stock) {
			products.put(code, new Product(code, name, price, stock));
		}

		boolean deleteProduct(String code) {
			return products.remove(code) != null;
		}

		Product getProduct(String code) {
			return products.get(code);
		}

		boolean hasStock(String code, int qty) {
			Product p = products.get(code);
			return p != null && p.stock >= qty;
		}

		void reduceStock(String code, int qty) {
			Product p = products.get(code);
			if (p != null)
				p.stock -= qty;
		}

		void displayInventory() {
			System.out.println(green + "\n===================== INVENTORY =====================");
			System.out.printf("%-8s %-20s %-10s %-8s%n", "Code", "Name", "Price", "Stock");
			System.out.println("-------------------------------------------------------" + end);
			for (Product p : products.values()) {
				System.out.printf(yellow + "%-8s %-20s P%-9.2f %-8d%n" + end, p.code, p.name, p.price, p.stock);
			}
			System.out.println(green + "=======================================================\n" + end);
		}
	}

	static class Checkout {
		List<CartItem> cart = new ArrayList<>();
		static final double TAX_RATE = 0.12;
		static final double DISCOUNT_RATE = 0.05;

		void addToCart(Product product, int qty) {
			cart.add(new CartItem(product, qty));
		}

		double getSubtotal() {
			double subtotal = 0;
			for (CartItem item : cart)
				subtotal += item.getLineTotal();
			return subtotal;
		}

		double getTotal(boolean isMember) {
			double subtotal = getSubtotal();
			double afterDiscount = subtotal - (isMember ? subtotal * DISCOUNT_RATE : 0);
			return afterDiscount + (afterDiscount * TAX_RATE);
		}

		void printReceipt(boolean isMember, double cashPaid) {
			double subtotal = getSubtotal();
			double discount = isMember ? subtotal * DISCOUNT_RATE : 0;
			double afterDiscount = subtotal - discount;
			double tax = afterDiscount * TAX_RATE;
			double total = afterDiscount + tax;
			double change = cashPaid - total;

			System.out.println(green + "\n=================== RECEIPT ===================");
			System.out.printf("%-20s %-5s %-10s %-10s%n", "Item", "Qty", "Price", "Total");
			System.out.println("-------------------------------------------------");
			for (CartItem item : cart) {
				System.out.printf("%-20s %-5d P%-9.2f P%-9.2f%n",
						item.product.name, item.quantity, item.product.price, item.getLineTotal());
			}
			System.out.println("-------------------------------------------------");
			System.out.printf("%-30s P%.2f%n", "Subtotal:", subtotal);
			System.out.printf("%-30s P%.2f%n", "Discount (" + (isMember ? "5%" : "0%") + "):", discount);
			System.out.printf("%-30s P%.2f%n", "Tax (12%):", tax);
			System.out.printf("%-30s P%.2f%n", "TOTAL:", total);
			System.out.printf("%-30s P%.2f%n", "Cash Paid:", cashPaid);
			System.out.printf("%-30s P%.2f%n", "Change:", Math.max(0, change));
			System.out.println("=================================================\n" + end);

			if (change < 0) {
				System.out.printf("NOTE: Insufficient payment! Amount short: P%.2f%n", Math.abs(change));
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Inventory inventory = new Inventory();

		inventory.addProduct("P001", "Bottled Water", 15.00, 50);
		inventory.addProduct("P002", "Instant Noodles", 18.50, 100);
		inventory.addProduct("P003", "Canned Sardines", 25.00, 40);
		inventory.addProduct("P004", "White Bread", 55.00, 30);
		inventory.addProduct("P005", "Ballpen", 8.00, 200);

		int choice;
		do {
			System.out.println(blue);
			System.out.println(
					"                                                           _                                                                     ");
			System.out.println(
					" _____ _____ _____        _           _           _      _| |_    _                 _                              _             ");
			System.out.println(
					"|  _  |     |   __|   ___| |_ ___ ___| |_ ___ _ _| |_   |   __|  |_|___ _ _ ___ ___| |_ ___ ___ _ _    ___ _ _ ___| |_ ___ _____ ");
			System.out.println(
					"|   __|  |  |__   |  |  _|   | -_|  _| '_| . | | |  _|  |   __|  | |   | | | -_|   |  _| . |  _| | |  |_ -| | |_ -|  _| -_|     |");
			System.out.println(
					"|__|  |_____|_____|  |___|_|_|___|___|_,_|___|___|_|    |_   _|  |_|_|_|\\_/|___|_|_|_| |___|_| |_  |  |___|_  |___|_| |___|_|_|_|");
			System.out.println(
					"                                                          |_|                                  |___|      |___|");
			System.out
					.println("1. View Inventory\n2. Add New Product\n3. Delete Product\n4. Checkout / New Transaction\n5. Exit");
			System.out.print("Choose an option: " + end);

			while (!sc.hasNextInt()) {
				System.out.print(red + "Invalid input. Enter a number: " + end);
				sc.next();
			}
			choice = sc.nextInt();

			switch (choice) {
				case 1 -> inventory.displayInventory();
				case 2 -> addNewProduct(sc, inventory);
				case 3 -> deleteProduct(sc, inventory);
				case 4 -> processCheckout(sc, inventory);
				case 5 -> System.out.println("Thank you! Exiting program...");
				default -> System.out.println("Invalid choice. Try again.\n");
			}

		} while (choice != 5);

		sc.close();
	}

	static void addNewProduct(Scanner sc, Inventory inventory) {
		sc.nextLine();

		System.out.print(blue + "Enter product code: ");
		String code = sc.nextLine().trim();

		System.out.print("Enter product name: ");
		String name = sc.nextLine().trim();

		// validate price input
		double price = -1;
		while (true) {
			System.out.print("Enter price: ");
			if (sc.hasNextDouble()) {
				price = sc.nextDouble();
				if (price >= 0) {
					break;
				} else {
					System.out.println(red + "Price cannot be negative. Please try again." + end);
				}
			} else {
				System.out.println(red + "Invalid input! Please enter a valid number for price (e.g., 15.50)." + end);
				sc.next();
			}
		}

		// validates stock input
		int stock = -1;
		while (true) {
			System.out.print("Enter stock quantity: ");
			if (sc.hasNextInt()) {
				stock = sc.nextInt();
				if (stock >= 0) {
					break;
				} else {
					System.out.println(red + "Stock cannot be negative. Please try again." + end);
				}
			} else {
				System.out.println(red + "Invalid input! Please enter a valid whole number for stock." + end);
				sc.next();
			}
		}

		inventory.addProduct(code, name, price, stock);
		System.out.println(green + "Product added successfully!\n" + end);
	}

	static void deleteProduct(Scanner sc, Inventory inventory) {
		sc.nextLine();
		System.out.print(blue + "Enter product code to delete: " + end);
		String code = sc.nextLine().trim();

		if (inventory.deleteProduct(code)) {
			System.out.println(green + "Product deleted successfully!\n" + end);
		} else {
			System.out.println(red + "Product code not found.\n" + end);
		}
	}

	static void processCheckout(Scanner sc, Inventory inventory) {
		Checkout checkout = new Checkout();
		inventory.displayInventory();
		sc.nextLine();

		while (true) {
			System.out.print(blue + "Enter product code to add (or 'done' to finish): " + end);
			String input = sc.nextLine().trim();

			if (input.equalsIgnoreCase("done"))
				break;

			Product product = inventory.getProduct(input);
			if (product == null) {
				System.out.println(red + "Product not found. Try again.\n" + end);
				continue;
			}

			// validate quantity and stock inputs
			int qty = -1;
			while (true) {
				System.out.print("Enter quantity: ");
				if (sc.hasNextInt()) {
					qty = sc.nextInt();
					sc.nextLine();

					if (qty <= 0) {
						System.out.println(red + "Quantity must be greater than 0." + end);
					} else if (!inventory.hasStock(input, qty)) {
						// Lock user inside loop if stock is insufficient
						System.out.println(red + "Insufficient stock! Available: " + product.stock + end);
					} else {
						break;
					}
				} else {
					System.out.println(red + "Invalid input! Please enter a valid whole number." + end);
					sc.next();
				}
			}

			checkout.addToCart(product, qty);
			inventory.reduceStock(input, qty);
			System.out.println(green + qty + " x " + product.name + " added to cart.\n" + end);
		}

		if (checkout.cart.isEmpty()) {
			System.out.println(yellow + "No items in cart. Transaction cancelled.\n" + end);
			return;
		}

		// validate membership input
		boolean isMember = false;
		while (true) {
			System.out.print("Is the customer a member (5% discount)? (yes/no): ");
			String memberInput = sc.next().trim().toLowerCase();
			if (memberInput.equals("yes") || memberInput.equals("y")) {
				isMember = true;
				break;
			} else if (memberInput.equals("no") || memberInput.equals("n")) {
				isMember = false;
				break;
			} else {
				System.out.println(red + "Invalid input! Please type 'yes' or 'no'." + end);
			}
		}

		System.out.printf("TOTAL AMOUNT DUE: P%.2f%n", checkout.getTotal(isMember));

		// validate cash paid amount
		double cashPaid = -1;
		while (true) {
			System.out.print("Enter cash paid: P");
			if (sc.hasNextDouble()) {
				cashPaid = sc.nextDouble();
				if (cashPaid >= 0) {
					break;
				} else {
					System.out.println(red + "Payment amount cannot be negative." + end);
				}
			} else {
				System.out.println(red + "Invalid input! Please enter a valid cash amount (e.g., 500.00)." + end);
				sc.next();
			}
		}

		checkout.printReceipt(isMember, cashPaid);
	}
}
