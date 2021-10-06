import java.util.Scanner;
import java.util.Stack;


/**
 * Given a space-separated expression containing numbers and simple operations (+ - * /)
 * compute the value of the expression.
 * This solution passes through the expression doing higher order operations, saving those values
 * on a stack. Then values from the stack (potentially with negation are added).
 */
public class Solution1 {

  private static final String ERR = "NaN";

  public String solution(String s) {
    if (s == null || s.length() == 0) return ERR;

    Scanner scanner= new Scanner(s);
    Stack<Double> stack = new Stack<>();
    String op = "+";  // implicit at the beginning of the expression

    if (!scanner.hasNextDouble())
      return ERR;
    double curr = scanner.nextDouble();  // first number
    stack.push(curr);

    while(scanner.hasNext()) {
      op = scanner.next();

      if (!scanner.hasNextDouble())
        return ERR;
      curr = scanner.nextDouble();
      if ("+".equals(op))
        stack.push(curr);
      else if ("-".equals(op))
        stack.push(-curr);
      else if ("*".equals(op))
        stack.push(stack.pop() * curr);
      else if ("/".equals(op)) {
        if (curr == 0.0)
          return ERR;
        else
          stack.push(stack.pop() / curr);
      } else // not a valid operation
        return ERR;
    }
    double res = 0.0;
    while (!stack.empty())
      res += stack.pop();

    return String.valueOf(res);
  }


  public static void main(String...args) {
    Solution1 sol = new Solution1();

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

    input = "2 - 3 - 4";
    expected = "-5";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "1 * 2 + 3 * 4";
    expected = "14.0";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);

    input = "1 * 2 - 3 * 4";
    expected = "-10.0";
    System.out.println(input + " -> " + sol.solution(input) + "; expected: " + expected);
  }

}
