package src;

import java.util.List;

public class Vertex
{
    private double avgCost;
    private String state;
    /**
     * Create a new vertex
     * @param state the name of the state
     * @param drugs the drugs sold in the state
     */
    public Vertex(String state, List<Drug> drugs)
    {
        this.state = state;
        double cost = 0;
        for(Drug d : drugs)
        {
            if(d.getClaims() == 0)
            {
                System.out.println(d);
            }
            cost += (double)d.getAggregateCost() / d.getClaims();
        }
        avgCost = (double)cost/drugs.size();
    }
    /**
     * Get the average aggregate cost per claim per drug
     * @return
     */
    public double getAvgCost()
    {
        return avgCost;
    }
    /**
     * Get the state this Vertex represents
     * @return the String state name
     */
    public String getState()
    {
        return state;
    }
    
    public String toString()
    {
        return "State: "+this.getState()+" with average cost: "+this.getAvgCost();
    }
}
