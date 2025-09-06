package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalculatorFrame extends JFrame {

    private final Calculator calculator = new Calculator();
    private final JTextField inputOutputField = new JTextField();

    /**
     * Creates a new CalculatorFrame.
     */
    public CalculatorFrame() {
        super("Simple Calculator");

        // Layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 420);
        // Center on screen
        setLocationRelativeTo(null);

        // Content panel
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        // Padding of the content panel
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Top panel
        JPanel top = new JPanel(new BorderLayout(8, 8));

        // Buttons panel
        JPanel buttons = new JPanel(new GridLayout(7, 3, 4, 4));

        // Last row panel
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 4, 4));

        // Design settings for buttons
        Dimension buttonSize = new Dimension(40, 30);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 12);

        ////////////////////////////////////////////////////////////////////////

        // Create buttons
        JButton clearButton = new JButton("CE");
        JButton backspaceButton = new JButton("⌫");
        JButton squareRootButton = new JButton("√");
        JButton powerButton = new JButton("x²");
        JButton customPowerButton = new JButton("xʸ");
        JButton logButton = new JButton("log");
        JButton piButton = new JButton("π");
        JButton leftBracketButton = new JButton("(");
        JButton rightBracketButton = new JButton(")");
        JButton divisionButton = new JButton("/");
        JButton multiplicationButton = new JButton("*");
        JButton subtractionButton = new JButton("-");
        JButton additionButton = new JButton("+");

        JButton trigonometryButton = new JButton("Trig ▾");
        JPopupMenu trigonometryMenu = new JPopupMenu();
        JButton sinButton = new JButton("sin");
        JButton cosButton = new JButton("cos");
        JButton tanButton = new JButton("tan");

        // Set up the trigonometry popup menu and actions
        trigonometryMenu.setPreferredSize(new Dimension(60, 100));
        trigonometryMenu.add(sinButton);
        trigonometryMenu.add(cosButton);
        trigonometryMenu.add(tanButton);
        // Style trigonometry menu buttons uniformly
        for (JButton b : new JButton[]{sinButton, cosButton, tanButton}) {
            b.setMaximumSize(new Dimension(60, 60));
            b.setFont(buttonFont);
            b.setMargin(new Insets(4, 0, 4, 4));
            b.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JButton number0Button = new JButton("0");
        JButton number1Button = new JButton("1");
        JButton number2Button = new JButton("2");
        JButton number3Button = new JButton("3");
        JButton number4Button = new JButton("4");
        JButton number5Button = new JButton("5");
        JButton number6Button = new JButton("6");
        JButton number7Button = new JButton("7");
        JButton number8Button = new JButton("8");
        JButton number9Button = new JButton("9");
        JButton dotButton = new JButton(".");
        JButton equalsButton = new JButton("=");

        // Style buttons uniformly
        for (JButton button : new JButton[]{
                clearButton, backspaceButton, squareRootButton,
                powerButton, customPowerButton, logButton, piButton,
                leftBracketButton, rightBracketButton, divisionButton,
                multiplicationButton, subtractionButton, additionButton,
                number1Button, number2Button,
                number3Button, number4Button, number5Button,
                number6Button, number7Button, number8Button,
                number9Button, dotButton
        }) {
            button.setPreferredSize(buttonSize);
            button.setFont(buttonFont);
        }

        ////////////////////////////////////////////////////////////////////////

        top.add(new JLabel("Expression:"), BorderLayout.WEST);
        top.add(inputOutputField, BorderLayout.CENTER);

        buttons.add(trigonometryButton);
        buttons.add(piButton);
        buttons.add(clearButton);
        buttons.add(backspaceButton);

        buttons.add(powerButton);
        buttons.add(customPowerButton);
        buttons.add(squareRootButton);
        buttons.add(divisionButton);

        buttons.add(logButton);
        buttons.add(leftBracketButton);
        buttons.add(rightBracketButton);
        buttons.add(multiplicationButton);

        buttons.add(number7Button);
        buttons.add(number8Button);
        buttons.add(number9Button);
        buttons.add(subtractionButton);

        buttons.add(number4Button);
        buttons.add(number5Button);
        buttons.add(number6Button);
        buttons.add(additionButton);

        buttons.add(number1Button);
        buttons.add(number2Button);
        buttons.add(number3Button);
        buttons.add(dotButton);

        bottomRow.add(number0Button);
        bottomRow.add(equalsButton);

        // Add components to the content panel
        panel.add(top, BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.CENTER);
        panel.add(bottomRow, BorderLayout.SOUTH);

        setContentPane(panel);

        ////////////////////////////////////////////////////////////////////////

        // Actions
        equalsButton.addActionListener(this::onEvaluate);
        // Press Enter to evaluate
        inputOutputField.addActionListener(this::onEvaluate);
        // Show the popup just below the Trig button
        trigonometryButton.addActionListener(ev -> {
            trigonometryMenu.show(trigonometryButton, 0, trigonometryButton.getHeight());
        });
        
    }

    /**
     * Evaluates the expression in the input field and updates the output field.
     */
    private void onEvaluate(ActionEvent e) {
        String expression = inputOutputField.getText();
        try {
            double value = calculator.evaluate(expression);
            inputOutputField.setText(String.valueOf(value));
        } catch (ArithmeticException | IllegalArgumentException ex) {
            // Show error dialog and keep, previous result
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


}
