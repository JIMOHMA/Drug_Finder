package src;

public class Drug implements Comparable<Drug>
{
    private final String state;
    private final String name;
    private final int claims;
    private final int aggregateCost;
    /**
     * Creates a Drug from a given state with a name, along with the number of claims and aggregate cost
     * @param state The state the drug is sold in
     * @param name the name of the drug
     * @param claims the number of claims in the Medicare Part D database
     * @param aggregateCost The aggregate cost in the Medicare Part D database.
     */
    public Drug(String state, String name, int claims, int aggregateCost)
    {
        this.state = state;
        this.name = name;
        this.claims = claims;
        this.aggregateCost = aggregateCost;
    }
    /**
     * Create another drug based of the drug specifed, Essentially, a copy of the drug specified
     * @param anotherDrug A Drug to copy
     */
    public Drug(Drug anotherDrug)
    {
        this(anotherDrug.state, anotherDrug.name, anotherDrug.claims, anotherDrug.aggregateCost);
    }
    /**
     * Get the name of the drug
     * @return The name of the drug
     */
    public String getName()
    {
        return this.name;
    }
    /**
     * Get the state the drug is sold in
     * @return The state the drug is sold in
     */
    public String getState()
    {
        return this.state;
    }
    /**
     * Get the number of claims for this drug
     * @return The number of claims for this drug
     */
    public int getClaims()
    {
        return this.claims;
    }
    /**
     * Get the aggregate cost of this drug
     * @return The aggregate cost of this drug
     */
    public int getAggregateCost()
    {
        return this.aggregateCost;
    }
    /**
     * Get the aggregate cost per claims for this drug
     * @return the average aggregate cost per claim
     */
    public double getAggregateCostPerClaim()
    {
        return (double)(this.aggregateCost)/this.claims;
    }
    /**
     * Hash the name of the drug
     * @return Hash of the name
     */
    public int hashByName()
    {
        return this.name.hashCode();
    }
    /**
     * Hash the state of this drug
     * @return Hash of the state
     */
    public int hashByState()
    {
        return this.state.hashCode();
    }
    /**
     * Creates a unique "ID" for this drug that can be used ofr hashing
     */
    public int hashCode()
    {
        return this.hashByName() ^ this.hashByState();
    }
    /**
     * Return a String representation of this Drug
     */
    public String toString()
    {
        return this.state+", "+this.name+", "+this.claims+" claims, with aggregate cost of $"+(this.aggregateCost/100.0);
    }
    @Override
    /**
     * Compare this drug to another.
     */
    public int compareTo(Drug d)
    {
        return Double.compare(this.getAggregateCostPerClaim(), d.getAggregateCostPerClaim());
    }
}
