1. Main Program Control Flow 

    1.1. Initialize an empty Stack variable (books) and an integer variable (choice).

    1.2. Display the Main Menu options: 1. Array, 2. Stack, 3. Exit.

    1.3. Read the user’s input and assign it to choice.

    1.4. Evaluate choice:

        If 1, proceed to the Array Module (Step 2).

        If 2, proceed to the Stack Module (Step 3).

        If 3, display a thank you message and terminate the program.

    1.5. Repeat back to Step 1.2 as long as choice is not equal to 3.

2. Module 1: Sequential Array Processing

    2.1. Create an integer array named scores with a fixed size of 5.

    2.2. Loop 5 times to read user inputs and store them sequentially into scores[0] through scores[4].

    2.3. Initialize a variable highest and assign it the value of the first score (scores[0]).

    2.4. Loop through each score in the array:

        Print the current score.

        Compare: IF score > highest, THEN update highest with the current score.

    2.5. Display the final calculated highest score, exit the module, and return to the main menu.

3. Module 2: Dynamic Stack Management

    3.1. Initialize an integer variable (stackChoice).

    3.2. Display the Stack Sub-Menu options: 1. Add, 2. Remove, 3. View Top, 4. Show All, 5. Back.

    3.3. Read the user's input and assign it to stackChoice.

    3.4. Evaluate stackChoice:

        Case 1 (Push): Input a string variable (book), add (push) it to the top of the stack, and display a confirmation message.

        Case 2 (Pop): Check if the stack is empty. If not empty, remove and display the top element (pop). Otherwise, display an "empty" warning.

        Case 3 (Peek): Check if the stack is empty. If not empty, display the top element (peek) without removing it. Otherwise, display an "empty" warning.

        Case 4 (Display): Check if the stack is empty. If not empty, print the entire contents of the stack. Otherwise, display an "empty" warning.

        Case 5 (Back): Display a return message and exit this sub-menu loop.

    3.5. Repeat back to Step 3.2 as long as stackChoice is not equal to 5. 


