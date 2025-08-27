package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalculatorFrame extends JFrame {

    private final JTextField inputField = new JTextField();
    private final JButton evaluateButton = new JButton("Evaluate");
    private final JLabel outputLabel = new JLabel("Result: ");
    private final Calculator calculator = new Calculator();

    public CalculatorFrame() {
        super("Simple Calculator");

        // Layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 420);
        setLocationRelativeTo(null); // center on screen

        // Gaps between components
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        // Padding of the panel
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel top = new JPanel(new BorderLayout(16, 16));
        top.add(new JLabel("Expression:"), BorderLayout.WEST);
        top.add(inputField, BorderLayout.CENTER);
        top.add(evaluateButton, BorderLayout.EAST);

        JPanel bottom = new JPanel(new BorderLayout(16, 16));
        bottom.add(outputLabel, BorderLayout.WEST);

        panel.add(top, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.CENTER);

        setContentPane(panel);

        // Actions
        evaluateButton.addActionListener(this::onEvaluate);
        inputField.addActionListener(this::onEvaluate); // press Enter to evaluate

    }

    private void onEvaluate(ActionEvent e) {
        String expression = inputField.getText();
        try {
            double value = calculator.evaluate(expression);
            outputLabel.setText("Result: " + value);
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
