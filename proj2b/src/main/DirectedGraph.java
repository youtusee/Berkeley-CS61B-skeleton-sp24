package main;

import java.util.HashMap;
import java.util.HashSet;

public class DirectedGraph {

    private final HashMap<Integer, HashSet<Integer>> adjList;
    private int numVertices;
    private int numEdges;
    private boolean[] marked;

    /**
     * create empty graph with V vertices
     */
    public DirectedGraph(int V) {
        adjList = new HashMap<>();
        numVertices = 0;
        numEdges = 0;
        for (int i = 0; i < V; i++) {
            adjList.put(i, new HashSet<>());
            numVertices++;
        }
    }

    /**
     * add an edge v->w
     */
    public void addEdge(int v, int w) {
        adjList.get(v).add(w);
        numEdges++;
    }

    /**
     * return vertices adjacent to v
     */
    public HashSet<Integer> adj(int v) {
        return adjList.get(v);
    }

    /**
     * traverse the graph from v, return all traversed vertices
     */
    public HashSet<Integer> traverse(int v) {
        marked = new boolean[numVertices];
        dfs(v);
        HashSet<Integer> result = new HashSet<>();
        for (int i = 0; i < marked.length; i++) {
            if (marked[i]) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * depth first search starting at v
     */
    private void dfs(int v) {
        marked[v] = true;
        for (int w : adj(v)) {
            if (!marked[w]) {
                marked[w] = true;
                dfs(w);
            }
        }
    }

    /**
     * number of vertices
     */
    public int V() {
        return numVertices;
    }

    /**
     * number of edges
     */
    public int E() {
        return numEdges;
    }
}
