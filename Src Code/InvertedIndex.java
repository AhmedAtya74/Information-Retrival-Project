import java.io.*;
import java.util.*;


class DictEntry2 {

    public int doc_freq = 0; // number of documents that contain the term
    public int term_freq = 0; //number of times the term is mentioned in the collection
    public HashSet<Integer> postingList;

    DictEntry2() {
        postingList = new HashSet<Integer>();
    }
}
public class InvertedIndex {

		 //--------------------------------------------
	    Map<Integer, String> sources;  // store the doc_id and the file name
	    HashMap<String, DictEntry2> index; // THe inverted index
	    //--------------------------------------------

	    InvertedIndex() {
	        sources = new HashMap<Integer, String>();
	        index = new HashMap<String, DictEntry2>();
	    }

	    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
	    {
	        // Create a list from elements of HashMap
	        List<Map.Entry<String, Integer> > list =
	               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
	 
	        // Sort the list
	        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
	            public int compare(Map.Entry<String, Integer> o1,
	                               Map.Entry<String, Integer> o2)
	            {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });
	 
	        // put data from sorted list to hashmap
	        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> aa : list) {
	            temp.put(aa.getKey(), aa.getValue());
	        }
	        return temp;
	    }
	    //---------------------------------------------
	    public void printDictionary() {
	        Iterator it = index.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry pair = (Map.Entry) it.next();
	            DictEntry2 dd = (DictEntry2) pair.getValue();
	            HashSet<Integer> hset = dd.postingList;// (HashSet<Integer>) pair.getValue();
	            System.out.print("** [" + pair.getKey() + "," + dd.doc_freq + "] <" + dd.term_freq + "> =--> ");
	            Iterator<Integer> it2 = hset.iterator();
	            while (it2.hasNext()) {
	                System.out.print(it2.next() + ", ");
	            }
	            System.out.println("");
	            //it.remove(); // avoids a ConcurrentModificationException
	        }
	        System.out.println("------------------------------------------------------");
	        System.out.println("*** Number of terms = " + index.size());
	    }

	    //-----------------------------------------------
	    public void buildIndex(String[] files) {
	        int i = 0;
	        for (String fileName : files) {
	            try ( BufferedReader file = new BufferedReader(new FileReader(fileName))) {
	                sources.put(i, fileName);
	                String ln;
	                while ((ln = file.readLine()) != null) {
	                    String[] words = ln.split("\\W+");
	                    for (String word : words) {
	                        word = word.toLowerCase();
	                        // check to see if the word is not in the dictionary
	                        if (!index.containsKey(word)) {
	                            index.put(word, new DictEntry2());
	                        }
	                        // add document id to the posting list
	                        if (!index.get(word).postingList.contains(i)) {
	                            index.get(word).doc_freq += 1; //set doc freq to the number of doc that contain the term 
	                            index.get(word).postingList.add(i); // add the posting to the posting:ist
	                        }
	                        //set the term_fteq in the collection
	                        index.get(word).term_freq += 1;
	                    }
	                }
	                printDictionary();
	            } catch (IOException e) {
	                System.out.println("File " + fileName + " not found. Skip it");
	            }
	            i++;
	        }
	    }

	    //--------------------------------------------------------------------------
	    // query inverted index
	    // takes a string of terms as an argument
	    public String find(String phrase) {
	    	
	        String[] words = phrase.split("\\W+");
	        
	        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()).postingList);
	       
	        for (String word : words) {
	            res.retainAll(index.get(word).postingList);
	        }
	        if (res.size() == 0) {
	            System.out.println("Not found");
	            return "";
	        }
	        String result = "Found in: \n";
	        for (int num : res) {
	            result += "\t" + sources.get(num) + "\n";
	        }
	        return result;
	    }

	    //----------------------------------------------------------------------------  
	    HashSet<Integer> intersect(HashSet<Integer> pL1, HashSet<Integer> pL2) {
		    HashSet<Integer> answer = new HashSet();
		    Iterator it1 = pL1.iterator();
		    Iterator it2 = pL2.iterator();
	 
		    Integer n1 = (Integer) it1.next();
		    Integer n2 = (Integer) it2.next();
	 
		    while(n1 != null && n2 != null)//this loop will end when one of the lists ends 
	            {
		    	if(n1.compareTo(n2) == 0) //n1 = n2 -> will move the two iterator to the comming position
	                {
		    		answer.add(n1);
		    	    n1 = (it1.hasNext())? (Integer) it1.next(): null;
		    	    n2 = (it2.hasNext())? (Integer) it2.next(): null;
		    	}
		    	else if(n1.compareTo(n2) < 0) //n1 < n2 -> Will move the first iterator
	                {
		    		n1 = (it1.hasNext())? (Integer) it1.next(): null;
		    	}
		    	else//n1>n2 -> Will move the second iterator 
	                {
		    		n2 = (it2.hasNext())? (Integer) it2.next(): null;
		    	} 
		    }
		    return answer;//answer will be holding the set of commen document number for both two posting lists
	    }
	    //-----------------------------------------------------------------------   

	    public String find_01(String phrase) { // 2 term phrase  2 postingsLists
	        String result = "";
	        String[] words = phrase.split("\\W+");
	        // 1- get first posting list
	        HashSet<Integer> pL1 = new HashSet<Integer>(index.get(words[0].toLowerCase()).postingList);
	        // 2- get second posting list
	        HashSet<Integer> pL2 = new HashSet<Integer>(index.get(words[1].toLowerCase()).postingList);
	        // 3- apply the algorithm
	        HashSet<Integer> answer = intersect(pL1, pL2);
	        result += "Found in: \n";
	        for (int num : answer) {
	            //System.out.println("\t" + sources.get(num));
	            result += "\t" + sources.get(num) + "\n";
	        }
	        return result;
	    }
	//-----------------------------------------------------------------------         

	    public String find_02(String phrase) { //  lists
	        String result = "";
	        if(phrase == "")
	        	return result;
	        
	        String[] words = phrase.split("\\W+");
	        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()).postingList);
	        for(int i = 1 ; i < words.length ; i++) {
	        	res = intersect(res,index.get(words[i].toLowerCase()).postingList);
	        }
	        result += "Found in: \n";
	        for( int id : res) {
	        	result += "\t" + sources.get(id) + "\n";
	        }
	        // write you code here
	        return result;

	    }
	    //-----------------------------------------------------------------------         

	    public String find_03(String phrase) { // optimized search 
	    	 String result = "";
		        if(phrase == "")
		        	return result;
		        
		        String[] words = phrase.split("\\W+");
		        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()).postingList);
		        for(int i = 1 ; i < words.length ; i++) {
		        	res = intersect(res,index.get(words[i].toLowerCase()).postingList);
		        }
		        result += "Found in: \n";
		        for( int id : res) {
		        	result += "\t" + sources.get(id) + "\n";
		        }
		        // write you code here
		        return result;

	    }
	    //-----------------------------------------------------------------------         

	    public String find_04(String phrase) { // optimized search 
	        String result = "";
	        if(phrase == "")
	        	return result;
	        
	        String[] words = phrase.split("\\W+");
	        HashMap<String, Integer> map=new HashMap<String, Integer>();  
	        for(String word : words) {
		        map.put(word, (index.get(word.toLowerCase()).postingList).size());
	        }
	        
	        Map<String, Integer> sortedMap = sortByValue(map);
	        HashSet<Integer> res = new HashSet<Integer>();
	        for (String key : sortedMap.keySet()) {
		        res = new HashSet<Integer>(index.get(key.toLowerCase()).postingList);
		        break;
	        	}
	        
	        for (Object key : sortedMap.keySet()) {
	        	res = intersect(index.get(key.toString().toLowerCase()).postingList , res);
	    	}
	        
	        result += "Found in: \n";
	        for( int id : res) {
	        	result += "\t" + sources.get(id) + "\n";
	        }
	        // write you code here
	        return result;
	    }
	    //-----------------------------------------------------------------------         
	    // C > 100
	    // C > 106
	    public void compare(String phrase) { // optimized search 
	        long iterations=1000000;
	        String result = "";
	        long startTime = System.currentTimeMillis();
	        for (long i = 1; i < iterations; i++) {
	            result = find(phrase);
	        }
	        long estimatedTime = System.currentTimeMillis() - startTime;
	        System.out.println(" (*) elapsed = " + estimatedTime+" ms.");
	        
	        System.out.println(" result = " + result);
	        startTime = System.currentTimeMillis();
	        for (long i = 1; i < iterations; i++) {
	            result = find_03(phrase);
	        }
	        estimatedTime = System.currentTimeMillis() - startTime;
	        System.out.println(" (*) Find_03 non-optimized intersect  elapsed = " + estimatedTime +" ms.");
	        System.out.println(" result = " + result);
	        
	        startTime = System.currentTimeMillis();
	        for (long i = 1; i < iterations; i++) {
	            result = find_04(phrase);
	        }
	        estimatedTime = System.currentTimeMillis() - startTime;
	        System.out.println(" (*) Find_04 optimized intersect elapsed = " + estimatedTime+" ms.");
	        System.out.println(" result = " + result);
	    }
}
