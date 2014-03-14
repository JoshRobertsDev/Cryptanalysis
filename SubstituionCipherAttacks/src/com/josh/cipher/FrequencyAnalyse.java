package com.josh.cipher;

import java.util.HashMap;

public class FrequencyAnalyse {
	public static HashMap<Character, Integer>  monograms(String cipherText) {
		HashMap<Character,Integer> frequencyMap = new HashMap<Character,Integer>();          
		for(int i = 0; i < cipherText.length(); i++){
		   char c = cipherText.charAt(i);
		   Integer val = frequencyMap.get(new Character(c));
		   if(val != null){
			   frequencyMap.put(c, new Integer(val + 1));
		   }else{
			   frequencyMap.put(c,1);
		   }
		}
		return frequencyMap;
	}
}
