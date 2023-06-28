import java.util.ArrayList;
import java.util.Stack;
import org.apache.commons.math3.special.Gamma;

class Calculation {

	public static String solve(String str) {
		ArrayList<String> strArr = convertToPostFix(str);
		return calculatePostFix(strArr);
	}

	/**
	 * PRE: requires a String str that contains the infix expression
	 * POST: converts it to postfix notation
	 * @return String
	 */
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
	
	/**
	 * PRE: requires a filled postfix ArrayList containing the postfix expression from infix
	 * POST: outputs the postfix result
	 * @return String
	 */
	private static String calculatePostFix(ArrayList<String> postfix) {
		if (postfix.size() <= 1) return postfix.remove(0);

		Stack<String> stack = new Stack<>();
		int i = 0;

		while (i < postfix.size()) {
			
			// if operator, then perform the calculation with two operands
			if (postfix.get(i).length() == 1 && isOperator(postfix.get(i).charAt(0))) {
				char operator = postfix.get(i).charAt(0);
				double op2 = Double.parseDouble(stack.pop());
				double op1 = Double.parseDouble(stack.pop());
				double result = calculate(op1, op2, operator);
				stack.push(String.valueOf(result));
			} else {
				stack.push(postfix.get(i)); // push the numbers onto stack
			}
			++i;
		}

		return stack.pop();
	}

	/**
	 * PRE: requires a character c
	 * POST: returns if c is a mathematical operator (+, -, *, /, ^, %)
	 * @return boolean
	 */
	private static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%';
	}

	/**
	 * PRE: requires two operators x and y
	 * POST: compares x and y if x is less than or equal to y according to order of precedence
	 * @return boolean
	 */
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

	/**
	 * PRE: requires two operands (double) and an operator (char)
	 * POST: returns the output calculation (operand1 operator operand2)
	 * @return double
	 */
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

	/**
	 * PRE: requires a string
	 * POST: returns true if the string contains a number. otherwise return fals
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * PRE: requires a starting integer value
	 * POST: return the factorial of the value
	 * @param num starting value or value
	 * @return double
	 */
	private static double factorial(double num) {
		boolean isNegative = num < 0 ? true : false;
		num = Math.abs(num);
		return isNegative ? -Gamma.gamma(num + 1) : Gamma.gamma(num + 1);
	}
}