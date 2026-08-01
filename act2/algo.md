1. Start
2. Initialize inventory with 5 default products
3. Display Main Menu
3.1. View Inventory
3.2. Add New Product
3.3. Delete Product
3.4. Checkout
3.5. Exit
4. View Inventory
4.1. Display list of all products (code, name, price, stock)
5. Add New Product
   5.1. Input product code and product name
   5.2. Input price
        5.2.1. Validate that price is a non-negative number
        5.2.2. Prompt again if input contains invalid characters or is negative
   5.3. Input stock quantity
        5.3.1. Validate that stock is a non-negative whole number
        5.3.2. Prompt again if input contains invalid characters or is negative
   5.4. Save product to inventory
6. Delete Product
6.1. Input product code to delete
6.2. If code exists in inventory → remove product, show success message
6.3. Else → show "Product code not found"
7. Checkout 
   7.1. Display inventory list
   7.2. Input product code (or 'done' to finish)
   7.3. Check if product exists in inventory
   7.4. Input quantity:
        7.4.1. Validate input is an integer
        7.4.2. Check if quantity is greater than 0
        7.4.3. Check if stock is sufficient for the requested quantity
        7.4.4. Prompt again for quantity if non-numeric, <= 0, or exceeds stock
   7.5. Add item to cart and reduce stock from inventory
   7.6. Repeat steps 7.2 to 7.5 until user inputs "done"
   7.7. Check member status (validate yes/no)
   7.8. Calculate total amount due
   7.9. Input cash paid (validate non-negative double)
   7.10. Generate and print receipt with change calculation
8. Exit
