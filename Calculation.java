import java.util.*;

public class Calculation {
  private static Stack<Double> operands;
  private static Stack<Character> operators;

  public static double solve(String str) {
    String[] parts = str.split(" ");
    int i = 0;
    // traverse through expression
    while (i < parts.length) {
      // if current character is ')' then solve for the parenthesis expression and add
      // to operands
      if (parts[i] == ")") {
        char operator = operators.pop();
        operators.pop();
        if (operator == '!') {
          double operand = operands.pop();
          boolean isNegative = operand < 0 ? true : false;
          operands.push(factorial(operand, isNegative));
        } else {
          double operand2 = operands.pop();
          double operand1 = operands.pop();
          calculate(operand1, operand2, operator);
        }
        ++i;
        // if current character is an integer, add to number to operands
      } else if (isNumber(parts[i])) {
        operands.push(Double.parseDouble(parts[i]));
        // if current character is PI
      } else if (parts[i].charAt(0) == '\u03c0') {
        operands.push(Math.PI);
        // otherwise push to operators
      } else {
        operators.push(parts[i].charAt(0));
        ++i;
      }
    }

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

  private static boolean isNumber(String str) {
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
