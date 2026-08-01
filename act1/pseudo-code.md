START PROGRAM

   // declaring and initializing scanner and stack objects and option placeholder
    CREATE sc AS Scanner for keyboard input
    CREATE books AS Stack of Strings
    CREATE choice AS Integer

    // input prompt and main menu
    REPEAT
        PRINT "========== Data Structures and Algorithm =========="
        PRINT "1. Array"
        PRINT "2. Stack"
        PRINT "3. Exit"
        PRINT "Enter choice: "
        choice = READ Integer FROM sc

        IF choice EQUALS 1 THEN

            // array declaration with 5 indices
            CREATE scores AS Array of Integer, size 5
            PRINT "Enter 5 student scores:"

            // input iteration using for loop
            REPEAT FOR i FROM 0 TO 4
                PRINT "Score " + (i + 1) + ": "
                scores[i] = READ Integer FROM sc
            END REPEAT

            // high score placeholder declaration and initialization
            highest = scores[0]

            PRINT "Student Scores:"

            // updates highest score using enhanced for loop and conditional statement
            FOR EACH score IN scores
                PRINT score
                IF score GREATER THAN highest THEN
                    highest = score
                END IF
            END FOR

            PRINT "Highest Score: " + highest

        ELSE IF choice EQUALS 2 THEN

            CREATE stackChoice AS Integer

            // stack prompt and main menu
            REPEAT
                PRINT "------ STACK MENU ------"
                PRINT "1. Push Book"
                PRINT "2. Pop Book"
                PRINT "3. Peek Top Book"
                PRINT "4. Display Stack"
                PRINT "5. Back"
                PRINT "Enter choice: "
                stackChoice = READ Integer FROM sc
                CLEAR leftover newline FROM sc

                IF stackChoice EQUALS 1 THEN
                    PRINT "Enter book name: "
                    book = READ String FROM sc
                    ADD book TO FRONT [TOP] OF books
                    PRINT book + " added to stack."

                ELSE IF stackChoice EQUALS 2 THEN
                    IF books IS NOT EMPTY THEN
                        removed = REMOVE FROM FRONT [TOP] OF books
                        PRINT "Removed: " + removed
                    ELSE
                        PRINT "Stack is empty."
                    END IF

                ELSE IF stackChoice EQUALS 3 THEN
                    IF books IS NOT EMPTY THEN
                        top = LOOK AT FRONT [TOP] OF books (does not remove it)
                        PRINT "Top Book: " + top
                    ELSE
                        PRINT "Stack is empty."
                    END IF

                ELSE IF stackChoice EQUALS 4 THEN
                    IF books IS NOT EMPTY THEN
                        PRINT "Current Stack: " + books
                    ELSE
                        PRINT "Stack is empty."
                    END IF

                ELSE IF stackChoice EQUALS 5 THEN
                    PRINT "Returning to Main Menu..."

                ELSE
                    PRINT "Invalid choice."
                END IF

            UNTIL stackChoice EQUALS 5

        ELSE IF choice EQUALS 3 THEN
            PRINT "Thank you for using the program!"

        ELSE
            PRINT "Invalid choice."
        END IF

    UNTIL choice EQUALS 3

    CLOSE sc

END Program 


