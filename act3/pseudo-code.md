DEFINE CLASS Employee
    ATTRIBUTES: name, dailyRate, daysWorked, overtimeHours, undertimeMinutes

DEFINE CLASS Payroll
    CONSTANTS:
        WORK_HOURS_PER_DAY = 8
        MINUTES_PER_HOUR = 60
        OT_MULTIPLIER = 1.25
        SSS_RATE = 0.035
        PHILHEALTH_RATE = 0.020
        PAGIBIG_RATE = 0.012

    ATTRIBUTES:
        employee, hourlyRate, minuteRate, otHourlyRate,
        basicEarnings, overtimePay, grossEarnings,
        sssDeduction, philHealthDeduction, pagIbigDeduction,
        undertimePenalty, totalDeductions, netEarnings

    CONSTRUCTOR(employee)
        this.employee = employee

        // Rate conversions
        hourlyRate = employee.dailyRate / WORK_HOURS_PER_DAY
        minuteRate = hourlyRate / MINUTES_PER_HOUR
        otHourlyRate = hourlyRate * OT_MULTIPLIER

        // Earnings
        basicEarnings = employee.daysWorked * employee.dailyRate
        overtimePay = employee.overtimeHours * otHourlyRate
        grossEarnings = basicEarnings + overtimePay

        // Statutory deductions & undertime penalty
        sssDeduction = grossEarnings * SSS_RATE
        philHealthDeduction = grossEarnings * PHILHEALTH_RATE
        pagIbigDeduction = grossEarnings * PAGIBIG_RATE
        undertimePenalty = employee.undertimeMinutes * minuteRate

        totalDeductions = sssDeduction + philHealthDeduction + pagIbigDeduction + undertimePenalty
        netEarnings = grossEarnings - totalDeductions


DEFINE CLASS ConsoleInput
    FUNCTION readStringWithDefault(label, defaultVal)
        LOOP
            READ input FROM user
            IF input IS EMPTY THEN RETURN defaultVal
            IF input IS NOT EMPTY THEN RETURN input
        END LOOP

    FUNCTION readBigDecimalNoDefault(label, min, max)
        LOOP
            READ input FROM user
            IF input IS EMPTY THEN
                PRINT "Input required."
                CONTINUE
            END IF

            IF input IS NUMERIC THEN
                val = CONVERT_TO_NUMBER(input)
                IF val >= min AND (max IS NULL OR val <= max) THEN
                    RETURN val
                ELSE
                    PRINT "Value out of bounds."
                END IF
            ELSE
                PRINT "Invalid numeric format."
            END IF
        END LOOP

    FUNCTION readYesNo(prompt)
        LOOP
            READ input FROM user
            IF input IS "y" OR "yes" THEN RETURN true
            IF input IS "n" OR "no" THEN RETURN false
            PRINT "Please answer with 'y' or 'n'."
        END LOOP


DEFINE CLASS PayslipPrinter
    FUNCTION printLaunchBanner()
        PRINT ANSI Shadow ASCII "PAYROLL"
        PRINT "MOCHA & LATTE'S CAFE PAYROLL"

    FUNCTION printPayslipHeader()
        PRINT ANSI Shadow ASCII "PAYSLIP"

    FUNCTION printPayslip(payroll)
        CALL printPayslipHeader()
        PRINT "================================================="
        PRINT "             MOCHA & LATTE'S CAFE"
        PRINT "     Statement of Earnings and Deductions"
        PRINT "================================================="
        PRINT "Employee Name : " + payroll.employee.name
        PRINT "Period        : Weekly Payroll"
        PRINT "-------------------------------------------------"
        PRINT "GROSS EARNINGS"
        PRINT "  Basic Daily Pay  : " + payroll.basicEarnings
        PRINT "  Overtime Pay     : " + payroll.overtimePay
        PRINT "  SUB-TOTAL 1 (Gross) = " + payroll.grossEarnings
        PRINT ""
        PRINT "DEDUCTIONS"
        PRINT "  SSS (3.5%)       : " + payroll.sssDeduction
        PRINT "  PhilHealth (2.0%): " + payroll.philHealthDeduction
        PRINT "  Pag-IBIG (1.2%)   : " + payroll.pagIbigDeduction
        PRINT "  Undertime Penalty: " + payroll.undertimePenalty
        PRINT "  SUB-TOTAL 2 (Deductions) = " + payroll.totalDeductions
        PRINT "================================================="
        PRINT "NET EARNINGS        = " + payroll.netEarnings
        PRINT "================================================="


// MAIN EXECUTION
BEGIN MAIN
    IF args CONTAINS "--test" THEN
        CALL runSelfTest()
        EXIT PROGRAM
    END IF

    CALL PayslipPrinter.printLaunchBanner()
    CREATE ConsoleInput input

    REPEAT
        PRINT "=== WEEKLY PAYROLL INPUTS ==="
        name = input.readStringWithDefault("[1/5] Employee Name", "Mocha Matcha Lover")
        dailyRate = input.readBigDecimalNoDefault("[2/5] Daily Wage Rate", 1.00, 100000.00)
        daysWorked = input.readBigDecimalNoDefault("[3/5] Days Worked", 0.00, 7.00)
        otHours = input.readBigDecimalNoDefault("[4/5] Overtime Hours", 0.00, 48.00)
        undertimeMins = input.readBigDecimalNoDefault("[5/5] Undertime Minutes", 0.00, 2880.00)

        CREATE Employee emp(name, dailyRate, daysWorked, otHours, undertimeMins)
        CREATE Payroll payroll(emp)

        CALL PayslipPrinter.printPayslip(payroll)

        keepRunning = input.readYesNo("Compute another payslip? [y/n]: ")
    UNTIL keepRunning == false

    PRINT "Thank you for using Mocha & Latte's Cafe Payroll System!"
END MAIN


FUNCTION runSelfTest()
    PRINT "=== AUTOMATED VERIFICATION TEST (--test) ==="
    CREATE Employee testEmp("Mocha Matcha Lover", 600.00, 6.00, 6.00, 29.00)
    CREATE Payroll pr(testEmp)

    ASSERT pr.hourlyRate == 75.00
    ASSERT pr.minuteRate == 1.25
    ASSERT pr.otHourlyRate == 93.75
    ASSERT pr.basicEarnings == 3600.00
    ASSERT pr.overtimePay == 562.50
    ASSERT pr.grossEarnings == 4162.50
    ASSERT pr.sssDeduction == 145.69
    ASSERT pr.philHealthDeduction == 83.25
    ASSERT pr.pagIbigDeduction == 49.95
    ASSERT pr.undertimePenalty == 36.25
    ASSERT pr.totalDeductions == 315.14
    ASSERT pr.netEarnings == 3847.36

    PRINT "[SUCCESS] All verification assertions passed!"
END FUNCTION
