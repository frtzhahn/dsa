import java.util.Scanner; 
import java.util.Stack;   

public class act1 {

    public static void main(String[] args) {

			// stack and scanner declaration
        Scanner sc = new Scanner(System.in);
        Stack<String> books = new Stack<>();

				// option input variable
        int choice;

        do {
					// main menu prompt
            System.out.println("\n========== Data Structures and Algorithm ==========");
            System.out.println("1. Array ");
            System.out.println("2. Stack ");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

						// stores option input
            choice = sc.nextInt();

						// executes selected option
            switch (choice) {

                case 1:

                    // Creates an integer array that can store 5 student scores
                    int[] scores = new int[5];

                    System.out.println("\nEnter 5 student scores:");

                    // Loop to input the scores into the array
                    for (int i = 0; i < scores.length; i++) {
                        System.out.print("Score " + (i + 1) + ": ");
                        scores[i] = sc.nextInt();
                    }

                    // Assumes the first score is the highest initially
                    int highest = scores[0];

                    System.out.println("\nStudent Scores:");

                    // Enhanced for loop to display all scores
                    // and find the highest score
                    for (int score : scores) {
                        System.out.println(score);

                        if (score > highest) {
                            highest = score;
                        }
                    }

                    // Displays the highest score
                    System.out.println("Highest Score: " + highest);

                    break;

                case 2:

                    // Stores the user's Stack menu choice
                    int stackChoice;

                    // Stack menu loop
                    do {

                        // Displays the Stack menu
                        System.out.println("\n------ STACK MENU ------");
                        System.out.println("1. Push Book");
                        System.out.println("2. Pop Book");
                        System.out.println("3. Peek Top Book");
                        System.out.println("4. Display Stack");
                        System.out.println("5. Back");
                        System.out.print("Enter choice: ");

                        // Reads the user's stack menu choice
                        stackChoice = sc.nextInt();

                        // Removes the leftover newline character
                        sc.nextLine();

                        // Executes the selected stack operation
                        switch (stackChoice) {

                            case 1:
                                // Gets the book name from the user
                                System.out.print("Enter book name: ");
                                String book = sc.nextLine();

                                // Adds the book to the top of the stack
                                books.push(book);

                                System.out.println(book + " added to stack.");
                                break;

                            case 2:
                                // Removes the top book if the stack is not empty
                                if (!books.isEmpty()) {
                                    System.out.println("Removed: " + books.pop());
                                } else {
                                    System.out.println("Stack is empty.");
                                }
                                break;

                            case 3:
                                // Displays the top book without removing it
                                if (!books.isEmpty()) {
                                    System.out.println("Top Book: " + books.peek());
                                } else {
                                    System.out.println("Stack is empty.");
                                }
                                break;

                            case 4:
                                // Displays all books currently in the stack
                                if (!books.isEmpty()) {
                                    System.out.println("Current Stack: " + books);
                                } else {
                                    System.out.println("Stack is empty.");
                                }
                                break;

                            case 5:
                                // Returns to the main menu
                                System.out.println("Returning to Main Menu...");
                                break;

                            default:
                                // Displays an error for an invalid menu choice
                                System.out.println("Invalid choice.");

                        }

                    } while (stackChoice != 5); // Repeats until Back is selected

                    break;

                case 3:
                    // Exits the program
                    System.out.println("Thank you for using the program!");
                    break;

                default:
                    // Displays an error for an invalid main menu choice
                    System.out.println("Invalid choice.");

            }

        } while (choice != 3); // Repeats the main menu until Exit is selected

				// closes scanner variable
        sc.close();
    }
}
