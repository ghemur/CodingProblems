package other.workflowTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Result {
  /*
   * Complete the 'minimumTime' function below.
   *
   * The function is expected to return an INTEGER.
   * The function accepts following parameters:
   *  1. INTEGER_ARRAY durations
   *  2. INTEGER_ARRAY parents
   *  3. INTEGER_ARRAY children
   */
  public static int minimumTime(List<Integer> durations, List<Integer> parents, List<Integer> children) {
    final int n = durations.size();
    int[] toFinish = new int[n];

    Graph graph = new Graph(n, parents, children);
    // Topological sort
//    Iterable<Integer> ordered = graph.topologicalOrder();
    Iterable<Integer> ordered = graph.topologicalOrderWithExplicitStack();

    int max = 0;
    for (int node: ordered) {
      toFinish[node] = durations.get(node);
      if (!graph.adj[node].isEmpty()) {
        // Add max value of toFinish for childrem
        toFinish[node] += graph.adj[node].stream().mapToInt(child -> toFinish[child]).max().orElse(0);
      }
      if ( toFinish[node] > max) {
        max = toFinish[node];
      }
    }
    return max;
  }

  /**
   * Directed graph with nodes represented by int values 0, 1, 2, etc
   */
  private static class Graph {
    final int n;  // number of vertices
    final List<Integer>[] adj;  // adjancency lists
    boolean[] visited;
    List<Integer> ordered;

    /**
     * Construct graph from edges
     */
    public Graph(int n, List<Integer> parents, List<Integer> children) {
      this.n = n;
      adj = new List[n];
      for (int i = 0; i < n; i++) {
        adj[i] = new ArrayList<>();
      }
      for (int i = 0; i < parents.size(); i++) {
        adj[parents.get(i)].add(children.get(i));
      }
    }

    public Iterable<Integer> topologicalOrder() {
      ordered = new ArrayList<>();
      visited = new boolean[n];
      for (int i = 0; i < n; i++) {
        if (!visited[i]) {
          dfs(i);
        }
      }
      return ordered;
    }
    private void dfs(int i) {
      visited[i] = true;
      for (int j: adj[i]) {
        if (!visited[j])
          dfs(j);
      }
      ordered.add(i);
    }

    public Iterable<Integer> topologicalOrderWithExplicitStack() {
      ordered = new ArrayList<>();
      visited = new boolean[n];
      Stack<Integer> stack = new Stack<>();
      for (int i = 0; i < n; i++) {
        if (!visited[i]) {
          stack.push(i);
          while (!stack.isEmpty()) {
            int node = stack.peek();
            visited[node] = true;
            boolean done = true;
            for (int j : adj[node]) {
              if (!visited[j]) {
                stack.push(j);
                done = false;
              }
            }
            if (done) {
              stack.pop();
              ordered.add(node);
            }
          }
        }
      }
      return ordered;
    }

  }
}
