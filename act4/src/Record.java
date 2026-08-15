import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Record {
	private static final String FILE_NAME = "database.ser";
	private List<Data> records;

	// ansi color codes
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String CYAN = "\u001B[36m";

	// loads existing data
	public Record() {
		this.records = loadFromFile();
	}

	// add new record function
	public void addRecord(Data record) {
		records.add(record);
		System.out.println(GREEN + "\n[SUCCESS] Record added successfully" + RESET);
	}

	// table display function
	public void viewAllRecords() {
		if (records.isEmpty()) {
			System.out.println(RED + "\n[ERROR] No records found database is empty" + RESET);
			return;
		}

		System.out.println(CYAN + """
				░█▀▀░█░█░█▀█░█▀█░░░█▀▄░█▀█░▀█▀░█▀█░█▀▄░█▀█░█▀▀░█▀▀
				░▀▀█░█▀█░█░█░█▀▀░░░█░█░█▀█░░█░░█▀█░█▀▄░█▀█░▀▀█░█▀▀
				░▀▀▀░▀░▀░▀▀▀░▀░░░░░▀▀░░▀░▀░░▀░░▀░▀░▀▀░░▀░▀░▀▀▀░▀▀▀
				""" + RESET);

		int tableWidth = 68;
		String doubleBorder = CYAN + "=".repeat(tableWidth) + RESET;
		String singleBorder = CYAN + "-".repeat(tableWidth) + RESET;

		System.out.println(doubleBorder);
		System.out.println(
				CYAN + String.format("| %-7s | %-30s | %-8s | %-10s |", "ID", "NAME", "QUANTITY", "PRICE (₱)") + RESET);
		System.out.println(singleBorder);
		for (Data r : records) {
			System.out.println(r.toTableRow());
		}
		System.out.println(doubleBorder);
	}

	// update function
	public boolean updateRecord(String id, String newName, int newQty, double newPrice) {
		Data record = findById(id);
		if (record != null) {
			record.setName(newName);
			record.setQuantity(newQty);
			record.setPrice(newPrice);
			return true;
		}
		return false;
	}

	// delete function
	public boolean deleteRecord(String id) {
		Data record = findById(id);
		if (record != null) {
			records.remove(record);
			return true;
		}
		return false;
	}

	// record searcher
	public Data findById(String id) {
		for (Data r : records) {
			if (r.getId().equalsIgnoreCase(id)) {
				return r;
			}
		}
		return null;
	}

	// saves array list into database file
	public void saveToFile() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			oos.writeObject(records);
			System.out.println(GREEN + "[SUCCESS] Data permanently saved to '" + FILE_NAME + RESET);
		} catch (IOException e) {
			System.out.println(RED + "[ERROR] Failed to save data to disk: " + e.getMessage() + RESET);
		}
	}

	// fetches file data back to program on runtime
	@SuppressWarnings("unchecked")
	private List<Data> loadFromFile() {
		File file = new File(FILE_NAME);
		if (!file.exists()) {
			return new ArrayList<>();
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			return (List<Data>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(RED + "[ERROR] Data file corrupted or unreadable" + RESET);
			return new ArrayList<>();
		}
	}
}
