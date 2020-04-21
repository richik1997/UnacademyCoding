# UnacademyCoding
Implementation of Redis Functions from Scratch
1. Why did you choose that language?

The main reason to choose Java is because of its Object oriented structure with presence of inbuilt TreeSet,HashSet,HashMap classes which makes it easier to implement the logics.

2. What are the further improvements that can be made to make it efficient?

These approaches are basic implementations of the Redis inbuilt operations. A lot of improvements can be made such as:
   Improve it by adding more operations to support different kinds of abstract data structures.
   Implement more complex functionalities.
   Though these few implemented operations take same time complexity as that of redis operations for small amount of data, by the help
   of distributed caching to store frequently used data, response time for large scale requests can be reduced.


3. What data structures have you used and why?

I have used the TreeSet class in Java to get the sorted order of the scores. TreeSet serves as an excellent choice for storing large amounts of sorted information which are supposed to be accessed quickly because of its faster access and retrieval time. TreeSet is basically implementation of a self-balancing binary search tree like Red-Black Tree. Therefore operations like add, remove and search take O(Log n) time. And operations like printing n elements in sorted order takes O(n) time. In addition to this, I have used HashMap to store the score value pairings based the order of scores maintained by the TreeSet. Apart from that I have used HashMap for maintaining the key-value pairings. It’s a linked list with each node consisting of four fields: hash, key, value, next node.

4. Do your implementation support multi-threaded operations? If No why can’t it be? If yes then how?

  Multithreading can easily be implemented in Java  by the Thread class
