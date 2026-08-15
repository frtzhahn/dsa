import java.math.BigDecimal;
import java.util.Scanner;

public class Console {
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String CYAN = "\u001B[36m";

	private final Scanner scanner;

	public Console(Scanner scanner) {
		this.scanner = scanner;
	}

	public String readStringWithDefault(String stepLabel, String defaultVal) {
		System.out.printf("%s%s%s [%sDefault: %s%s] › ",
				GREEN, stepLabel, RESET,
				CYAN, defaultVal, RESET);

		while (true) {
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				return defaultVal;
			}
			if (!input.isEmpty()) {
				return input;
			}
		}
	}

	public BigDecimal readBigDecimalNoDefault(String stepLabel, BigDecimal min, BigDecimal max) {
		System.out.printf("%s%s%s › ", GREEN, stepLabel, RESET);

		while (true) {
			String input = scanner.nextLine().trim();

			if (input.isEmpty()) {
				System.out.printf("  %s[!] Input required. Please enter a value.%s%n  › ", RED, RESET);
				continue;
			}

			try {
				BigDecimal value = new BigDecimal(input);
				if (value.compareTo(min) < 0) {
					System.out.printf("  %s[!] Value must be at least %s.%s%n  › ", RED, min.toPlainString(), RESET);
					continue;
				}
				if (max != null && value.compareTo(max) > 0) {
					System.out.printf("  %s[!] Value cannot exceed %s.%s%n  › ", RED, max.toPlainString(), RESET);
					continue;
				}
				return value;
			} catch (NumberFormatException e) {
				System.out.printf("  %s[!] Invalid numeric format. Please try again.%s%n  › ", RED, RESET);
			}
		}
	}

	public boolean readYesNo(String prompt) {
		System.out.print(prompt);
		while (true) {
			String input = scanner.nextLine().trim().toLowerCase();
			if (input.equals("y") || input.equals("yes")) {
				return true;
			}
			if (input.equals("n") || input.equals("no")) {
				return false;
			}
			System.out.printf("  %s[!] Please answer with 'y' or 'n': %s", RED, RESET);
		}
	}
}
