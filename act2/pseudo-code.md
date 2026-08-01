DEFINE CLASS Product
    ATTRIBUTES: code, name, price, stock

DEFINE CLASS CartItem
    ATTRIBUTES: product, quantity
    FUNCTION getLineTotal()
        RETURN product.price * quantity

DEFINE CLASS Inventory
    ATTRIBUTES: products (map of code -> Product)

    FUNCTION addProduct(code, name, price, stock)
        products[code] = new Product(code, name, price, stock)

    FUNCTION deleteProduct(code)
        IF code exists in products THEN
            REMOVE products[code]
            RETURN true
        RETURN false

    FUNCTION getProduct(code)
        RETURN products[code] IF exists ELSE null

    FUNCTION hasStock(code, qty)
        p = products[code]
        RETURN (p exists AND p.stock >= qty)

    FUNCTION reduceStock(code, qty)
        IF products[code] exists THEN
            products[code].stock -= qty

    FUNCTION displayInventory()
        PRINT header
        FOR EACH product IN products
            PRINT code, name, price, stock
        PRINT footer


DEFINE CLASS Checkout
    ATTRIBUTES: cart (list of CartItem)
    CONSTANTS: TAX_RATE = 0.12, DISCOUNT_RATE = 0.05

    FUNCTION addToCart(product, qty)
        cart.ADD(new CartItem(product, qty))

    FUNCTION getSubtotal()
        subtotal = 0
        FOR EACH item IN cart
            subtotal += item.getLineTotal()
        RETURN subtotal

    FUNCTION getTotal(isMember)
        subtotal = getSubtotal()
        discount = isMember ? subtotal * DISCOUNT_RATE : 0
        afterDiscount = subtotal - discount
        RETURN afterDiscount + (afterDiscount * TAX_RATE)

    FUNCTION printReceipt(isMember, cashPaid)
        subtotal = getSubtotal()
        discount = isMember ? subtotal * DISCOUNT_RATE : 0
        afterDiscount = subtotal - discount
        tax = afterDiscount * TAX_RATE
        total = afterDiscount + tax
        change = cashPaid - total

        PRINT receipt header
        FOR EACH item IN cart
            PRINT item.name, item.quantity, item.price, item.getLineTotal()
        PRINT subtotal, discount, tax, total, cashPaid, MAX(0, change)

        IF change < 0 THEN
            PRINT "Insufficient payment! Short by " + ABS(change)


// MAIN CLASS
BEGIN MAIN
    CREATE Inventory inventory
    inventory.addProduct("P001", "Bottled Water", 15.00, 50)
    inventory.addProduct("P002", "Instant Noodles", 18.50, 100)
    inventory.addProduct("P003", "Canned Sardines", 25.00, 40)
    inventory.addProduct("P004", "White Bread", 55.00, 30)
    inventory.addProduct("P005", "Ballpen", 8.00, 200)

    REPEAT
        PRINT menu:
            "1. View Inventory"
            "2. Add New Product"
            "3. Delete Product"
            "4. Checkout / New Transaction"
            "5. Exit"
        READ choice (validate it is a number)

        SWITCH choice:
            CASE 1: inventory.displayInventory()
            CASE 2: CALL addNewProduct(inventory)
            CASE 3: CALL deleteProduct(inventory)
            CASE 4: CALL processCheckout(inventory)
            CASE 5: PRINT "Thank you! Exiting program..."
            DEFAULT: PRINT "Invalid choice. Try again."

    UNTIL choice == 5
END MAIN

// sub classes and objects
FUNCTION addNewProduct(inventory)
    READ code FROM user
    READ name FROM user

    // Validate Price Input
    REPEAT
        READ price FROM user
        IF price IS A VALID NUMBER AND price >= 0 THEN 
            BREAK
        ELSE
            PRINT "Invalid price! Must be a non-negative number."
    END REPEAT

// Validate Quantity & Stock Loop
    REPEAT
        READ qty FROM user
        IF qty IS NOT A VALID INTEGER THEN
            PRINT "Invalid input! Please enter a whole number."
        ELSE IF qty <= 0 THEN
            PRINT "Quantity must be greater than 0."
        ELSE IF NOT inventory.hasStock(input, qty) THEN
            PRINT "Insufficient stock! Available: " + product.stock
        ELSE
            BREAK // Exit quantity loop only when input is valid and stock exists
        END REPEAT

    inventory.addProduct(code, name, price, stock)
    PRINT "Product added successfully!"


FUNCTION deleteProduct(inventory)
    READ code FROM user
    IF inventory.deleteProduct(code) THEN
        PRINT "Product deleted successfully!"
    ELSE
        PRINT "Product code not found."


FUNCTION processCheckout(inventory)
    CREATE Checkout checkout
    inventory.displayInventory()

    LOOP
        READ input (product code or "done")
        IF input == "done" THEN BREAK

        product = inventory.getProduct(input)
        IF product is null THEN
            PRINT "Product not found."
            CONTINUE

        READ qty FROM user

        IF qty <= 0 THEN
            PRINT "Quantity must be greater than 0."
            CONTINUE

        IF NOT inventory.hasStock(input, qty) THEN
            PRINT "Insufficient stock! Available: " + product.stock
            CONTINUE

        checkout.addToCart(product, qty)
        inventory.reduceStock(input, qty)
        PRINT qty + " x " + product.name + " added to cart."
    END LOOP

    IF checkout.cart IS EMPTY THEN
        PRINT "No items in cart. Transaction cancelled."
        RETURN

    READ isMember (yes/no) FROM user

    PRINT "TOTAL AMOUNT DUE: " + checkout.getTotal(isMember)
    READ cashPaid FROM user

    checkout.printReceipt(isMember, cashPaid)

