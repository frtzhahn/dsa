import java.util.Scanner;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);
	private static final Record manager = new Record();

	// ansi color codes
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String CYAN = "\u001B[36m";

	public static void main(String[] args) {
		clearScreen();
		boolean running = true;

		while (running) {
			printMenu();
			int choice = getValidInteger(YELLOW + "Choose an option (1-6): " + RESET, 1, 6);

			switch (choice) {
				case 1:
					System.out.println("\n");
					manager.viewAllRecords();
					System.out.println("\n");
					break;
				case 2:
					handleCreate();
					break;
				case 3:
					handleUpdate();
					break;
				case 4:
					handleDelete();
					break;
				case 5:
					clearScreen();
					break;
				case 6:
					manager.saveToFile();
					running = false;
					break;
				default:
					System.out.println(RED + "\n[ERROR] Invalid menu choice" + RESET);
			}
		}
	}

	// clear terminal
	public static void clearScreen() {
		try {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			}
		} catch (Exception e) {
			System.out.print("\033[H\033[2J");
			System.out.flush();
		}
	}

	// main menu
	private static void printMenu() {
		System.out.println("\n" + CYAN + """
				░█▄█░█▀█░█▀▀░█░█░█▀█░▀░█▀▀░░░█▀▀░█░█░█▀█░█▀█░░░▀█▀░█▀█░█░█░█▀▀░█▀█░▀█▀░█▀█░█▀▄░█░█
				░█░█░█░█░█░░░█▀█░█▀█░░░▀▀█░░░▀▀█░█▀█░█░█░█▀▀░░░░█░░█░█░▀▄▀░█▀▀░█░█░░█░░█░█░█▀▄░░█░
				░▀░▀░▀▀▀░▀▀▀░▀░▀░▀░▀░░░▀▀▀░░░▀▀▀░▀░▀░▀▀▀░▀░░░░░▀▀▀░▀░▀░░▀░░▀▀▀░▀░▀░░▀░░▀▀▀░▀░▀░░▀░
								""" + RESET);
		System.out.println("1. View All Records");
		System.out.println("2. Add New Record");
		System.out.println("3. Update a Record");
		System.out.println("4. Delete a Record");
		System.out.println("5. Clear Console");
		System.out.println("6. Save & Exit");
	}

	// add record
	private static void handleCreate() {
		System.out.println("\n--- ADD RECORD ---");

		String id;
		while (true) {
			System.out.print(YELLOW + "Enter Unique ID (max 7 characters): " + RESET);
			id = scanner.nextLine().trim();
			if (id.isEmpty()) {
				System.out.println(RED + "[ERROR] ID cannot be empty" + RESET);
			} else if (id.length() > 7) {
				System.out.println(RED + "[ERROR] ID exceeds maximum limit of 7 characters" + RESET);
			} else if (manager.findById(id) != null) {
				System.out.println(RED + "[ERROR] That ID already exists" + RESET);
			} else {
				break;
			}
		}

		String name;
		while (true) {
			System.out.print(YELLOW + "Enter Item Name (max 30 characters, press Enter to default): " + RESET);
			name = scanner.nextLine().trim();
			if (name.isEmpty()) {
				name = "Unnamed Item";
				break;
			} else if (name.length() > 30) {
				System.out.println(RED + "[ERROR] Item name cannot exceed 30 characters" + RESET);
			} else {
				break;
			}
		}

		int quantity = getValidInteger(YELLOW + "Enter Quantity (0 - 99999): " + RESET, 0, 99999);
		double price = getValidDouble(YELLOW + "Enter Price (₱0.00 - ₱9999999.99): ₱" + RESET, 0.0, 9999999.99);

		Data newRecord = new Data(id, name, quantity, price);
		manager.addRecord(newRecord);
	}

	// update record
	private static void handleUpdate() {
		System.out.println("\n--- UPDATE RECORD ---");
		System.out.print(YELLOW + "Enter the ID of the record to update: " + RESET);
		String id = scanner.nextLine().trim();

		Data existing = manager.findById(id);
		if (existing == null) {
			System.out.println(RED + "[ERROR] Record with ID '" + id + "' not found" + RESET);
			return;
		}

		System.out.println("\nCurrent details: " + existing);

		String newName;
		while (true) {
			System.out.print(YELLOW + "Enter New Name (max 30 chars, press Enter to keep current): " + RESET);
			newName = scanner.nextLine().trim();
			if (newName.isEmpty()) {
				newName = existing.getName();
				break;
			} else if (newName.length() > 30) {
				System.out.println(RED + "[ERROR] Item name cannot exceed 30 characters" + RESET);
			} else {
				break;
			}
		}

		int newQty = getValidInteger(YELLOW + "Enter New Quantity (0 - 99999): " + RESET, 0, 99999);

		double newPrice = getValidDouble(YELLOW + "Enter New Price (₱0.00 - ₱9999999.99): ₱" + RESET, 0.0, 9999999.99);

		boolean success = manager.updateRecord(id, newName, newQty, newPrice);
		if (success) {
			System.out.println(GREEN + "\n[SUCCESS] Record updated in RAM Changes will save permanently on exit" + RESET);
		}
	}

	// delete function
	private static void handleDelete() {
		System.out.println("\n--- DELETE A RECORD ---");
		System.out.print(YELLOW + "Enter the ID of the record to delete: " + RESET);
		String id = scanner.nextLine().trim();

		boolean success = manager.deleteRecord(id);
		if (success) {
			System.out.println(GREEN + "\n[SUCCESS] Record deleted from RAM Changes will save permanently on exit." + RESET);
		} else {
			System.out.println(RED + "[ERROR] Record with ID '" + id + "' not found" + RESET);
		}
	}

	// option input exeception handling
	private static int getValidInteger(String prompt, int min, int max) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			try {
				int value = Integer.parseInt(input);
				if (value < min || value > max) {
					System.out.println(RED + String.format("[ERROR] Value must be between %d and %d", min, max) + RESET);
					continue;
				}
				return value;
			} catch (NumberFormatException e) {
				System.out.println(RED + "[ERROR] Invalid input enter a valid whole number" + RESET);
			}
		}
	}

	// exception handling for double inputs
	private static double getValidDouble(String prompt, double min, double max) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			try {
				double value = Double.parseDouble(input);
				if (value < min || value > max) {
					System.out.println(RED + String.format("[ERROR] Value must be between ₱%.2f and ₱%.2f", min, max) + RESET);
					continue;
				}
				return value;
			} catch (NumberFormatException e) {
				System.out.println(RED + "[ERROR] Invalid input enter a valid decimal number" + RESET);
			}
		}
	}
}
