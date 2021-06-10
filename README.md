# Information Retrival Project


Built inverted index for local collection of documents the following functionality.
Use the supplied set of text files to test upon.
1. Implement the intersect algorithm given to you in the
intersect( HashSet<Integer> pL1, HashSet<Integer> pL2)
which return HashSet<Integer> contain the result of the intersection,
2. Use the intersect method to in find_01 (given in InvertedIndex002.java) 
Note: that the query must contain exactly 2 terms.
3. Use the intersect method to in find_02 (given in InvertedIndex002.java) 
Note: that the query must contain exactly 3 terms.
4. Extend the code in #(3) in find_03 to be able to work with any number of query terms.
 In case of 1 term do not use the intersect query (obviously)
In case 2 or more loop through the term list and intersect like you did in (3)
5. Modify the code in #(4) in find_04 in order to optimize the searched
(hint: choose the terms with least number of doc_freq
6. Use the given compare method in order to compar find vs find_03 vs find_04
explain the results
