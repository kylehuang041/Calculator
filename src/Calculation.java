import java.util.Stack;
import org.apache.commons.math3.special.Gamma;

class Calculation {
	private static Stack<Double> operands = new Stack<>();
	private static Stack<Character> operators = new Stack<>();

	public static double solve(String str) {
		String[] parts = str.split("\\s+");
		int i = 0;
		// traverse through expression
		while (i < parts.length) {
			// if factorial number
			if (parts[i].contains("!")) {
				double operand = Double.parseDouble(parts[i].substring(0, parts[i].length() - 1));
				operands.push(factorial(operand));
				// if current string contains PI, then add to operand
			} else if (parts[i].charAt(0) == '\u03c0') {
				operands.push(Math.PI);
				// oif current string is a square root, then add to operand
			} else if (parts[i].charAt(0) == '\u221A') {
				operands.push(Math.sqrt(operands.pop()));
				// if current string is ')' then solve for the parenthesis expression and add to operands
			} else if (parts[i].equals(")")) {
				while (operators.peek() != '(') {
					char operator = operators.pop();
					double operand2 = operands.pop();
					double operand1 = operands.pop();
					calculate(operand1, operand2, operator);
				}
				operators.pop();
				// if current string is an integer, add to number to operands
			} else if (isNumber(parts[i])) {
				operands.push(Double.parseDouble(parts[i]));
				if (i + 1 < parts.length && parts[i + 1].equals("(")) {
					operators.push('*');
				}
			// push operators
			} else {
				operators.push(parts[i].charAt(0));
			}
			++i;
		}

		// calculate the rest of the operands
		while (operators.size() > 0) {
			char operator = operators.pop();
			double operand2 = operands.pop();
			double operand1 = operands.pop();
			calculate(operand1, operand2, operator);
		}

		return operands.pop();
	}

	private static void calculate(double operand1, double operand2, char operator) {
		if (operator == '*') {
			operands.push(operand1 * operand2);
		} else if (operator == '/') {
			operands.push(operand1 / operand2);
		} else if (operator == '+') {
			operands.push(operand1 + operand2);
		} else if (operator == '-') {
			operands.push(operand1 - operand2);
		} else if (operator == '%') {
			operands.push(operand1 % operand2);
		} else if (operator == '^') {
			operands.push(Math.pow(operand1, operand2));
		}
	}

	public static boolean isNumber(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * PRE: requires a starting integer value
	 * POST: return the factorial of the value
	 * 
	 * @param num starting value or value
	 * @return result -> factorial of the value
	 */
	private static double factorial(double num) {
		boolean isNegative = num < 0 ? true : false;
		num = Math.abs(num);
		return isNegative ? -Gamma.gamma(num + 1) : Gamma.gamma(num + 1);
	}
}