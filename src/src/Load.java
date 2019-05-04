package src;
import java.util.*;
import java.io.*;
/**
 * The Loading module
 * @author Duncan McKay
 * 
 */
public class Load
{
    public static final String database = "data/PartD_Prescriber_PUF_Drug_St_15.csv";
    public static final String hashes = "data/hash.txt";
    /**
     * The different sources that can be loaded
     * @author Duncan McKay
     *
     */
    public enum Source
    {
        HASH, DATABASE
    }
    /**
     * Load a source
     * @param src A Source enum describing what to load.
     * @return A Map object (A hash) of all the loaded objects
     * @throws FileNotFoundException If the database can't be found
     * @throws IOException If some other exceptions occurs during loading.
     */
    public static Map<Integer, Drug> load(Source src) throws FileNotFoundException, IOException
    {
        try
        {
            Map<Integer, Drug> m = new Map<Integer, Drug>();
            switch(src)
            {
                case HASH:
                    m = buildMap(loadHashTables(true));
                    break;
                case DATABASE:
                    createHashTables(loadDatabase());
                    m = buildMap(loadHashTables(true));
                    break;
            }
            return m;
        }
        catch(FileNotFoundException e)
        {
            if(src == Source.HASH)
            {
                System.out.println("Hashes don't exist!");
            }
            else
            {
                System.out.println("The database doesn't exist!");
            }
            throw e;
        }
    }
    private static Iterable<Drug> loadDatabase() throws FileNotFoundException, IOException
    {
        System.out.println("Loading database to create hashes. This may take some time...");
        Scanner in = new Scanner(new File(database));
        in.useDelimiter("\n");
        for(int i = 0; i < 3; i++)
        {
            in.next();
        }
        String currentState = "";
        Queue<Drug> drugs = new LinkedList<Drug>();
        while(in.hasNext())
        {
            String i = in.next();
            String state = i.substring(0, i.indexOf("@")).trim();
            if(!(state.equals(currentState)))
            {
                System.out.println("Loading for state: "+state+"...");
                currentState = state;
            }
            Drug d = parseDrug(i.split("@"));
            drugs.add(d);
        }
        in.close();
        System.out.println("Load complete");
        return drugs;
    }
    private static void createHashTables(Iterable<Drug> drugs) throws FileNotFoundException, IOException
    {
        System.out.println("Writing hashes...");
        FileWriter writer = new FileWriter(new File(hashes));
        for(Drug d : drugs)
        {
            writer.write(String.format("%s%n", (d.getState()+","+d.getName()+","+d.getClaims()+","+d.getAggregateCost())));
        }
        writer.close();
        System.out.println("Write complete");
    }
    private static Map<Integer, Drug> buildMap(Iterable<Drug> d)
    {
        Map<Integer, Drug> m = new Map<Integer, Drug>();
        for(Drug drug : d)
        {
            m.add(drug.hashCode(), drug);
        }
        return m;
    }
    /**
     * Load the hash specifically. Used in the Digraph
     * @param printStatus True if the console should print the current loading status
     * @return An Iterable of Drugs loaded from the hash
     * @throws FileNotFoundException If the hash can't be found
     */
    public static Iterable<Drug> loadHashTables(boolean printStatus) throws FileNotFoundException
    {
        Queue<Drug> d = new LinkedList<Drug>();
        if(printStatus)
        {
            System.out.println("Loading hashes...");
        }
        try
        {
            Scanner in = new Scanner(new File(hashes));
            in.useDelimiter("\n");
            String currentState = "";
            while(in.hasNext())
            {
                String i = in.next();
                String[] info = i.split(",");
                String state = info[0];
                if(printStatus && !(state.equals(currentState)))
                {
                    System.out.println("Loading for state: "+state+"...");
                    currentState = state;
                }
                int index = 1;
                String name = info[index++];
                if(name.startsWith("PANCRELIPASE 5"))
                {
                    name += info[index++];
                }
                int claims = Integer.parseInt(info[index++].trim());
                int aggregateCost = Integer.parseInt(info[index++].trim());
                Drug drug = new Drug(state, name, claims, aggregateCost);
                d.add(drug);
            }
            in.close();
            if(printStatus)
            {
                System.out.println("Load complete");
            }
            return d;
        }
        catch(FileNotFoundException e)
        {
            throw e;
        }
    }
    private static String removeCommasAndDollars(String in)
    {
        String contents = "";
        for(char i : in.toCharArray())
        {
            if(i == ',' || i == '$')
            {
                continue;
            }
            else
            {
                contents+=String.valueOf(i);
            }
        }
        return contents;
    }
    private static Drug parseDrug(String[] in)
    {
        boolean foundMoney = false;
        boolean foundState = false;
        boolean foundName = false;
        boolean foundClaims = false;
        String moneyIn = "";
        String state = "";
        String name = "";
        String claims = "";
        int emptyStrs = 7;
        int stringsSinceName = 0;
        for(String i : in)
        {
            //true if we've found everything for the drug
            if(foundState && foundMoney && foundName && foundClaims)
            {
                float moneyVal = Float.parseFloat(moneyIn);
                int aggregateCost = (int)(100*moneyVal);
                Drug drug = new Drug(state, name, Integer.parseInt(claims), aggregateCost);
                if(drug.getName().startsWith("PANCRELIPASE"))
                {
                    if(drug.getClaims() == 0)
                    {
                        System.exit(0);
                    }
                }
                return drug;
            }
            if(i.length() >= 2 || foundState)
            {
                if(emptyStrs >= 7 && foundState == false)
                {
                    foundState = true;
                    state = i.trim();
                    //System.out.println(state);
                    emptyStrs = 0;
                }
                else if(foundState && !(foundName))
                {
                    foundName = true;
                    name = i.trim();
                }
                else if(foundName && !(foundClaims))
                {
                    if(stringsSinceName == 2)
                    {
                        foundClaims = true;
                        //System.out.println(claims);
                        stringsSinceName = 0;
                        claims = removeCommasAndDollars(i.trim());
                    }
                    else
                    {
                        stringsSinceName++;
                    }
                }
                else if(!(foundMoney) && i.startsWith("$"))
                {
                    foundMoney = true;
                    moneyIn = removeCommasAndDollars(i);
                    //System.out.println(moneyIn);
                }
            }
            else
            {
                emptyStrs++;
            }
        }
        return null;
    }
}
