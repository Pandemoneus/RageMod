package net.rageland.ragemod.text;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public class Language 
{
	private String name;
	private ArrayList<ArrayList<String>> dictionary;	// First index is word size
	private Pattern puncPattern;
	
	public Language()
	{
		// Temp - set up sample dictionary
		name = "Test Language";
		puncPattern = Pattern.compile("([^\\.\\,\\!\\?\\;\\:]*)([\\.\\,\\!\\?\\;\\:]*)$");
		
		dictionary = new ArrayList<ArrayList<String>>();
		
		// Set up banks for up to 12 letter words
		for( int i = 0; i < 12; i++ )
		{
			dictionary.add(new ArrayList<String>());
		}
	}
	
	// Add a new word to the dictionary
	public void addWord(String word)
	{
		addWord(word, word.length());
	}
	public void addWord(String word, int length)
	{
		dictionary.get(length - 1).add(word);
	}
	
	// Returns a list of partially and completely translated version of the source string
	public ArrayList<String> translate(String source)
	{
		ArrayList<String> result = new ArrayList<String>();
		String[] split = source.split(" ");
		int total = split.length;
		int wordIndex;
		Random random = new Random();
		
		// Create an array of numbers that represents the words not translated yet
		ArrayList<Integer> toTranslate = new ArrayList<Integer>();
		for( int i = 0; i < total; i++)
			toTranslate.add(i);
		
		// Go through 4 passes to get 25%, 50%, 75%, and 100% translation
		for( int i = 1; i <= 4; i++ )
		{
			while( toTranslate.size() > (total * (1 - ((double)i / 4))) )
			{
				wordIndex = toTranslate.remove(random.nextInt(toTranslate.size()));
				split[wordIndex] = translateWord(split[wordIndex], random);
			}
			result.add(join(split, " "));
		}
		
		return result;
		
	}
	
	private String translateWord(String word, Random random) 
	{
		Matcher matcher = puncPattern.matcher(word);

	    if( matcher.find() )
	    	word = matcher.group(1);	// Separate the word without the punctuation
	    
		// Find the length of the word at the specified index
		int wordLength = word.length();
		
		if( wordLength > 0 )
		{
			// Cut all word lengths down to 12
			if( wordLength > 12 )
				wordLength = 12;
			
			// Pull a random word of that length from the dictionary
			String newWord = dictionary.get(wordLength - 1).get(random.nextInt(dictionary.get(wordLength - 1).size()));
			
			// Test for capitalization
			if( word.substring(0, 1).equals(word.substring(0, 1).toUpperCase()) )
				newWord = newWord.substring(0, 1).toUpperCase() + newWord.substring(1);
			
			// Return the replaced and processed word
			return newWord + matcher.group(2);		// Add the punctuation back	
		}
		else
			return "";
	}

	private static String join(String[] array, String delimiter) 
	{
		List<String> s = Arrays.asList(array);

	    if (s == null || s.isEmpty()) return "";
	    Iterator<String> iter = s.iterator();
	    StringBuilder builder = new StringBuilder(iter.next());
	    while( iter.hasNext() )
	    {
	        builder.append(delimiter).append(iter.next());
	    }
	    return builder.toString();
	}

	
	
	
	
	
	
	
	
	
}