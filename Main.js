const equ = "-1 + 4 ( 2 / -3 ) * 5";

function solve(str) {
  const operands = [];
  const operators = [];
  const regex = /\d/;
  let i = 0;

  function calculate(operand1, operand2, operator) {
    if (operator === '*') {
      operands.push(operand1 * operand2);
    } else if (operator === '/') {
      operands.push(operand1 / operand2);
    } else if (operator === '+') {
      operands.push(operand1 + operand2);
    } else if (operator === '-') {
      operands.push(operand1 - operand2);
    } else if (operator === "%") {
      operands.push(operand1 % operand2);
    } else if (operator === "^") {
      operands.push(Math.pow(operand1, operand2));
    }
  }

  // Helper function to check if a character is an operator
  function isOperator(char) {
    return ['*', '/', '+', '-', '%', '^'].includes(char);
  }

  // traverse through expression
  while (i < str.length) {
    // if current character is ')' then solve for the parenthesis expression and add to operands
    if (str[i] === ')') {
      const operator = operators.pop();
      const operand2 = operands.pop();
      const operand1 = operands.pop();
      operators.pop();
      calculate(operand1, operand2, operator);
      ++i;
    // if current character is an integer, add to number to operands
    } else if (regex.test(str[i]) || (str[i] === '-' && regex.test(str[i + 1]))) {
      let num = "";
      if (str[i] === '-') {
        num += '-';
        ++i;
      }
      while (regex.test(str[i])) {
        num += str[i];
        ++i;
      }
      if (str[i] === '(') {
        operators.push('*');
      }
      operands.push(Number(num));
    // otherwise push to operators
    } else if (isOperator(str[i])) {
      operators.push(str[i]);
      ++i;
    } else {
      ++i;
    }
  }

  while (operators.length > 0) {
    const operator = operators.pop();
    const operand2 = operands.pop();
    const operand1 = operands.pop();
    calculate(operand1, operand2, operator);
  }

  return operands.pop();
}

console.log(solve(equ));
