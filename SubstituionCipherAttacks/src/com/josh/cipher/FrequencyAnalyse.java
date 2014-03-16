package com.josh.cipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FrequencyAnalyse {
	
	private final static Character[] frequency = {'E','T','A','O','I','R','H','N','S','L','D','C','U','M','F','G','P','W','Y','B','V','K','J','Q','X','Z'};
	
	public static List<Character>  monograms(String cipherText) {
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
		return sortByValue(frequencyMap);
	}
	
	private static List<Character> sortByValue(Map<Character, Integer> frequencyMap) {
		List<Entry<Character, Integer>> list = new LinkedList<Entry<Character, Integer>>(frequencyMap.entrySet());
		List<Character> key = new ArrayList<>();
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
        //Map<Character, Integer> sortedMap = new LinkedHashMap<Character, Integer>();
        for (Entry<Character, Integer> entry : list)
        {
        	key.add(entry.getKey());
            //sortedMap.put(entry.getKey(), entry.getValue());
        }

        return key;
	}
	
	public static char[] getKey(String cipherText) {
		List<Character> expectedFrequency = Arrays.asList(frequency);
		List<Character> frequencyList = monograms(cipherText);
		List<Character> missingLetters = new ArrayList<>();
		List<Character> charList = new ArrayList<>();
		char[] key = new char[26];
		
		System.out.println(expectedFrequency + "\n" + frequencyList);
		
		int count = 0;
		for(Character i = 'A'; i <= 'Z'; i++) {
			int index = expectedFrequency.indexOf(i);
			if(index < frequencyList.size()) {
				key[count] = frequencyList.get(index);
				count++;
			}
		}
		
		for(int i = 0; i < key.length; i++) {
			charList.add(key[i]);
		}
		
    	for(char letter = 'A'; letter <= 'Z'; letter++) {
    		if(!charList.contains(letter))
    			missingLetters.add(letter);
    	}
    	
    	for(int i = 0; i < charList.size(); i++) {
    		if(charList.get(i) == '\0') {
    			key[i] = missingLetters.get(0);
    			missingLetters.remove(0);
    		} 
    	}
		return key;
	}
}
