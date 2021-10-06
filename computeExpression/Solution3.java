import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;


/**
 * Given a space-separated expression containing numbers and simple operations (+ - * /)
 * compute the value of the expression. Parentheses are allowed.
 * This solution passes through the expression doing higher order operations, saving those values
 * on a stack. Then values from the stack (potentially with negation are added).
 * When parentheses are encountered, the computation is called recursively.
 */
public class Solution3 {

  private static final String ERR = "NaN";
  private static Pattern NUMBER_PATTER = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");

  private int index;

  public String solution(String s) {
    if (s == null || s.length() == 0) return ERR;

    String[] tokens = s.split(" ");

    index = 0;

    try {
      double res = compute(tokens);
      return res == Double.NEGATIVE_INFINITY || res == Double.POSITIVE_INFINITY ?
          ERR:
          String.valueOf(res);
    } catch(Exception e) {
      return ERR;
    }
  }

  private double compute(String[] tokens) {
    Stack<Double> stack = new Stack<>();
    String op = "+";
    double curr;

    while (index < tokens.length) {
      String token = tokens[index++];
      if (isValidNumber(token)) {
        curr = Double.valueOf(token);
        updateStack(op, curr, stack);
      } else if (")".equals(token)) {
        // We are at the end of parenthesis and we need to calculate and return the result;
        break;
      } else if ("(".equals(token)) {
        double insideParenthesisResult = compute(tokens);
        // Update the stack based on the result we got from parenthesis operations.
        updateStack(op, insideParenthesisResult, stack);
      } else {
        op = token;
      }

    }

    double res = 0.0;
    while(!stack.isEmpty()) {
      res += stack.pop();
    }

    return res;
  }

  void updateStack(String op, double curr, Stack<Double> stack) {
    if ("+".equals(op))
      stack.push(curr);
    else if ("-".equals(op))
      stack.push(-curr);
    else if ("*".equals(op))
      stack.push(stack.pop() * curr);
    else if ("/".equals(op)) {
      stack.push(stack.pop() / curr);
    } else // not a valid operation
      throw new IllegalArgumentException("Operation not recognized");
  }

  private static boolean isValidNumber(String token) {
    return NUMBER_PATTER.matcher(token).matches();
  }

  public static void main(String...args) {
    Solution3 sol = new Solution3();

    String input;
    String expected;

    input = " * 4";
    expected = "NaN";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "4";
    expected = "4.0";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "6 / 3";
    expected = "2.0";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "6 / 0";
    expected = "NaN";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "2 + 3 + 4";
    expected = "9.0";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "2 - 3 + -4";
    expected = "-5";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "1 * 2 + 3 * 4";
    expected = "14.0";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "1 * 2 - 3 * 4";
    expected = "-10.0";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "2 * ( 3 + 4 )";
    expected = "14.0";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "( 3 - 2 ) * ( 3 * ( 2 - 1 ) + 4 )";
    expected = "7.0";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);
  }

}
