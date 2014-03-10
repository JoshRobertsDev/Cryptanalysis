package com.josh.cipher;

import dk.dren.hunspell.Hunspell;

public class Fitness {
	
	private String cipherText;
	private Cipher cipher = new Cipher();
	private Alphabet alphabet = new Alphabet();
	private Hunspell.Dictionary d;
	//private InputStream commonWordsInput = Fitness.class.getResourceAsStream("test.txt");
	//private List<List<String>> commonWords = new ArrayList<>();
	
	public Fitness() {
		String dir = "Dictionaries/en_GB";
		String language = "en_GB";
		try {
			d = Hunspell.getInstance().getDictionary(dir+"/"+language);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*InputStreamReader isr = new InputStreamReader(commonWordsInput);
		BufferedReader br = new BufferedReader(isr);
		String word;
		
		for(int i = 0; i < 59; i++) {
		    commonWords.add(new ArrayList<String>());
		}
		
		try {
			while ((word = br.readLine()) != null) {
				word = word.split(" ")[0]; 
				commonWords.get(word.length()).add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	public void setCipherText(String text) {
		this.cipherText = text;
	}

	public double score(char[] key) {
		String[] words = new String(cipher.substitute(cipherText, key, alphabet.getAlphabet())).split(" ");
		/*
		for(int i = 0; i < words.length; i++) {
			text += words[i] + " ";
			//System.out.println(commonWords.get(words[i].length()));
		}
		*/
		double totalWords = words.length;
		double correctWordsCount = 0;
		
		for(int i = 0; i < words.length; i++) {
			/*if(commonWords.get(words[i].length()).contains(words[i])) {
				correctWordsCount++;
			}*/
			if(!d.misspelled(words[i]))
				correctWordsCount++;
		}

		return correctWordsCount/totalWords;
	}
}
