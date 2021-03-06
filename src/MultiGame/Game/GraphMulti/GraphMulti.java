package MultiGame.Game.GraphMulti;

import java.util.*;

/**
 * This class represents a directed graph using adjacency list
 * representation
 */
public class GraphMulti
{
    private int V;   // No. of vertices
    private LinkedList<Integer> adj[]; //Adjacency Lists

    /**
     *  Constructor
     * @param v vertex of the graph
     */
    public GraphMulti(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    /**
     * Function to add an edge into the graph
     * @param v the start vertex of the edge
     * @param w the end vertex of the edge
     */
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    ArrayList<Integer> ans = new ArrayList<>();

    /**
     * prints BFS traversal from a given source s
     * @param s the starting vertex
     * @return ArrayList of all vertexes that have a path to starting vertex
     */

    public ArrayList<Integer> BFS(int s)
    {
        ans.clear();
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean visited[] = new boolean[V];

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            ans.add(s);

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        return ans;
    }
}