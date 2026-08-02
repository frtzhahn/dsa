# Problem
Create an interactive, educational console program to compute weekly net pay for daily-wage employees, incorporating overtime premiums, undertime penalties, statutory deductions, and robust input validation.

# Solution
The program computes weekly net pay for daily-wage employees at Mocha & Latte's Cafe. It prompts for employee name (default: "Mocha Matcha Lover"), daily rate, days worked, overtime hours, and undertime minutes with input validation to prevent crashes. Using BigDecimal precision, it computes basic earnings, overtime pay (125%), statutory deductions (SSS 3.5%, PhilHealth 2.0%, Pag-IBIG 1.2%), and undertime penalties. It outputs an ANSI-colored itemized payslip receipt and includes an automated --test verification flag.
