package net.rageland.ragemod.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.player.PlayerData;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.ChatColor;

public class Language {
	public final String name;
	public final boolean isForeign;
	private final ArrayList<ArrayList<String>> dictionary = new ArrayList<ArrayList<String>>(); // First index is word size
	private final Pattern puncPattern = Pattern.compile("([^\\.\\,\\!\\?\\;\\:]*)([\\.\\,\\!\\?\\;\\:]*)$");
	private final Random random = new Random();
	
	public static final int MAX_WORD_LENGTH = 12; 

	public Language(final String name, final boolean isForeign) {
		this.name = name;
		this.isForeign = isForeign;
		
		// Setup dictionary for up to MAX_WORD_LENGTH letter words
		for (int i = 0; i < MAX_WORD_LENGTH; i++) {
			dictionary.add(new ArrayList<String>());
		}
	}

	public void addWord(final String word) {
		if (word == null || word.isEmpty())
			return;
		
		dictionary.get(word.length() - 1).add(word);
	}

	public String translate(final String source, final int skillLevel) {
		if (source == null)
			return null;
		
		final String[] split = source.split("\\s+");
		final int total = split.length;

		// Create an array of numbers that represents the words not translated yet
		final ArrayList<Integer> toTranslate = new ArrayList<Integer>();
		for (int i = 0; i < total; i++)
			toTranslate.add(i);

		final int percentage = skillLevel >= 0 ? (total * skillLevel) / 100 : 0;
		
		while (toTranslate.size() > (percentage)) {
			final int wordIndex = toTranslate.remove(random.nextInt(toTranslate.size()));
			// Don't translate any of the codes
			if (!split[wordIndex].equals("$player") && !split[wordIndex].equals("$myname")) {
				split[wordIndex] = Message.NPC_FOREIGN_COLOR + translateWord(split[wordIndex]);
			} else {
				split[wordIndex] = Message.NPC_TEXT_COLOR + split[wordIndex];
			}
		}
		
		for (final int i : toTranslate) {
			split[i] = Message.NPC_TEXT_COLOR + split[i];
		}

		return join(split, " ");
	}
	
	public String translate(final String source, final PlayerData player) {
		if (player == null)
			return source;
		
		return translate(source, player.getLanguageSkill(Race.fromLanguage(this)));
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
		final StringBuilder sb = new StringBuilder(iter.next());
		
		while (iter.hasNext()) {
			sb.append(delimiter).append(iter.next());
		}
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof Language))
			return false;
		
		final Language l = (Language) o;
		
		return this.name.equalsIgnoreCase(l.name);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
    public int hashCode() {
    	return new HashCodeBuilder(23, 43).append(name).toHashCode();
    }
}
