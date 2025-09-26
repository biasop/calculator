import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controller {
    @FXML
    private TextField displayField;

    private double number1 = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    @FXML
    void handleClear(ActionEvent event) {
        clearAll();
    }

    private void clearAll() {
        displayField.setText("0");
        number1 = 0;
        operator = "";
        startNewNumber = true;
    }

    @FXML
    void handleClearEntry(ActionEvent event) {
        displayField.setText("0");
        startNewNumber = true;
    }

    @FXML
    void handleDecimal(ActionEvent event) {
        String currentText = displayField.getText();
        if (!currentText.contains(".")) {
            displayField.setText(currentText + ".");
            startNewNumber = false;
        }
    }

    @FXML
    void handleEquals(ActionEvent event) {
        calculateResult();
        operator = "";
    }

    private void calculateResult() {
        if (operator.isEmpty()) return;

        double number2 = safeParseDouble(displayField.getText());
        if (displayField.getText().equals("ERROR")) return;

        double result = 0;
        switch (operator) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                if (number2 == 0) {
                    displayField.setText("ERROR");
                    clearAll();
                    return;
                } else {
                    result = number1 / number2;
                }
                break;
        }
        displayField.setText(formatResult(result));
        startNewNumber = true;
    }

    @FXML
    void handleNumberButton(ActionEvent event) {
        Button clickedButton = (Button)event.getSource();
        String number = clickedButton.getText();
        appendNumber(number);
    }

    @FXML
    void handleOperatorButton(ActionEvent event) {
        Button clickedButton = (Button)event.getSource();
        String newOperator = clickedButton.getText();

        if (!operator.isEmpty()) {
            calculateResult();
        }

        number1 = safeParseDouble(displayField.getText());
        if (displayField.getText().equals("ERROR")) return;
        
        operator = newOperator;
        startNewNumber = true;
    }

    private void appendNumber(String number) {
        if (startNewNumber) {
            displayField.setText(number);
            startNewNumber = false;
        } else {
            String currentText = displayField.getText();
            if (currentText.equals("0")) {
                displayField.setText(number);
            } else {
                displayField.setText(currentText + number);
            }
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%s", result);
        }
    }

    private double safeParseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            displayField.setText("ERROR");
            return 0;
        }
    }

    @FXML
    void switchToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToCalScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("maytinh.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}