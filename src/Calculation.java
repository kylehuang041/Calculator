import java.util.ArrayList;
import java.util.Stack;
import org.apache.commons.math3.special.Gamma;

class Calculation {

	public static String solve(String str) {
		ArrayList<String> strArr = convertToPostFix(str);
		return calculatePostFix(strArr);
	}

	private static ArrayList<String> convertToPostFix(String str) {
		String[] parts = str.split("\\s+");
		Stack<Character> tokens = new Stack<>();
		ArrayList<String> postfix = new ArrayList<>();
		int i = 0;

		// traverse through expression
		while (i < parts.length) {
			// if factorial number
			if (parts[i].contains("!")) {
				double operand = Double.parseDouble(parts[i].substring(0, parts[i].length() - 1));
				postfix.add(String.valueOf(factorial(operand)));
				// if current string contains PI, then add to operand
			} else if (parts[i].contains("\u03c0")) {
				if (parts[i].charAt(0) == '\u03c0') {
					postfix.add(String.valueOf(Math.PI));
				} else {
					double num = Double.parseDouble(parts[i].substring(0, parts[i].length() - 1));
					postfix.add(String.valueOf(num * Math.PI));
				}
				// if current string is a square root, then add to operand
			} else if (parts[i].charAt(0) == '\u221A') {
				postfix.add(String.valueOf(Math.sqrt(Double.parseDouble(parts[i + 1]))));
				i++; // Skip the next token as it has been processed as a square root
				// if current string is ')' then solve for the parenthesis expression and add to
				// operands
			} else if (parts[i].equals("(")) {
				tokens.push('(');
			} else if (parts[i].equals(")")) {
				while (!tokens.empty() && tokens.peek() != '(')
					postfix.add(String.valueOf(tokens.pop()));
				tokens.pop();
				// if current string is an integer, add to number to operands
			} else if (isNumber(parts[i])) {
				postfix.add(parts[i]);
				if (i + 1 < parts.length && parts[i + 1].equals("(")) {
					tokens.push('*');
				}
				// push operators
			} else if (isOperator(parts[i].charAt(0))) {
				while (!tokens.empty() && tokens.peek() != '(' && isOperatorLessEqualTo(parts[i].charAt(0), tokens.peek())) {
					postfix.add(String.valueOf(tokens.pop()));
				}
				tokens.push(parts[i].charAt(0));
			}
			++i;
		}

		while (!tokens.empty())
			postfix.add(String.valueOf(tokens.pop()));
		return postfix;
	}

	private static String calculatePostFix(ArrayList<String> postfix) {
		Stack<String> stack = new Stack<>();
		int i = 0;

		while (i < postfix.size()) {
			if (isOperator(postfix.get(i).charAt(0))) {
				char operator = postfix.get(i).charAt(0);
				double op2 = Double.parseDouble(stack.pop());
				double op1 = Double.parseDouble(stack.pop());
				double result = calculate(op1, op2, operator);
				stack.push(String.valueOf(result));
			} else {
				stack.push(postfix.get(i));
			}
			++i;
		}

		return stack.pop();
	}

	private static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%';
	}

	private static boolean isOperatorLessEqualTo(char x, char y) {
		// if x < y in terms of operator precedence order, return true
		if ((x == '+' || x == '-') && (y == '*' || y == '/' || y == '%' || y == '^'))
			return true;

		// if x == y in terms of operator high precedence order, return true
		else if ((x == '*' || x == '/' || x == '%' || x == '^') && (y == '*' || y == '/'
				|| y == '%' || y == '^'))
			return true;

		// if x == y in terms of operator low precedence order, return true
		else if ((x == '+' || x == '-') && (y == '+' || y == '-'))
			return true;

		// otherwise, return false
		else
			return false;
	}

	private static double calculate(double operand1, double operand2, char operator) {
		if (operator == '*') {
			return operand1 * operand2;
		} else if (operator == '/') {
			return operand1 / operand2;
		} else if (operator == '+') {
			return operand1 + operand2;
		} else if (operator == '-') {
			return operand1 - operand2;
		} else if (operator == '%') {
			return operand1 % operand2;
		} else if (operator == '^') {
			return Math.pow(operand1, operand2);
		}
		throw new IllegalArgumentException();
	}

	public static boolean isNumber(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * PRE: requires a starting integer value
	 * POST: return the factorial of the value
	 *
	 * @param num        starting value or value
	 * @param isNegative flag indicating if the value is negative
	 * @return result -> factorial of the value
	 */
	private static double factorial(double num) {
		boolean isNegative = num < 0 ? true : false;
		return isNegative ? -1 * Gamma.gamma(num) : Gamma.gamma(num);
	}
}