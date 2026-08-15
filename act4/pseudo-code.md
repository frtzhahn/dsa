// DATA.JAVA PSEUDO CODE

CLASS Data IMPLEMENTS Serializable:
    // table categories
    PRIVATE id AS String
    PRIVATE name AS String
    PRIVATE quantity AS Integer
    PRIVATE price AS Real

    // constructor for user inputs
    CONSTRUCTOR(id, name, quantity, price):
        SET this.id = id
        SET this.name = name
        SET this.quantity = quantity
        SET this.price = price

    // getters/setters
    FUNCTION getId(): RETURN id
    FUNCTION setId(id): SET this.id = id
    FUNCTION getName(): RETURN name
    FUNCTION setName(name): SET this.name = name
    FUNCTION getQuantity(): RETURN quantity
    FUNCTION setQuantity(quantity): SET this.quantity = quantity
    FUNCTION getPrice(): RETURN price
    FUNCTION setPrice(price): SET this.price = price

    // table row layout
    FUNCTION toTableRow() RETURNS String:
        RETURN Formatted("| %-7s | %-30s | %-8d | %-10.2f |", id, name, quantity, price)

    // update row display layout
    FUNCTION toString() RETURNS String:
        RETURN Formatted("[ID: %s | NAME: %s | QUANTITY: %d | PRICE: ₱%.2f]", id, name, quantity, price)
END CLASS


// RECORD.JAVA PSEUDO CODE

CLASS Record:
    CONSTANT FILE_NAME = "database.ser"
    PRIVATE records AS List OF Data

    // ansi color codes
    CONSTANT RESET, RED, GREEN, CYAN

    // loads existing data
    CONSTRUCTOR():
        SET records = loadFromFile()

    // add new record function
    FUNCTION addRecord(record AS Data):
        APPEND record TO records
        PRINT GREEN + "\n[SUCCESS] Record added successfully" + RESET

    // table display function
    FUNCTION viewAllRecords():
        IF records IS EMPTY THEN
            PRINT RED + "\n[ERROR] No records found database is empty" + RESET
            RETURN
        END IF

        PRINT CYAN + ASCII_TABLE_HEADER_BANNER + RESET

        SET tableWidth = 68
        SET doubleBorder = CYAN + REPEAT("=", tableWidth) + RESET
        SET singleBorder = CYAN + REPEAT("-", tableWidth) + RESET

        PRINT doubleBorder
        PRINT CYAN + Formatted("| %-7s | %-30s | %-8s | %-10s |", "ID", "NAME", "QUANTITY", "PRICE (₱)") + RESET
        PRINT singleBorder
        FOR EACH r IN records DO
            PRINT r.toTableRow()
        END FOR
        PRINT doubleBorder

    // update function
    FUNCTION updateRecord(id, newName, newQty, newPrice) RETURNS Boolean:
        SET record = findById(id)
        IF record IS NOT NULL THEN
            record.setName(newName)
            record.setQuantity(newQty)
            record.setPrice(newPrice)
            RETURN TRUE
        END IF
        RETURN FALSE

    // delete function
    FUNCTION deleteRecord(id) RETURNS Boolean:
        SET record = findById(id)
        IF record IS NOT NULL THEN
            REMOVE record FROM records
            RETURN TRUE
        END IF
        RETURN FALSE

    // record searcher
    FUNCTION findById(id) RETURNS Data:
        FOR EACH r IN records DO
            IF r.getId() EQUALS_IGNORE_CASE(id) THEN
                RETURN r
            END IF
        END FOR
        RETURN NULL

    // saves array list into database file
    FUNCTION saveToFile():
        TRY:
            OPEN ObjectOutputStream WRITING TO FILE_NAME
            WRITE records TO stream
            PRINT GREEN + "[SUCCESS] Data permanently saved to '" + FILE_NAME + RESET
        CATCH IOException e:
            PRINT RED + "[ERROR] Failed to save data to disk: " + e.getMessage() + RESET
        END TRY

    // fetches file data back to program on runtime
    FUNCTION loadFromFile() RETURNS List OF Data:
        IF file FILE_NAME DOES NOT EXIST THEN
            RETURN NEW Empty List
        END IF
        TRY:
            OPEN ObjectInputStream READING FROM FILE_NAME
            RETURN READ Object AS List OF Data
        CATCH IOException OR ClassNotFoundException:
            PRINT RED + "[ERROR] Data file corrupted or unreadable" + RESET
            RETURN NEW Empty List
        END TRY
END CLASS


// MAIN.JAVA PSEUDO CODE

CLASS Main:
    STATIC scanner AS Scanner
    STATIC manager AS Record

    // ansi color codes
    STATIC RESET, RED, GREEN, YELLOW, CYAN

    FUNCTION main():
        CALL clearScreen()
        SET running = TRUE

        WHILE running IS TRUE DO
            CALL printMenu()
            SET choice = getValidInteger(YELLOW + "Choose an option (1-5): " + RESET, 1, 5)

            SWITCH choice:
                CASE 1:
                    PRINT "\n\n"
                    CALL manager.viewAllRecords()
                    PRINT "\n\n"
                CASE 2:
                    CALL handleCreate()
                CASE 3:
                    CALL handleUpdate()
                CASE 4:
                    CALL handleDelete()
                CASE 5:
                    CALL manager.saveToFile()
                    SET running = FALSE
                DEFAULT:
                    PRINT RED + "\n[ERROR] Invalid menu choice" + RESET
            END SWITCH
        END WHILE
    END FUNCTION

    // clear terminal
    FUNCTION clearScreen():
        TRY:
            IF Operating System IS Windows THEN
                EXECUTE "cmd /c cls"
            ELSE
                EXECUTE "clear"
            END IF
        CATCH Exception:
            PRINT ANSI escape sequence "\033[H\033[2J"
            FLUSH output stream
        END TRY
    END FUNCTION

    // main menu
    FUNCTION printMenu():
        PRINT CYAN + ASCII_MAIN_MENU_BANNER + RESET
        PRINT "1. View All Records"
        PRINT "2. Add New Record"
        PRINT "3. Update a Record"
        PRINT "4. Delete a Record"
        PRINT "5. Save & Exit"
    END FUNCTION

    // add record
    FUNCTION handleCreate():
        PRINT "\n--- ADD RECORD ---"

        WHILE TRUE DO
            PROMPT YELLOW + "Enter Unique ID (max 7 characters): " + RESET
            SET id = READ trimmed string
            IF id IS EMPTY THEN
                PRINT RED + "[ERROR] ID cannot be empty" + RESET
            ELSE IF LENGTH(id) > 7 THEN
                PRINT RED + "[ERROR] ID exceeds maximum limit of 7 characters" + RESET
            ELSE IF manager.findById(id) IS NOT NULL THEN
                PRINT RED + "[ERROR] That ID already exists" + RESET
            ELSE
                BREAK LOOP
            END IF
        END WHILE

        WHILE TRUE DO
            PROMPT YELLOW + "Enter Item Name (max 30 characters, press Enter to default): " + RESET
            SET name = READ trimmed string
            IF name IS EMPTY THEN
                SET name = "Unnamed Item"
                BREAK LOOP
            ELSE IF LENGTH(name) > 30 THEN
                PRINT RED + "[ERROR] Item name cannot exceed 30 characters" + RESET
            ELSE
                BREAK LOOP
            END IF
        END WHILE

        SET quantity = getValidInteger(YELLOW + "Enter Quantity (0 - 99999): " + RESET, 0, 99999)
        SET price = getValidDouble(YELLOW + "Enter Price (₱0.00 - ₱9999999.99): ₱" + RESET, 0.0, 9999999.99)

        SET newRecord = NEW Data(id, name, quantity, price)
        CALL manager.addRecord(newRecord)
    END FUNCTION

    // update record
    FUNCTION handleUpdate():
        PRINT "\n--- UPDATE RECORD ---"
        PROMPT YELLOW + "Enter the ID of the record to update: " + RESET
        SET id = READ trimmed string

        SET existing = manager.findById(id)
        IF existing IS NULL THEN
            PRINT RED + "[ERROR] Record with ID '" + id + "' not found" + RESET
            RETURN
        END IF

        PRINT "\nCurrent details: " + existing.toString()

        WHILE TRUE DO
            PROMPT YELLOW + "Enter New Name (max 30 chars, press Enter to keep current): " + RESET
            SET newName = READ trimmed string
            IF newName IS EMPTY THEN
                SET newName = existing.getName()
                BREAK LOOP
            ELSE IF LENGTH(newName) > 30 THEN
                PRINT RED + "[ERROR] Item name cannot exceed 30 characters" + RESET
            ELSE
                BREAK LOOP
            END IF
        END WHILE

        SET newQty = getValidInteger(YELLOW + "Enter New Quantity (0 - 99999): " + RESET, 0, 99999)
        SET newPrice = getValidDouble(YELLOW + "Enter New Price (₱0.00 - ₱9999999.99): ₱" + RESET, 0.0, 9999999.99)

        SET success = manager.updateRecord(id, newName, newQty, newPrice)
        IF success IS TRUE THEN
            PRINT GREEN + "\n[SUCCESS] Record updated in RAM Changes will save permanently on exit" + RESET
        END IF
    END FUNCTION

    // delete function
    FUNCTION handleDelete():
        PRINT "\n--- DELETE A RECORD ---"
        PROMPT YELLOW + "Enter the ID of the record to delete: " + RESET
        SET id = READ trimmed string

        SET success = manager.deleteRecord(id)
        IF success IS TRUE THEN
            PRINT GREEN + "\n[SUCCESS] Record deleted from RAM Changes will save permanently on exit." + RESET
        ELSE
            PRINT RED + "[ERROR] Record with ID '" + id + "' not found" + RESET
        END IF
    END FUNCTION

    // option input exeception handling
    FUNCTION getValidInteger(prompt, min, max) RETURNS Integer:
        WHILE TRUE DO
            PROMPT prompt
            SET input = READ trimmed string
            TRY:
                SET value = PARSE input TO Integer
                IF value < min OR value > max THEN
                    PRINT RED + Formatted("[ERROR] Value must be between %d and %d", min, max) + RESET
                    CONTINUE LOOP
                END IF
                RETURN value
            CATCH NumberFormatException:
                PRINT RED + "[ERROR] Invalid input enter a valid whole number" + RESET
            END TRY
        END WHILE
    END FUNCTION

    // exception handling for double inputs
    FUNCTION getValidDouble(prompt, min, max) RETURNS Real:
        WHILE TRUE DO
            PROMPT prompt
            SET input = READ trimmed string
            TRY:
                SET value = PARSE input TO Real
                IF value < min OR value > max THEN
                    PRINT RED + Formatted("[ERROR] Value must be between ₱%.2f and ₱%.2f", min, max) + RESET
                    CONTINUE LOOP
                END IF
                RETURN value
            CATCH NumberFormatException:
                PRINT RED + "[ERROR] Invalid input enter a valid decimal number" + RESET
            END TRY
        END WHILE
    END FUNCTION
END CLASS
