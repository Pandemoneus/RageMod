package net.rageland.ragemod.text;

import java.util.ArrayList;
import java.util.HashMap;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Race;

public class Languages {
	private HashMap<Race, Language> languages = new HashMap<Race, Language>();

	public void loadDictionaries() {
	}

	// Translate a sentence into the specified language at all comprehension levels
	public ArrayList<String> translate(String source, Race race) {
		return languages.get(race).translate(source);
	}

	public String getAbbreviation(int id_Language) {
		switch (id_Language) {
		case 1:
			return "Cr";
		case 2:
			return "Gh";
		case 3:
			return "Be";
		case 4:
			return "Av";
		default:
			return "XX";
		}
	}

}
