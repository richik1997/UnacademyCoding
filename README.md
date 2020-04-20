# UnacademyCoding
Implementation of Redis Functions from Scratch
1. Why did you choose that language?

The main reason to choose Java is because of the inbuilt TreeSet,HashSet,HashMap classes which makes it easier to implement the logics.

2. What are the further improvements that can be made to make it efficient?

Don't have prior experience of working in Redis. With more experience much better implementations can be achieveable compared to the current rookie implementation. Reducing complexity of the operations and improving the performance of the functions to make them perform as accurately as the existing Redis functions will be my primary focus.

3. What data structures have you used and why?

I have used the TreeSet class in Java to get the sorted order of the scores. TreeSet serves as an excellent choice for storing large amounts of sorted information which are supposed to be accessed quickly because of its faster access and retrieval time. TreeSet is basically implementation of a self-balancing binary search tree like Red-Black Tree. Therefore operations like add, remove and search take O(Log n) time. And operations like printing n elements in sorted order takes O(n) time. In addition to this, I have used HashMap to store the score value pairings based the order of scores maintained by the TreeSet.
