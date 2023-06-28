import java.util.Stack;

class Calculation {

	private static String convertToPostFix(String str) {
		String[] parts = 
		Stack<Character> operators = new Stack<>();
		String postfix = "";

		for (String )
	}

	public static double solve(String str) {
		String[] parts = str.split("\\s+");
		int i = 0;
		// traverse through expression
		while (i < parts.length) {
			// if factorial number
			if (parts[i].contains("!")) {
				double operand = Double.parseDouble(parts[i].substring(0, parts[i].length() - 1));
				boolean isNegative = operand < 0 ? true : false;
				operands.push(factorial(operand, isNegative));
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
	 * PRE: requires a base and exponent
	 * POST: return the result of a exponential expression
	 * 
	 * @param base     base number
	 * @param exponent exponent number
	 * @return result -> result of a exponential expression
	 */
	private static double power(double base, double exponent) {
		double res = 1;
		for (int i = 1; i <= Math.abs(exponent); i++) {
			res *= base;
		}
		return (exponent > 0) ? res : 1 / res;
	}

	/**
	 * PRE: requires a starting integer value
	 * POST: return the factorial of the value
	 * 
	 * @param num starting value or value
	 * @return result -> factorial of the value
	 */
	private static double factorial(double num, boolean isNegative) {
		num = Math.round(num);
		if (num < 0)
			isNegative = true;
		if (num == 0)
			return (isNegative) ? -1 : 1;
		return (!isNegative) ? num * factorial(num - 1, isNegative)
				: num * factorial(num + 1, isNegative);
	}
}