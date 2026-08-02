import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Payslip {
	public static final String RESET = "\u001B[0m";
	public static final String CYAN = "\u001B[36m";
	public static final String BOLD_GREEN = "\u001B[1;32m";
	public static final String YELLOW = "\u001B[33m";

	public static void printLaunchBanner() {
		System.out.println(CYAN);
		System.out.println("██████╗  █████╗ ██╗    ██╗██████╗  ██████╗ ██║     ██║");
		System.out.println("██╔══██╗██╔══██╗╚██╗ ██╔╝██╔══██╗██╔═══██╗██║     ██║");
		System.out.println("██████╔╝███████║ ╚████╔╝ ██████╔╝██║   ██║██║     ██║");
		System.out.println("██╔═══╝ ██╔══██║  ╚██╔╝  ██╔══██╗██║   ██║██║     ██║");
		System.out.println("██║     ██║  ██║   ██║   ██║  ██║╚██████╔╝███████╗███████╗");
		System.out.println("╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝");
		System.out.println("             MOCHA & LATTE'S CAFE PAYROLL" + RESET);
		System.out.println();
	}

	public static void printPayslipHeader() {
		System.out.println(CYAN);
		System.out.println("██████╗  █████╗ ██╗    ██╗███████╗██╗     ██╗██████╗ ");
		System.out.println("██╔══██╗██╔══██╗╚██╗ ██╔╝██╔════╝██║     ██║██╔══██╗");
		System.out.println("██████╔╝███████║ ╚████╔╝ ███████╗██║     ██║██████╔╝");
		System.out.println("██╔═══╝ ██╔══██║  ╚██╔╝  ╚════██║██║     ██║██╔═══╝ ");
		System.out.println("██║     ██║  ██║   ██║   ███████║███████╗██║██║     ");
		System.out.println("╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝╚══════╝╚═╝╚═╝     " + RESET);
	}

	public static void printPayslip(Payroll payroll) {
		Employee emp = payroll.getEmployee();

		printPayslipHeader();

		System.out.println("=================================================================");
		System.out.println("                    MOCHA & LATTE'S CAFE");
		System.out.println("            Statement of Earnings and Deductions");
		System.out.println("=================================================================");
		System.out.printf("Employee Name : %s%n", emp.getName());
		System.out.println("Period        : Weekly Payroll");
		System.out.println("-----------------------------------------------------------------");

		System.out.println("GROSS EARNINGS");
		System.out.printf("  Basic Daily     %-4s days @ ₱%-8s / day       = ₱%10s%n",
				fmtQty(emp.getDaysWorked()), fmt(emp.getDailyRate()), fmt(payroll.getBasicEarnings()));
		System.out.printf("  Overtime        %-4s hrs  @ ₱%-8s / hr        = ₱%10s%n",
				fmtQty(emp.getOvertimeHours()), fmt(payroll.getOtHourlyRate()), fmt(payroll.getOvertimePay()));
		System.out.println("  ---------------------------------------------------------------");
		System.out.printf("  %sSUB-TOTAL 1 (Gross Earnings)                       = ₱%10s%s%n",
				CYAN, fmt(payroll.getGrossEarnings()), RESET);

		System.out.println("\nDEDUCTIONS");
		System.out.printf("  SSS        (3.5%% of Gross)                         = ₱%10s%n",
				fmt(payroll.getSssDeduction()));
		System.out.printf("  PhilHealth (2.0%% of Gross)                         = ₱%10s%n",
				fmt(payroll.getPhilHealthDeduction()));
		System.out.printf("  Pag-IBIG   (1.2%% of Gross)                         = ₱%10s%n",
				fmt(payroll.getPagIbigDeduction()));
		System.out.printf("  Undertime  %-4s mins @ ₱%-8s / min            = ₱%10s%n",
				fmtQty(emp.getUndertimeMinutes()), fmt(payroll.getMinuteRate()), fmt(payroll.getUndertimePenalty()));
		System.out.println("  ---------------------------------------------------------------");
		System.out.printf("  SUB-TOTAL 2 (Total Deductions)                     = ₱%10s%n",
				fmt(payroll.getTotalDeductions()));

		System.out.println("=================================================================");
		System.out.printf("%sNET EARNINGS                                         = ₱%s%s%n",
				BOLD_GREEN, fmt(payroll.getNetEarnings()), RESET);
		System.out.println("=================================================================");
	}

	private static String fmt(BigDecimal val) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(val);
	}

	private static String fmtQty(BigDecimal val) {
		DecimalFormat df = new DecimalFormat("0.#");
		return df.format(val);
	}
}
