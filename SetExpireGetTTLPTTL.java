/*
  The following functions are implemented using Java:
  SET-Redis operations have been performed by overloading the SET() function to support its different operations 
  SET(key,value)
  Parameters 
  1. key represents "KEY"
  2. value represents the vakue to be mapped to the respective keys
  Returns "OK" if successfully done
  
  SET(key,expiry,type)
  Parameters 
  1. key represents "KEY"
  2. expiry represents the time of expiry of the respective key 
  3. type specifies : PX for storing the time in miliseconds and EX for seconds 
  Returns "OK" if successfully done
  
  GET(key)
  Parameter:
  1. key represents the key whose mapped value needs to be returned
  Returns the string value mapped if key exists
  
  EXPIRE(key,expiry,type)
  Parameters 
  1. key represents "KEY"
  2. expiry represents the time of expiry of the respective key 
  3. type specifies : PX for storing the time in miliseconds and EX for seconds 
  Returns 1 if successful and 0 otherwise
  
  TTL(key)
  Parameter:
  1. key represents the key whose remaining time to expire needs to be returned
  Returns the following values:
  1. -2 if key does not exist
  2. -1 if time of expiry is not set
  3. the remaining time to expire in seconds
  
  PTTL(key)
  Parameter:
  1. key represents the key whose remaining time to expire needs to be returned
  Returns the following values:
  1. -2 if key does not exist
  2. -1 if time of expiry is not set
  3. the remaining time to expire in miliseconds
  
*/


import java.util.*;
import java.util.concurrent.*;
import java.util.Date;

class DBContent
{
    // Store the value
    String value;
    // Store the time when time of expiry was set
    long time;
    // Store the time to expire
    long expiry;

    DBContent(String value, long time, long expiry)
    {
        this.expiry = expiry;
        this.value = value;
        this.time = time;
    }
}


class RedisDB
{
        // Map to store key and the respective unit of time of expiry pairings
        HashMap<String, String> tt = new HashMap<String,String>();
        // Map to store key and (value,time,expiry) mappings
        HashMap<String, DBContent> mp = new HashMap<String, DBContent>();

        String set(String key, String value)
        {
            if(mp.containsKey(key))
            {
                DBContent d = mp.get(key);
                mp.replace(key,new DBContent(value,d.time,d.expiry));
            }
            else
            {
                mp.put(key,new DBContent(value,-1,-1));
            }

            return "OK";
        }

        String set(int key, String value)
        {
            String keystring = Integer.toString(key);
            return set(keystring, value);
        }

        String set(String key, long expiry, String type)
        {

            if(mp.containsKey(key))
            {
                tt.put(key, type);
                DBContent d = mp.get(key);
                long timeInMilliSec = new Date().getTime(); 
                 TimeUnit ti = TimeUnit.MILLISECONDS;
                 if(type.equals("EX"))
                 {
                     mp.replace(key,new DBContent(d.value,timeInMilliSec, expiry*1000));
                 }
                 else if(type.equals("PX"))
                 {
                     mp.replace(key,new DBContent(d.value,timeInMilliSec, expiry));
                 }
                 else{
                     return "Specify unit of time : PX for miliseconds and EX for seconds";
                 }

            }
            else 
            {
                return "Key does not exist";
            }

            return "OK";
                
        }

        String set(int key, long expiry, String type)
        {
            String keystring = Integer.toString(key);
            return set(keystring, expiry, type);
        }

        String get(String key)
        {
            if(mp.containsKey(key))
            {
                DBContent d = mp.get(key);
                return d.value;
            }

            return "Key does not exist";
        }

        String get(int key)
        {
            String keyvalue = Integer.toString(key);
            return  get(keyvalue);
        }

        int expire(String key, long expiry, String type)
        {
            String rete = set(key,expiry,type);
            if(rete.equals("OK"))
                return 1;
            else return 0;
            
        }

        int expire(int key, long expiry, String type)
        {
            String rete = set(Integer.toString(key),expiry,type);
            if(rete.equals("OK"))
                return 1;
            else return 0;
            
        }

        long TTL(int key)
        {
            return TTL(Integer.toString(key));
        }

        long TTL(String key)
        {
            if(mp.containsKey(key))
            {
                DBContent d = mp.get(key);

                if(d.expiry == -1)
                    return -1;
                long timeInMilliSec = new Date().getTime(); 
                TimeUnit ti = TimeUnit.MILLISECONDS;
                long diff = d.time + d.expiry - timeInMilliSec;
                return ti.toSeconds(diff);
            }
            else{
                return -2;
            }
        }

        long PTTL(int key)
        {
            return PTTL(Integer.toString(key));
        }

        long PTTL(String key)
        {
            if(mp.containsKey(key))
            {
                DBContent d = mp.get(key);

                if(d.expiry == -1)
                    return -1;
                long timeInMilliSec = new Date().getTime(); 
                long diff = d.time + d.expiry - timeInMilliSec;
                return diff;
            }
            else{
                return -2;
            }
        }
}



public class SetExpireGetTTLPTTL {
    public static void main(String args[]) {
        RedisDB r = new RedisDB();
        System.out.println(r.set(2,"121av"));
        System.out.println(r.set("3141","1efghv"));
        System.out.println(r.set(2,1100000, "EX"));
        System.out.println(r.get(3141));
        System.out.println(r.expire(3141, 314721, "PX"));
        System.out.println(r.TTL("2"));
        System.out.println(r.PTTL(2));
        System.out.println(r.TTL(3141));
        System.out.println(r.PTTL("3141"));


    }
}
