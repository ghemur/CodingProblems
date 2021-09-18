package codility.sumToTarget;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;


/**
 * Given an array of integers and a target value y, return the maximum length of a subarray that sums to y.
 */
class Solution {

  public int solution(int[] a, int y) {
    if (a == null || a.length == 0) return 0;
//     return bruteForce(a, y);
//     return withSums(a, y);
    return withTargetLookup(a, y);
  }

  private int bruteForce(int[] a, int y) {
    System.out.println("Running bruteForce ...");
    final int n = a.length;
    int max = 0;
    for (int i = 0; i < n; i++) {
      for (int j = i; j < n; j++) {
        int sum = add(a, i, j);
        if (sum == y && j - i + 1 > max) {
          max = j - i + 1;
        }
      }
    }
    return max;
  }

  private int withSums(int[] a, int y) {
    System.out.println("Running withSums ...");
    final int n = a.length;
    int max = 0;
    int[] sums = prefixSums(a);
    for (int i = 0; i < n; i++) {
      for (int j = n - 1; j >= i; j--) {
        int sum = sums[j + 1] - sums[i];
        if (sum == y) {
          if (j - i + 1 > max)
            max = j - i + 1;
          break;
        }
      }
    }
    return max;
  }

  private int withTargetLookup(int[] a, int y) {
    System.out.println("Running withTargetLookup ...");
    final int n = a.length;
    int max = 0;
    int[] sums = prefixSums(a);
    for (int i = 0; i < n; i++) {
      // Need j so that sums[j + 1] == sums[i] + y
      int target = sums[i] + y;
      int j = hitTarget(a, sums, i, target) - 1;
      if (j >= 0 && j - i + 1 > max) {
        max = j - i + 1;
      }
    }
    return max;
  }
  private int hitTarget(int[] a, int[] sums, int i, int target) {
    for (int j = a.length - 1; j >= i; j--) {
      if (sums[j] == target) {
        return j;
      }
    }
    return -1;
  }
  /*
    1 4 10
    1 5 15
    i = 0, j= 0, sum = 1 = 1 - 0
    i = 2, j = 2, sum = 10
    i = 1, j = 2
  */
  private int add(int[] a, int start, int end) {
    int sum = 0;
    for (int i = start; i <= end; i++) {
      sum += a[i];
    }
    return sum;
  }

  private int[] prefixSums(int[] a) {
    int[] sums = new int[a.length + 1];
    sums[0] = 0;
    for (int i = 0; i < a.length; i++) {
      sums[i + 1] = sums[i] + a[i];
    }
    return sums;
  }

  public static void main(String...args) {
    Solution sol = new Solution();
    sol.printSolution(new int[]{1, -1, 5, -2, 3}, 3, 4);
    sol.printSolution(new int[]{-2, -1, 2, 1}, 1, 2);
    sol.printSolution(new int[]{-1, -1, -1, -1}, 1, 0);
    sol.printSolution(new int[]{}, 1, 0);

    final int n = 10_000;
    final int lowerBound = -10;
    final int upperBound = 10;
    final int target = 0;
    int[] a = new Random().ints(n, lowerBound, upperBound).toArray();

//    runBruteForce(a);
//    runWithSums(a);
//    runWithTargetLookups(a);

    reportDuration(a, Solution::runBruteForce);
    // Output:
    // Running bruteForce ...
    // Duration: 53394
    reportDuration(a, Solution::runWithSums);
    // Output:
    // Running withSums ...
    // Duration: 43
    reportDuration(a, Solution::runWithTargetLookups);
    // Output:
    // Running withTargetLookup ...
    // Duration: 26
  }

  private static void runBruteForce(final int[] a) {
    new Solution().bruteForce(a, 0);
  }
  private static void runWithSums(final int[] a) {
    new Solution().withSums(a, 0);
  }
  private static void runWithTargetLookups(final int[] a) {
    new Solution().withTargetLookup(a, 0);
  }

  private static void reportDuration(int[] a, Consumer<int[]> function) {
    Instant start = Instant.now();
    function.accept(a);
    Instant finish = Instant.now();
    long timeElapsed = Duration.between(start, finish).toMillis();
    System.out.println("Duration: " + timeElapsed);
  }

  private void printSolution(int[] a, int target, int expected) {
    System.out.println(Arrays.toString(a) + " -> " + target + "; found: " + solution(a, target) + "; expected: " + expected);
  }

}

