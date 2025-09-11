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
        setSize(280, 410);
        // Center on screen
        setLocationRelativeTo(null);

        // Content panel
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        // Padding of the content panel
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Top panel
        JPanel top = new JPanel(new BorderLayout(8, 8));

        // Buttons panel
        JPanel buttons = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        ////////////////////////////////////////////////////////////////////////

        // Create

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
        // Trigonometry button has longer text, we need to give it more space.
        trigonometryButton.setMargin(new Insets(4, 0, 4, 0));
        JPopupMenu trigonometryMenu = new JPopupMenu();
        JButton sinButton = new JButton("sin");
        JButton cosButton = new JButton("cos");
        JButton tanButton = new JButton("tan");

        // Set up the trigonometry popup menu and actions
        trigonometryMenu.setPreferredSize(new Dimension(60, 100));
        trigonometryMenu.add(sinButton);
        trigonometryMenu.add(cosButton);
        trigonometryMenu.add(tanButton);

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

        ////////////////////////////////////////////////////////////////////////

        // Design

        // Design settings for the application.
        Dimension buttonSize = new Dimension(40, 30);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 12);
        inputOutputField.setFont(buttonFont);
        inputOutputField.setHorizontalAlignment(JTextField.RIGHT);

        // Style buttons uniformly.
        for (JButton button : new JButton[]{
                clearButton, backspaceButton, squareRootButton,
                powerButton, customPowerButton, logButton, piButton,
                leftBracketButton, rightBracketButton, divisionButton,
                multiplicationButton, subtractionButton, additionButton,
                number1Button, number2Button,
                number3Button, number4Button, number5Button,
                number6Button, number7Button, number8Button,
                number9Button, dotButton,
                equalsButton, number0Button
        }) {
            button.setPreferredSize(buttonSize);
            button.setFont(buttonFont);
        }

        // Style trigonometry menu buttons uniformly.
        for (JButton b : new JButton[]{sinButton, cosButton, tanButton}) {
            b.setMaximumSize(new Dimension(60, 60));
            b.setFont(buttonFont);
            b.setMargin(new Insets(4, 0, 4, 4));
            b.setHorizontalAlignment(SwingConstants.CENTER);
        }

        ////////////////////////////////////////////////////////////////////////

        // Grid

        top.add(new JLabel("Expression:"), BorderLayout.WEST);
        top.add(inputOutputField, BorderLayout.CENTER);


        gbc.gridy = 0;
        gbc.gridx = 0; gbc.gridwidth = 1; buttons.add(trigonometryButton, gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; buttons.add(piButton, gbc);
        gbc.gridx = 2; gbc.gridwidth = 1; buttons.add(clearButton, gbc);
        gbc.gridx = 3; gbc.gridwidth = 1; buttons.add(backspaceButton, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0; gbc.gridwidth = 1; buttons.add(powerButton, gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; buttons.add(customPowerButton, gbc);
        gbc.gridx = 2; gbc.gridwidth = 1; buttons.add(squareRootButton, gbc);
        gbc.gridx = 3; gbc.gridwidth = 1; buttons.add(divisionButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0; gbc.gridwidth = 1; buttons.add(logButton, gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; buttons.add(leftBracketButton, gbc);
        gbc.gridx = 2; gbc.gridwidth = 1; buttons.add(rightBracketButton, gbc);
        gbc.gridx = 3; gbc.gridwidth = 1; buttons.add(multiplicationButton, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0; gbc.gridwidth = 1; buttons.add(number7Button, gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; buttons.add(number8Button, gbc);
        gbc.gridx = 2; gbc.gridwidth = 1; buttons.add(number9Button, gbc);
        gbc.gridx = 3; gbc.gridwidth = 1; buttons.add(subtractionButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0; gbc.gridwidth = 1; buttons.add(number4Button, gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; buttons.add(number5Button, gbc);
        gbc.gridx = 2; gbc.gridwidth = 1; buttons.add(number6Button, gbc);
        gbc.gridx = 3; gbc.gridwidth = 1; buttons.add(additionButton, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0; gbc.gridwidth = 1; buttons.add(number1Button, gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; buttons.add(number2Button, gbc);
        gbc.gridx = 2; gbc.gridwidth = 1; buttons.add(number3Button, gbc);
        gbc.gridx = 3; gbc.gridwidth = 1; buttons.add(dotButton, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0; gbc.gridwidth = 2; buttons.add(number0Button, gbc);
        gbc.gridx = 2; gbc.gridwidth = 2; buttons.add(equalsButton, gbc);

        // Add components to the content panel
        panel.add(top, BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.CENTER);

        setContentPane(panel);

        ////////////////////////////////////////////////////////////////////////

        // Actions

        // Show the popup just below the Trig button
        trigonometryButton.addActionListener(ev -> {
            trigonometryMenu.show(trigonometryButton, 0, trigonometryButton.getHeight());
        });
        piButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "3.1415926"));
        clearButton.addActionListener(ev -> inputOutputField.setText(""));
        // Backspace functionality.
        backspaceButton.addActionListener(ev -> {
            String text = inputOutputField.getText();
            if (!text.isEmpty()) {
                inputOutputField.setText(text.substring(0, text.length() - 1));
            }
        });

        powerButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "^2"));
        customPowerButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "^"));
        squareRootButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "sqrt"));
        divisionButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "/"));

        logButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "log"));
        leftBracketButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "("));
        rightBracketButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + ")"));
        multiplicationButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "*"));

        number7Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "7"));
        number8Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "8"));
        number9Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "9"));
        subtractionButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "-"));

        number4Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "4"));
        number5Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "5"));
        number6Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "6"));
        additionButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "+"));

        number1Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "1"));
        number2Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "2"));
        number3Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "3"));
        dotButton.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "."));

        number0Button.addActionListener(ev -> inputOutputField.setText(inputOutputField.getText() + "0"));
        // Press the symbol or Enter to evaluate.
        equalsButton.addActionListener(this::onEvaluate);
        inputOutputField.addActionListener(this::onEvaluate);
        
    }

    ////////////////////////////////////////////////////////////////////////

    // Functions

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
