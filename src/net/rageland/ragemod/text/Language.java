package net.rageland.ragemod.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public class Language {
	private final String name;
	private final ArrayList<ArrayList<String>> dictionary = new ArrayList<ArrayList<String>>(); // First index is word size
	private final Pattern puncPattern;
	private final Random random = new Random();
	
	public static final int MAX_WORD_LENGTH = 16; 

	public Language() {
		// Temp - set up sample dictionary
		name = "Test Language";
		puncPattern = Pattern.compile("([^\\.\\,\\!\\?\\;\\:]*)([\\.\\,\\!\\?\\;\\:]*)$");

		// Set up banks for up to 16 letter words
		for (int i = 0; i < MAX_WORD_LENGTH; i++) {
			dictionary.add(new ArrayList<String>());
		}
	}

	// Add a new word to the dictionary
	public void addWord(final String word) {
		addWord(word, word.length());
	}

	private void addWord(final String word, final int length) {
		if (length < 1 || word == null || word.isEmpty())
			return;
		
		dictionary.get(length - 1).add(word);
	}

	// Returns a list of partially and completely translated version of the source string
	public ArrayList<String> translate(final String source) {
		ArrayList<String> result = new ArrayList<String>();
		String[] split = source.split("\\s+");
		int total = split.length;
		int wordIndex;

		// Add color prefixes to the English words
		for (int i = 0; i < split.length; i++)
			split[i] = Message.NPC_TEXT_COLOR + split[i];

		// Create an array of numbers that represents the words not translated yet
		ArrayList<Integer> toTranslate = new ArrayList<Integer>();
		for (int i = 0; i < total; i++)
			toTranslate.add(i);

		// Go through 4 passes to get 25%, 50%, 75%, and 100% translation
		for (int i = 1; i <= 4; i++) {
			while (toTranslate.size() > (total * (1 - ((double) i / 4)))) {
				wordIndex = toTranslate.remove(random.nextInt(toTranslate.size()));
				// Don't translate any of the codes
				if (!split[wordIndex].equals("<playerName/>") && !split[wordIndex].equals("<selfName/>"))
					split[wordIndex] = Message.NPC_FOREIGN_COLOR + translateWord(split[wordIndex]);
			}
			result.add(join(split, " "));
		}

		return result;

	}

	private String translateWord(String word) {
		// Remove the color prefix
		word = ChatColor.stripColor(word);

		final Matcher matcher = puncPattern.matcher(word);

		if (matcher.find())
			word = matcher.group(1); // Separate the word without the punctuation

		// Find the length of the word at the specified index
		int wordLength = word.length();

		if (wordLength > 0) {
			// Cut all word lengths down to maximum word length
			if (wordLength > MAX_WORD_LENGTH)
				wordLength = MAX_WORD_LENGTH;

			// Pull a random word of that length from the dictionary
			String newWord = dictionary.get(wordLength - 1).get(random.nextInt(dictionary.get(wordLength - 1).size()));

			// Test for capitalization
			if (word.substring(0, 1).equals(word.substring(0, 1).toUpperCase()))
				newWord = newWord.substring(0, 1).toUpperCase() + newWord.substring(1);

			// Return the replaced and processed word
			return newWord + matcher.group(2); // Add the punctuation back	
		} else
			return "";
	}

	public static String join(final String[] array, final String delimiter) {
		final List<String> s = Arrays.asList(array);

		if (s == null || s.isEmpty())
			return "";
		
		final Iterator<String> iter = s.iterator();
		final StringBuilder builder = new StringBuilder(iter.next());
		
		while (iter.hasNext()) {
			builder.append(delimiter).append(iter.next());
		}
		
		return builder.toString();
	}

}
