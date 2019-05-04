package src;

public class DirectedEdge
{
    private Vertex from;
    private Vertex to;
    private double weight;
    /**
     * Create a Directed Edge. the weight is the difference in magnitude between the two vertices's average cost
     * @param from The vertex this edge points from
     * @param to The vertex this edge points to
     * @see Vertex#getAvgCost()
     */
    public DirectedEdge(Vertex from, Vertex to)
    {
        this.from = from;
        this.to = to;
        this.weight = Math.abs(from.getAvgCost() - to.getAvgCost());
    }
    /**
     * Get the vertex this edge points from
     * @return The Vertex object the Edge points from
     */
    public Vertex from()
    {
        return this.from;
    }
    /**
     * Get the vertex this edge points to
     * @return The Vertex object this Edge points to
     */
    public Vertex to()
    {
        return this.to;
    }
    /**
     * Get the weight of this edge
     * @return the weight of this edge
     */
    public double weight()
    {
        return this.weight;
    }
}
