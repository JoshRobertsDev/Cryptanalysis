package com.josh.cipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class FrequencyAnalyse {
	
	private final static Character[] frequency = {'E','T','A','O','I','R','H','N','S','L','D','C','U','M','F','G','P','W','Y','B','V','K','J','X','Z','Q'};
	
	private static Map<Character, Integer>  monograms(String cipherText) {
		cipherText = cipherText.replaceAll(" ", "");
		Map<Character,Integer> frequencyMap = new HashMap<Character,Integer>();          
		for(int i = 0; i < cipherText.length(); i++){
		   char c = cipherText.charAt(i);
		   Integer val = frequencyMap.get(new Character(c));
		   if(val != null){
			   frequencyMap.put(c, new Integer(val + 1));
		   }else{
			   frequencyMap.put(c,1);
		   }
		}
		//System.out.println(frequencyMap);
		return sortByValue(frequencyMap);
	}
	
	private static Map<Character, Integer> sortByValue(Map<Character, Integer> frequencyMap) {
		List<Entry<Character, Integer>> list = new LinkedList<Entry<Character, Integer>>(frequencyMap.entrySet());
        
		// Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Character, Integer>>()
        {
            public int compare(Entry<Character, Integer> o1,
                    Entry<Character, Integer> o2)
            {
            	return o2.getValue().compareTo(o1.getValue());
            }
        });
        // Maintaining insertion order with the help of LinkedList
        Map<Character, Integer> sortedMap = new LinkedHashMap<Character, Integer>();
        for (Entry<Character, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
	}
	
	public static char[] getKey(String cipherText) {
		List<Character> expectedFrequency = Arrays.asList(frequency);
		Map<Character, Integer> frequencyMap = monograms(cipherText);				
		List<Character> frequencyList = new ArrayList<>();
		frequencyList.addAll(frequencyMap.keySet());
		char[] key = new char[26];
		
		for(char letter = 'A'; letter <= 'Z'; letter++) {
    		if(!frequencyList.contains(letter))
    			frequencyList.add(letter);
    	}
		//System.out.println(expectedFrequency + "\n" + frequencyList);
		
		int count = 0;
		for(Character i = 'A'; i <= 'Z'; i++) {
			int index = expectedFrequency.indexOf(i);
			key[count] = frequencyList.get(index);
			count++;
		}		
		return key;
	}
	/*
	private String permutationString() {
		return null;
	}
	
	public static void permutation(String str) { 
	    permutation("", str); 
	}

	private static void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0) System.out.println(prefix);
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}*/
}