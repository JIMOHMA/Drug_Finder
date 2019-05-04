package src;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class Digraph
{
    private Vertex[] vertices;
    private List<DirectedEdge>[] adj;
    private int V = 0;
    private int E = 0;

    @SuppressWarnings("unchecked")
    /**
     * Create a Digraph
     * @throws FileNotFoundException If the hash table can't be found.
     */
    public Digraph() throws FileNotFoundException
    {
        Iterable<Drug> d = Load.loadHashTables(false);
        List<Drug> lst = new LinkedList<Drug>();
        List<Vertex> vLst = new LinkedList<Vertex>();
        String curr = "";
        for (Drug drug : d)
        {
            if (curr.equals(""))
            {
                curr = drug.getState();
            }
            if (drug.getState().equals(curr))
            {
                lst.add(drug);
            }
            else
            {
                Vertex ver = new Vertex(curr, lst);
                vLst.add(ver);
                lst = new LinkedList<Drug>();
                lst.add(drug);
                curr = drug.getState();
            }
        }
        Vertex ver = new Vertex(curr, lst);
        vLst.add(ver);
        V = vLst.size();
        adj = (LinkedList<DirectedEdge>[]) new LinkedList[V];
        vertices = new Vertex[V];
        for (int v = 0; v < V; v++)
        {
            adj[v] = new LinkedList<DirectedEdge>();
        }
        for (int v = 0; v < V; v++)
        {
            //System.out.println(v);
            vertices[v] = vLst.get(v);
            for (int w = 0; w < V; w++)
            {
                if (v == w)
                {
                    continue;
                }
                else
                {
                    // from = v
                    // to = w
                    if (vLst.get(v).getAvgCost() < vLst.get(w).getAvgCost())
                    {
                        DirectedEdge e = new DirectedEdge(vLst.get(v), vLst.get(w));
                        adj[v].add(e);
                        E++;
                    }
                }
            }
        }
    }
    /**
     * Get the number of vertices in this Digraph
     * @return The number of vertices in this Digraph
     */
    public int V()
    {
        return this.V;
    }
    /**
     * Get the number of edges in this Digraph
     * @return The number of edges in this Digraph
     */
    public int E()
    {
        return this.E;
    }
    /**
     * Get the vth Vertex
     * @param v the Vertex to get
     * @return The vth Vertex
     * @throws ArrayIndexOutOfBoundsException Thrown when {@code v < 0} or {@code v >= V()}
     * @see V() 
     */
    public Vertex getVertex(int v) throws ArrayIndexOutOfBoundsException
    {
        try
        {
            return vertices[V];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            throw e;
        }       
    }
    /**
     * Get the Vertex with the state name specified
     * @param name The state name of the Vertex
     * @return The Vertex that has the State name, if it exists
     * @throws ArrayIndexOutOfBoundsException Thrown when the Vertex can't be found
     */
    public Vertex getVertex(String name) throws IllegalArgumentException
    {
        if(indexOf(name) == -1)
        {
            throw new IllegalArgumentException("Vertex does not exist!");
        }
        return getVertex(indexOf(name));
    }
    /**
     * Get the edges that point from a Vertex
     * @param v the index of the Vertex to get the edges that point from it
     * @return a List of DirectedEdges that point from the Vertex
     * @throws ArrayIndexOutOfBoundsException Thrown when {@code v >= V()}
     * @see V()
     */
    public List<DirectedEdge> adj(int v) throws ArrayIndexOutOfBoundsException
    {
        if(v >= V)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        return adj[v];
    }
    /**
     * Get the index of a Vertex by its name
     * @param name The name of the Vertex
     * @return The index of the Vertex if it exists, -1 if it doesn't exist.
     */
    public int indexOf(String name)
    {
        for(int v = 0; v < V; v++)
        {
            if(name.equals(vertices[v].getState()))
            {
                return v;
            }
        }
        return -1;
    }
}
