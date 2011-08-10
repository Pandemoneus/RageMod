package net.rageland.ragemod.quest;

public class PlayerQuestData
{
	private Quest quest;
	private int objectiveCounter;
	private boolean playerOnQuest;

	public PlayerQuestData()
	{
		
	}
	
	public Quest getQuest()
	{
		return quest;
	}
	
	public int getObjectiveCounter()
	{
		return objectiveCounter;
	}
	
	public boolean isPlayerOnQuest()
	{
		return playerOnQuest;
	}
	
	/**
	 * Will increment the objectiveCounter if it is less then what is required
	 * by the quest.
	 */
	public void incrementObjectiveCounter()
	{
		if(objectiveCounter < quest.getQuestData().getObjectiveCounter())
			objectiveCounter++;
	}

	/**
	 * Starts a new quest for the player.
	 * 
	 * @param quest
	 * @param questCounter
	 */
	public void startNewQuest(Quest quest, int objectiveCounter)
	{
		this.quest = quest;
		this.objectiveCounter = objectiveCounter;
		this.playerOnQuest = true;
	}

	/**
	 * Resets the quest and questCounter. Called when a quest is finished.
	 */
	public void endQuest()
	{
		playerOnQuest = false;
	}

}