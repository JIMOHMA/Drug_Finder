package src;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GraphServices
{
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private Digraph G;
    private int s;
    /**
     * Create Graph utilities (BFS, etc.) for a particular Digraph
     * @param d The Digraph
     * @param s the source index to perform searches on.
     */
    public GraphServices(Digraph d, int s)
    {
        this.G = d;
        this.s = s;
        this.bfs();
    }
    private void bfs()
    {
        marked = new boolean[G.V()];
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        Queue<Integer> q = new LinkedList<Integer>();
        for(int v = 0; v < G.V(); v++)
        {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;
        q.add(s);   
        while(!q.isEmpty())
        {
            int v = q.remove();
            for(DirectedEdge e : G.adj(v))
            {
                int w = G.indexOf(e.to().getState());
                if(!marked[w])
                {
                    edgeTo[w] = e;
                    distTo[w] = distTo[v] + e.weight();
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }
    /**
     * Check if a path exists to the index of the vertex
     * @param v the index of the vertex
     * @return true if a path exists to the index v from the source
     */
    public boolean hasPathTo(int v)
    {
        return this.marked[v];
    }
    /**
     * Get the path to the index v
     * @param v the index to get a path to
     * @return A Stack of the Vertices pushed in such an order that the represent the path
     */
    public Stack<Vertex> pathTo(int v)
    {
        if(!this.hasPathTo(v))
        {
            return null;
        }
        else
        {
            Stack<Vertex> path = new Stack<Vertex>();
            int x;
            for(x = v; distTo[x] != 0; x = G.indexOf(edgeTo[x].from().getState()))
            {
                path.push(G.getVertex(x));
            }
            path.push(G.getVertex(x));
            return path;
        }
    }
    /**
     * Get the distance to the index v
     * @param v the index of the Vertex to get the distance to.
     * @return the distance to the index v
     */
    public double distTo(int v)
    {
        return this.distTo[v];
    }
}
