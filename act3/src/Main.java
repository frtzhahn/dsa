import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("--test")) {
			runSelfTest();
			return;
		}

		Scanner scanner = new Scanner(System.in);
		Console input = new Console(scanner);

		Payslip.printLaunchBanner();

		boolean keepRunning = true;
		while (keepRunning) {
			Employee emp = promptEmployeeData(input);
			Payroll payroll = new Payroll(emp);

			Payslip.printPayslip(payroll);

			keepRunning = input.readYesNo("\nCompute another payslip? [y/n]: ");
			System.out.println();
		}

		System.out.println("Thank you for using Mocha & Latte's Cafe Payroll System!");
	}

	private static Employee promptEmployeeData(Console input) {
		System.out.println("=== WEEKLY PAYROLL INPUTS ===");

		String name = input.readStringWithDefault(
				"[1/5] Employee Name",
				"Mocha Matcha Lover");

		BigDecimal dailyRate = input.readBigDecimalNoDefault(
				"[2/5] Daily Wage Rate (₱)",
				new BigDecimal("1.00"),
				new BigDecimal("100000.00"));

		BigDecimal daysWorked = input.readBigDecimalNoDefault(
				"[3/5] Days Worked",
				new BigDecimal("0.00"),
				new BigDecimal("7.00"));

		BigDecimal otHours = input.readBigDecimalNoDefault(
				"[4/5] Overtime Hours",
				new BigDecimal("0.00"),
				new BigDecimal("48.00"));

		BigDecimal undertimeMins = input.readBigDecimalNoDefault(
				"[5/5] Undertime Minutes",
				new BigDecimal("0.00"),
				new BigDecimal("2880.00"));

		return new Employee(name, dailyRate, daysWorked, otHours, undertimeMins);
	}

	// self test method
	private static void runSelfTest() {
		System.out.println(Console.GREEN);
		System.out.println("████████╗███████╗███████╗████████╗");
		System.out.println("╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝");
		System.out.println("   ██║   █████╗  ███████╗   ██║   ");
		System.out.println("   ██║   ██╔══╝  ╚════██║   ██║   ");
		System.out.println("   ██║   ███████╗███████║   ██║   ");
		System.out.println("   ╚═╝   ╚══════╝╚══════╝   ╚═╝   ");
		System.out.println(Console.RESET);
		Employee testEmp = new Employee(
				"Mocha Matcha Lover",
				new BigDecimal("600.00"),
				new BigDecimal("6.00"),
				new BigDecimal("6.00"),
				new BigDecimal("29.00"));

		Payroll pr = new Payroll(testEmp);

		boolean pass = true;
		pass &= assertEqual("Hourly Rate", new BigDecimal("75.00"), pr.getHourlyRate());
		pass &= assertEqual("Minute Rate", new BigDecimal("1.25"), pr.getMinuteRate());
		pass &= assertEqual("OT Hourly Rate", new BigDecimal("93.75"), pr.getOtHourlyRate());
		pass &= assertEqual("Basic Earnings", new BigDecimal("3600.00"), pr.getBasicEarnings());
		pass &= assertEqual("Overtime Pay", new BigDecimal("562.50"), pr.getOvertimePay());
		pass &= assertEqual("Gross Earnings", new BigDecimal("4162.50"), pr.getGrossEarnings());
		pass &= assertEqual("SSS Deduction (3.5%)", new BigDecimal("145.69"), pr.getSssDeduction());
		pass &= assertEqual("PhilHealth (2.0%)", new BigDecimal("83.25"), pr.getPhilHealthDeduction());
		pass &= assertEqual("Pag-IBIG (1.2%)", new BigDecimal("49.95"), pr.getPagIbigDeduction());
		pass &= assertEqual("Undertime Penalty", new BigDecimal("36.25"), pr.getUndertimePenalty());
		pass &= assertEqual("Total Deductions", new BigDecimal("315.14"), pr.getTotalDeductions());
		pass &= assertEqual("Net Earnings", new BigDecimal("3847.36"), pr.getNetEarnings());

		if (pass) {
			System.out.println("\n[SUCCESS] All verification assertions passed!");
		} else {
			System.err.println("\n[FAILURE] Verification test failed!");
			System.exit(1);
		}
	}

	private static boolean assertEqual(String field, BigDecimal expected, BigDecimal actual) {
		if (expected.compareTo(actual) == 0) {
			System.out.printf("  [PASS] %-24s : %s%n", field, actual);
			return true;
		} else {
			System.err.printf("  [FAIL] %-24s : Expected %s, got %s%n", field, expected, actual);
			return false;
		}
	}
}
