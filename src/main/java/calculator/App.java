// TODO GUI                                         OK
// TODO unit tests                                  OK
// TODO error handling                              OK
// TODO operators array
// TODO comments                                    OK
// TODO help panel for console, and GUI maybe?
// TODO brackets and long equation support
// TODO negative numbers support

// TODO try to understand the GUI and tests and add negative and brackets support
// TODO Especially the GUI and try move the GuiApp file to App, UNDERSATND TAHT!!!!!    OK
package calculator;

import java.util.Scanner;

/**
 * The App class is the entry point of a simple calculator application.
 * It provides a console-based interactive interface for users to input
 * mathematical expressions and receive the computed results. The application
 * uses the Calculator class to evaluate expressions and handle basic mathematical
 * operations.
 */
public class App {
    /**
     * The main method serves as the entry point of the application. It provides a console-based
     * interactive interface for users to input mathematical expressions and receive calculated results.
     * The method uses an instance of the Calculator class to evaluate expressions entered by the user.
     * The user can type 'exit' to terminate the application.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Switch between GUI and console modes
        if (args != null && args.length > 0 && "--gui".equalsIgnoreCase(args[0]))
         {
            javax.swing.SwingUtilities.invokeLater(() -> {
                 CalculatorFrame frame = new CalculatorFrame();
                 frame.setVisible(true);
             });
            return;
        }

        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Simple Calculator (type 'exit' to quit)");
        System.out.println("Enter an expression like 2+2, 7 - 3, 5 * 8, 10 / 2");

        while (true) {
            System.out.print("> ");
            // Save input
            String expression = scanner.nextLine();

            if (expression == null || expression.isBlank() || expression.equals(" ") || "exit".equals(expression.trim())) {
                System.out.println("Bye!");
                break;
            }

            try {
                double result = calculator.evaluate(expression);
                System.out.println(result);
            }
            catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Error:\n " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Unexpected error:\n " + e.getMessage());
            }



        }
    }
}