package calculator;

public class Calculator {

    public double add(double a, double b) {
        return a + b;
    }

    public double sub(double a, double b) {
        return a - b;
    }

    public double mul(double a, double b) {
        return a * b;
    }

    public double div(double a, double b) {
        if (b == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    public double sqrt(double a) {
        return Math.sqrt(a);
    }

    public double pow(double a) {
        return Math.pow(a, 2);
    }

    public double pown(double a, double b) {
        return Math.pow(a, b);
    }

    public double evaluate(String expression) {

        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Empty expression");
        }

        String trimmedExpression = expression
                .replaceAll("([+\\-*/^])", " $1 ")
                .replaceAll("(?i)\\b(pown)\\b", " pown ")
                .replaceAll("(?i)\\b(sqrt)\\b", " sqrt ")
                .replaceAll("\\s+", " ")
                .trim();

        String[] parts = trimmedExpression.split(" ");
        try {
            if (parts.length == 2) {
                if ("sqrt".equalsIgnoreCase(parts[0])) {
                    double a = Double.parseDouble(parts[1]);
                    return sqrt(a);
                }
                if ("^".equals(parts[1])) {
                    double a = Double.parseDouble(parts[0]);
                    return pow(a);
                }
                throw new IllegalArgumentException("Unsupported unary expression");

            }
            else if (parts.length == 3) {
                double a = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double b = Double.parseDouble(parts[2]);


            return switch (operator) {
                case "+" -> add(a,b);
                case "-" -> sub(a,b);
                case "*" -> mul(a,b);
                case "/" -> div(a,b);
                case "^" -> pow(a);
                case "sqrt" -> sqrt(a);
                case "pown" -> pown(a,b);
                default -> throw new IllegalArgumentException("Unsupported operator:\n " + operator);
            };
            } else {
                throw new IllegalArgumentException("Invalid expression format");
            }
        }
        catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid number format", nfe);
        }


    }

}

        // try to parse the expressions, short it
//        String spacedExpression = expression.replaceAll("([+\\-*/^])", " $1 ");
//        String spacedExpression2 = spacedExpression.replaceAll("pown", " pown ");
//        String spacedExpression3 = spacedExpression2.replaceAll("sqrt", " sqrt ");
//        String trimmedExpression = spacedExpression3.replaceAll(" +", " ").trim();
//        double a = Double.parseDouble(trimmedExpression.substring(0, trimmedExpression.indexOf(' ')));
//        String operator = trimmedExpression.substring(trimmedExpression.indexOf(' ') + 1, trimmedExpression.lastIndexOf(' '));
//        double b = Double.parseDouble(trimmedExpression.substring(trimmedExpression.lastIndexOf(' ')));
