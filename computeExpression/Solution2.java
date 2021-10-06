import java.util.Scanner;
import java.util.Stack;


/**
 * Given a space-separated expression containing numbers and simple operations (+ - * /)
 * compute the value of the expression.
 * This solution does one pass. It computes the overall expression in res,
 * and higher priority expressions are computed in pri.
 */
public class Solution2 {

  private static final String ERR = "NaN";

  public String solution(String s) {
    if (s == null || s.length() == 0) return ERR;

    Scanner scanner = new Scanner(s);
    String op = "+";  // implicit at the beginning of the expression

    if (!scanner.hasNextDouble())
      return ERR;

    double curr = scanner.nextDouble();  // first number
    double res = 0.0; // accummulates overal result
    double pri = curr; // accummulates priority expression (multiplications/divisions)

    while(scanner.hasNext()) {
      op = scanner.next();

      if (!scanner.hasNextDouble())
        return ERR;
      curr = scanner.nextDouble();
      if ("+".equals(op)) {
        res += pri;
        pri = curr;
      } else if ("-".equals(op)) {
        res += pri;
        pri = -curr;
      } else if ("*".equals(op)) {
        pri *= curr;
      } else if ("/".equals(op)) {
        if (curr == 0.0)
          return ERR;
        else
          pri /= curr;
      } else // not a valid operation
        return ERR;
    }
    res += pri;

    return String.valueOf(res);
  }


  public static void main(String...args) {
    Solution2 sol = new Solution2();

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
