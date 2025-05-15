import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller class that handles user interactions with the calculator GUI.
 */
public class CalculatorController {

	@FXML
	private Label label;

	private Calculator calculator = new Calculator();
	private String currentInput = ""; // The current input string


    /**
     * Handles the press of the "C" button to clear all input and reset the calculator.
     */
	@FXML
	void CancelPressed(ActionEvent event) {
		calculator.reset();
		currentInput = "";
		label.setText("");
	}


    /**
     * Handles number or decimal point button presses.
     */
	@FXML
	void NumberPressed(ActionEvent event) {
		Button button = (Button)event.getSource(); // Get the button that was clicked
		String value = button.getText(); // Get the text on the button (number or ".")

		if(calculator.isStartNewNumber()) { 
			// If starting a new number input, reset the current input
			currentInput = ""; 
			calculator.setStartNewNumber(false);
		}

		if(value.equals(".") && currentInput.contains(".")) { 
			// Prevent more than one decimal point in the same number
			return; 
		}

		// Append the digit or decimal point to the current input
		currentInput += value; 
		
		// Display the updated input on the screen
		label.setText(currentInput); 
	}


    /**
     * Handles operator button presses (+, -, *, /).
     */  
	@FXML
	void operatorPressed(ActionEvent event) {
		Button button = (Button)event.getSource(); // Get the operator button pressed
		String operator = button.getText(); // Get the text on the button (+, -, *, /)

		if(currentInput.isEmpty()) {
			// If there's no input number, ignore the operator press
			return;
		}

		try {
			double inputNumber = Double.parseDouble(currentInput); 
			
			calculator.calculate(inputNumber); // Perform calculation with the previous result
			calculator.setOperator(operator);  // Set the new operator
			calculator.setStartNewNumber(true); // Mark that next input will be a new number
			currentInput = ""; // Clear current input
		} catch (Exception e) {
	        handleException(e);
	    }
	}


    /**
     * Handles the equals (=) button to compute the final result.
     */
	@FXML
	void EqualPressed(ActionEvent event) {
		if (currentInput.isEmpty()) {
			// Nothing to calculate
			return; 
		}

		try {
			double inputNumber = Double.parseDouble(currentInput);
			double result = calculator.calculate(inputNumber); // Compute result using current operator

			label.setText(formatResult(result));
			currentInput = ""; // Clear input after calculation
		} catch (Exception e) {
	        handleException(e);
	    }
		
		calculator.setOperator(""); // Clear operator after final calculation
	}


    /**
     * Handles the "-/+" button to change the sign of the current number.
     */
	@FXML
	void SignPressed(ActionEvent event) {
		if (currentInput.isEmpty()) {
			// No number to change sign for
			return; 
		}

		try {
			double number = Double.parseDouble(currentInput);
			number = -number; 

			currentInput = formatResult(number); // Update the input string with new value
			label.setText(currentInput); 
		} catch (Exception e) {
	        handleException(e);
	    }
	}
	
    /**
     * Formats the result to display as an integer if it has no decimal part.
     * 
     * @param result the numeric result to format
     * @return formatted string representation
     */
	private String formatResult(double result) {
		if (result == (long) result) { 
			// If the result is a whole number, remove the decimal point
			return String.format("%d", (long) result);
		}
		else { 
			return String.format("%s", result);
		}
	}
	
	/**
	 * Handles exceptions by updating the label and resetting input/calculator if needed.
	 * 
	 * @param e the exception to handle
	 */
	private void handleException(Exception e) {
	    if (e instanceof NumberFormatException) {
	        label.setText("Invalid input"); // Input string was not a valid number
	    } else if (e instanceof ArithmeticException) {
	        label.setText("Cannot divide by zero"); // Division by zero error
	        currentInput = "";
	        calculator.reset(); // Reset calculator state
	    } else {
	        label.setText("Error");
	        currentInput = "";
	    }
	}

}

