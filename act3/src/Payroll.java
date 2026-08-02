import java.math.BigDecimal;
import java.math.RoundingMode;

public class Payroll {
	public static final BigDecimal WORK_HOURS_PER_DAY = new BigDecimal("8");
	public static final BigDecimal MINUTES_PER_HOUR = new BigDecimal("60");
	public static final BigDecimal OT_RATE_MULTIPLIER = new BigDecimal("1.25");

	// Fixed statutory rates applied against Gross Earnings
	public static final BigDecimal SSS_RATE = new BigDecimal("0.035"); // 3.5%
	public static final BigDecimal PHILHEALTH_RATE = new BigDecimal("0.020"); // 2.0%
	public static final BigDecimal PAGIBIG_RATE = new BigDecimal("0.012"); // 1.2%

	private final Employee employee;

	private final BigDecimal hourlyRate;
	private final BigDecimal minuteRate;
	private final BigDecimal otHourlyRate;

	private final BigDecimal basicEarnings;
	private final BigDecimal overtimePay;
	private final BigDecimal grossEarnings;

	private final BigDecimal sssDeduction;
	private final BigDecimal philHealthDeduction;
	private final BigDecimal pagIbigDeduction;
	private final BigDecimal undertimePenalty;
	private final BigDecimal totalDeductions;

	private final BigDecimal netEarnings;

	public Payroll(Employee employee) {
		this.employee = employee;

		// 1. Rate Conversions
		this.hourlyRate = employee.getDailyRate()
				.divide(WORK_HOURS_PER_DAY, 4, RoundingMode.HALF_UP);

		this.minuteRate = this.hourlyRate
				.divide(MINUTES_PER_HOUR, 4, RoundingMode.HALF_UP);

		this.otHourlyRate = this.hourlyRate
				.multiply(OT_RATE_MULTIPLIER)
				.setScale(4, RoundingMode.HALF_UP);

		// 2. Earnings
		this.basicEarnings = employee.getDaysWorked()
				.multiply(employee.getDailyRate())
				.setScale(2, RoundingMode.HALF_UP);

		this.overtimePay = employee.getOvertimeHours()
				.multiply(this.otHourlyRate)
				.setScale(2, RoundingMode.HALF_UP);

		this.grossEarnings = this.basicEarnings.add(this.overtimePay);

		// 3. Separate Statutory Deductions (based on Gross)
		this.sssDeduction = grossEarnings.multiply(SSS_RATE).setScale(2, RoundingMode.HALF_UP);
		this.philHealthDeduction = grossEarnings.multiply(PHILHEALTH_RATE).setScale(2, RoundingMode.HALF_UP);
		this.pagIbigDeduction = grossEarnings.multiply(PAGIBIG_RATE).setScale(2, RoundingMode.HALF_UP);

		// 4. Dynamic Undertime Penalty
		this.undertimePenalty = employee.getUndertimeMinutes()
				.multiply(this.minuteRate)
				.setScale(2, RoundingMode.HALF_UP);

		// 5. Total Deductions & Net Pay
		this.totalDeductions = sssDeduction
				.add(philHealthDeduction)
				.add(pagIbigDeduction)
				.add(undertimePenalty);

		this.netEarnings = grossEarnings.subtract(totalDeductions);
	}

	public Employee getEmployee() {
		return employee;
	}

	public BigDecimal getHourlyRate() {
		return hourlyRate.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getMinuteRate() {
		return minuteRate.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getOtHourlyRate() {
		return otHourlyRate.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getBasicEarnings() {
		return basicEarnings;
	}

	public BigDecimal getOvertimePay() {
		return overtimePay;
	}

	public BigDecimal getGrossEarnings() {
		return grossEarnings;
	}

	public BigDecimal getSssDeduction() {
		return sssDeduction;
	}

	public BigDecimal getPhilHealthDeduction() {
		return philHealthDeduction;
	}

	public BigDecimal getPagIbigDeduction() {
		return pagIbigDeduction;
	}

	public BigDecimal getUndertimePenalty() {
		return undertimePenalty;
	}

	public BigDecimal getTotalDeductions() {
		return totalDeductions;
	}

	public BigDecimal getNetEarnings() {
		return netEarnings;
	}
}
