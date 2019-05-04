package src;
import java.util.Scanner;
import java.io.*;
public class Test
{
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        Map<Integer, Drug> m;
        try
        {
            m = Load.load(Load.Source.HASH);
        }
        catch(FileNotFoundException e)
        {
            try
            {
                m = Load.load(Load.Source.DATABASE);
            }
            catch(FileNotFoundException f)
            {
                throw e;
            }
        }
        Scanner in = new Scanner(System.in);
        while(true)
        {
            System.out.print("Hash Query. Has form: (State, NAME). type \"graph\" for graph applications or type \"done\" to exit: ");
            String query = in.nextLine();
            if(query.equalsIgnoreCase("done"))
            {
                in.close();
                System.exit(0);
            }
            else if(query.equalsIgnoreCase("graph"))
            {
                Digraph g = new Digraph();
                GraphServices s = null;
                while(true)
                {
                    System.out.print("Specify source state: ");
                    String src = in.nextLine();
                    try
                    {
                        s = new GraphServices(g, g.indexOf(src));
                        break;
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println("Invalid city");
                    }
                }
                while(true)
                {
                    System.out.print("Specify state. type \"done\" to exit graph applications: ");
                    query = in.nextLine();
                    if(query.equalsIgnoreCase("done"))
                    {
                        break;
                    }
                    else
                    {
                        if(!s.hasPathTo(g.indexOf(query)))
                        {
                            System.out.println("No path");
                            continue;
                        }
                        double dist = s.distTo(g.indexOf(query));
                        System.out.println(query+ " has average cost cheaper by: $" + Math.round(dist*100)/100.0);
                    }
                }
            }
            else
            {
                try
                {
                    String[] info = query.split(",");
                    int index = info[0].trim().hashCode() ^ info[1].trim().hashCode();
                    System.out.println("Hash index: " + index);
                    Drug d = m.get(index);
                    System.out.println(d);
                    System.out.println("Hash index Drug produces: " + d.hashCode());
                }
                catch(Exception e)
                {
                    System.out.println("Input Error. Ensure input is of correct format.");
                }
            }
        }
    }
}
