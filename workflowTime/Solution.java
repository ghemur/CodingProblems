package other.workflowTime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;


public class Solution {
  public static void main(String[] args) throws IOException {
//    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/other/workflowTime/input.txt"));
//    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    int durationsCount = Integer.parseInt(bufferedReader.readLine().trim());
    List<Integer> durations = IntStream.range(0, durationsCount).mapToObj(i -> {
      try {
        return bufferedReader.readLine().replaceAll("\\s+$", "");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    })
        .map(String::trim)
        .map(Integer::parseInt)
        .collect(toList());
    int parentsCount = Integer.parseInt(bufferedReader.readLine().trim());
    List<Integer> parents = IntStream.range(0, parentsCount).mapToObj(i -> {
      try {
        return bufferedReader.readLine().replaceAll("\\s+$", "");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    })
        .map(String::trim)
        .map(Integer::parseInt)
        .collect(toList());
    int childrenCount = Integer.parseInt(bufferedReader.readLine().trim());
    List<Integer> children = IntStream.range(0, childrenCount).mapToObj(i -> {
      try {
        return bufferedReader.readLine().replaceAll("\\s+$", "");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    })
        .map(String::trim)
        .map(Integer::parseInt)
        .collect(toList());
    int result = Result.minimumTime(durations, parents, children);
    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();
    bufferedReader.close();
    bufferedWriter.close();
  }
}

