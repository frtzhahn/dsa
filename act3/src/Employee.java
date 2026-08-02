import java.math.BigDecimal;

public class Employee {
    private final String name;
    private final BigDecimal dailyRate;
    private final BigDecimal daysWorked;
    private final BigDecimal overtimeHours;
    private final BigDecimal undertimeMinutes;

    public Employee(String name, BigDecimal dailyRate, BigDecimal daysWorked,
                    BigDecimal overtimeHours, BigDecimal undertimeMinutes) {
        this.name = name;
        this.dailyRate = dailyRate;
        this.daysWorked = daysWorked;
        this.overtimeHours = overtimeHours;
        this.undertimeMinutes = undertimeMinutes;
    }

    public String getName() { return name; }
    public BigDecimal getDailyRate() { return dailyRate; }
    public BigDecimal getDaysWorked() { return daysWorked; }
    public BigDecimal getOvertimeHours() { return overtimeHours; }
    public BigDecimal getUndertimeMinutes() { return undertimeMinutes; }
}
