import java.util.ArrayList;
import java.util.Stack;

class Calculation {

	public static String solve(String str) {
		ArrayList<String> strArr = convertToPostFix(str);
		String temp = convertArrayListToString(strArr);
		System.out.println(temp);
		return "";
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
				boolean isNegative = operand < 0 ? true : false;
				postfix.add(String.valueOf(factorial(operand, isNegative)));
				// if current string contains PI, then add to operand
			} else if (parts[i].charAt(0) == '\u03c0') {
				postfix.add(String.valueOf(Math.PI));
				// if current string is a square root, then add to operand
			} else if (parts[i].charAt(0) == '\u221A') {
				postfix.add(String.valueOf(Math.sqrt(Double.parseDouble(parts[i + 1]))));
				i++; // Skip the next token as it has been processed as a square root
				// if current string is ')' then solve for the parenthesis expression and add to operands
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

		while (!tokens.empty())	postfix.add(String.valueOf(tokens.pop()));
		return postfix;
	}

	private static void calculatePostFix(ArrayList<String> postfix) {
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

	private static String convertArrayListToString(ArrayList<String> arr) {
		String res = "";

		for (String item : arr) {
			res += " " + item;
		}

		return res;
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
	 * PRE: requires a base and exponent
	 * POST: return the result of an exponential expression
	 *
	 * @param base     base number
	 * @param exponent exponent number
	 * @return result -> result of an exponential expression
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
	 * @param num        starting value or value
	 * @param isNegative flag indicating if the value is negative
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
