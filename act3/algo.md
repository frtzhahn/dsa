# Algorithm

1. **Start**
2. **Check Execution Mode**:
* 2.1. If command-line arguments contain `--test`, execute automated verification test suite and terminate.
* 2.2. Otherwise, proceed to interactive CLI execution.


3. **Display Main Launch Banner** ("PAYROLL" in ANSI Shadow ASCII art).
4. **Begin Interactive Payroll Loop**:
* 4.1. **Input Employee Name**: Read string input (Default to `"Mocha Matcha Lover"` if empty).
* 4.2. **Input Daily Wage Rate ($\text{₱}$)**: Validate input is numeric and $\ge 1.00$. Re-prompt on invalid entry.
* 4.3. **Input Days Worked**: Validate input is numeric and within range $0.00$ to $7.00$. Re-prompt on invalid entry.
* 4.4. **Input Overtime Hours**: Validate input is numeric and within range $0.00$ to $48.00$. Re-prompt on invalid entry.
* 4.5. **Input Undertime Minutes**: Validate input is numeric and within range $0.00$ to $2880.00$. Re-prompt on invalid entry.


5. **Compute Rates and Earnings**:
* 5.1. $\text{Hourly Rate} = \frac{\text{Daily Rate}}{8}$
* 5.2. $\text{Minute Rate} = \frac{\text{Hourly Rate}}{60}$
* 5.3. $\text{Overtime Hourly Rate} = \text{Hourly Rate} \times 1.25$
* 5.4. $\text{Basic Earnings} = \text{Days Worked} \times \text{Daily Rate}$
* 5.5. $\text{Overtime Pay} = \text{Overtime Hours} \times \text{Overtime Hourly Rate}$
* 5.6. $\text{Gross Earnings} = \text{Basic Earnings} + \text{Overtime Pay}$


6. **Compute Deductions**:
* 6.1. $\text{SSS Deduction} = \text{Gross Earnings} \times 0.035$ ($3.5\%$)
* 6.2. $\text{PhilHealth Deduction} = \text{Gross Earnings} \times 0.020$ ($2.0\%$)
* 6.3. $\text{Pag-IBIG Deduction} = \text{Gross Earnings} \times 0.012$ ($1.2\%$)
* 6.4. $\text{Undertime Penalty} = \text{Undertime Minutes} \times \text{Minute Rate}$
* 6.5. $\text{Total Deductions} = \text{SSS} + \text{PhilHealth} + \text{Pag-IBIG} + \text{Undertime Penalty}$


7. **Compute Net Earnings**:
* 7.1. $\text{Net Earnings} = \text{Gross Earnings} - \text{Total Deductions}$


8. **Display Official Payslip**:
* 8.1. Print ANSI Shadow ASCII header ("PAYSLIP").
* 8.2. Render receipt showing itemized Gross Earnings (Sub-total 1), separate Statutory Deductions, Undertime Penalty (Sub-total 2), and final Net Earnings.


9. **Prompt Re-run Option**:
* 9.1. Ask `"Compute another payslip? [y/n]"`.
* 9.2. If `y` or `yes`, restart loop from step 4.
* 9.3. If `n` or `no`, display exit message and terminate program.


10. **End**
