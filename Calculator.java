/**
 * Class that handles the logic and state of the calculator.
 */
public class Calculator {
	private double currentResult; // The current result of the calculations
	private String currentOperator; // The last operator used (+, -, *, /)
	private boolean startNewNumber; // Indicates whether a new number should start being entered

	/**
	 * Default constructor. Initializes the calculator to its default state.
	 */
	public Calculator() {
		reset();
	}	


	/**
	 * Resets the calculator to its initial state.
	 */
	public void reset() {
		currentResult = 0;
		currentOperator = "";
		startNewNumber = true;
	}


	/**
	 * @return true if a new number should be started, false otherwise.
	 */
	public boolean isStartNewNumber() {
		return startNewNumber;
	}


	/**
	 * Sets whether a new number should be started.
	 * 
	 * @param startNewNumber true to start a new number, false otherwise
	 */
	public void setStartNewNumber(boolean startNewNumber) {
		this.startNewNumber = startNewNumber;
	}


	/**
	 * Sets the current operator for the calculation.
	 * 
	 * @param operator the operator (+, -, *, /)
	 */
	public void setOperator(String operator) {
		this.currentOperator = operator; 
	}


	/**
	 * Performs a calculation using the current operator and input number.
	 * 
	 * @param inputNumber the number to apply the operation to
	 * @return the updated result
	 * @throws ArithmeticException if division by zero occurs
	 */
	public double calculate(double inputNumber) {
		switch (currentOperator) {
		case "": 
			// First calculation when no operator has been selected yet
			currentResult = inputNumber;
			break;
		case "+":
			currentResult += inputNumber;
			break;
		case "-":
			currentResult -= inputNumber;
			break;
		case "*":
			currentResult *= inputNumber;
			break;
		case "/":
			// Prevent division by zero
			if (inputNumber == 0) {
				throw new ArithmeticException("Cannot divide by zero");
			}
			currentResult /= inputNumber;
			break;
		}
		return currentResult;
	}
}
