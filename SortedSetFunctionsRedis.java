/*
  The following functions are implemented using Java:
  ZADD(score,value,key)
  Parameters 
  1. key represnts "KEY"
  2. scores represnts the list of scores
  3. value represents the list of values for respective scores
  
  ZRANGE(start,end, key)
  Parameters 
  1. key represnts "KEY"
  2. start represnts the starting value of the range
  3. end represnts the ending value of the range
  
  ZRANK(value,key)
  Parameters 
  1. key represnts "KEY"
  2. value represents the value whose rank needs to be searched
  
*/

import java.util.*;

class RedisDB
{
    // Map to store the score value pairings in increasing order of scores for respective keys
    HashMap<String,HashMap<Integer, String>> scorevalue = new HashMap<String,HashMap<Integer, String>>();
    // Map to store the scores in sorted order for respective keys
    HashMap <String, TreeSet<Integer>> scoressorted = new  HashMap <String, TreeSet<Integer>> ();

    void ZADD(ArrayList<Integer> score, ArrayList<String> value, String keyName)
    {
        
        TreeSet <Integer> sc = new TreeSet<Integer>();
        // If elements are needed to be added to
        // existing key
        if(scorevalue.containsKey(keyName))
        {
            // Fetch all the existing scores
            sc.addAll(scoressorted.get(keyName));

            HashMap <Integer, String> mp = new HashMap<>();
            Iterator<Integer> i = sc.iterator();
            while(i.hasNext())
            {
                int x = i.next();
                // Add the existing scores to the list
                score.add(x);
                // Add the existing values to the list
                value.add(scorevalue.get(keyName).get(x));
            }
            sc.clear();
        }

        // Store the scores in sorted order
        sc.addAll(score);
        
        HashMap <Integer, String> mp = new HashMap<>();
        Iterator<Integer> i = sc.iterator();
        while(i.hasNext())
        {
            int x = score.indexOf(i.next());
            // Insert the score value pairs 
            mp.put(score.get(x),value.get(x));
            
            System.out.println(value.get(x));
        }

        // Update the data for the respective key
        scorevalue.put(keyName, mp);
        scoressorted.put(keyName, sc);
    }

    // Returns the values mapped to scores within the given range
    // for the respective key
    ArrayList<String> ZRANGE(int start, int end, String keyName)
    {
        ArrayList<String> ans = new ArrayList<String>();
        // Check if the given key exists
        if(scoressorted.containsKey(keyName))
        {
            TreeSet<Integer> sc = scoressorted.get(keyName);
            // Store all scores lying in between the range
            TreeSet<Integer> subset = (TreeSet<Integer>)sc.subSet(start,end);
            
            if(subset.size() == 0) 
            {
                ans.add("nil");
                return ans;
            }
            Iterator<Integer> i = subset.iterator();
            
            while(i.hasNext())
            {
                int x = i.next();
                // Display corresponding values of every score
                ans.add(scorevalue.get(keyName).get(x));
            }

            return ans;
        }
        else
        {
            ans.add("nil");
            return ans;
        }
    }

    // Returns the rank of the value within all values
    // in the given key
    int ZRANK(String s,String keyName)
    {
        int x = -1;
        // Check if the key exists
        if(scorevalue.containsKey(keyName))
        {
            // Iterate over all scores present in the map
            // paired with the given key
            for (Integer i : scorevalue.get(keyName).keySet()) 
            {
                // Check if the value matches with any of the values
                if (s.equals(scorevalue.get(keyName).get(i))) {
                    // Store the score
                   x = i;
                }
            }

            // If the value is not found
            if(x == -1)
                return x;

            TreeSet<Integer> sc = scoressorted.get(keyName);
            // Fetch the scores which are less than the score x
            TreeSet<Integer> rank = (TreeSet<Integer>)sc.headSet(x);
            // Number of scores present in the treeset 
            // gives us the rank of the value 
            return rank.size();
        }

        // If the key does not exist
        return -2;
    }

}

class Main
{
    public static void main(String args[])
    {
        ArrayList<Integer> scores = new ArrayList<Integer>(); 
        scores.add(1); 
        scores.add(20); 
        scores.add(31); 
        scores.add(4); 


        ArrayList<String> values = new ArrayList<String>(); 
        values.add("1"); 
        values.add("2"); 
        values.add("3"); 
        values.add("4");

        RedisDB R = new RedisDB();
        System.out.println("Values in order of increasing scores:");
        R.ZADD(scores,values,"myset");
    
        scores.clear();
        scores.add(25); 
        scores.add(14); 


        values.clear();
        values.add("31"); 
        values.add("41");
        
        System.out.println("Values in order of increasing scores:");
        R.ZADD(scores,values,"myset");

       ArrayList<String> ans = R.ZRANGE(1,28, "myset");
       System.out.println("Values mapped to scores from "+ 1 +" to " + 28 +" :");
       for(String s: ans)
        System.out.println(s);

        for(String s : values)
        {
            int rank = R.ZRANK(s,"myset");
            if(rank == -1)
                System.out.println("Value does not exist");
            else if(rank == -2)
                System.out.println("Key does not exist");
            else
                System.out.println("Rank of "+ s + " is " + rank);
        }

    }
}
