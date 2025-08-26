// TODO GUI
// TODO unit tests
// TODO operators array
// TODO error handling
// TODO comments
// TODO help panel for console, and GUI maybe?
package calculator;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Simple Calculator (type 'exit' to quit)");
        System.out.println("Enter an expression like 2+2, 7 - 3, 5 * 8, 10 / 2");

        while (true) {
            System.out.print("> ");
            String expression = scanner.nextLine(); // save input

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