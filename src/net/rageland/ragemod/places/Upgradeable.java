package net.rageland.ragemod.places;

public interface Upgradeable {
	/**
	 * Increases the level by one and grants all benefits.
	 * @return true if it was possible to level up, otherwise false
	 */
	public boolean levelUp();
	
	/**
	 * Decreases the level by one.
	 * @return true if it was possible to level down, otherwise false
	 */
	public boolean levelDown();
	
	/**
	 * Returns the current level.
	 * @return the current level
	 */
	public int getLevel();
	
	/**
	 * Returns the maximum possible level.
	 * @return the maximum possible level
	 */
	public int getMaxLevel();
	
	/**
	 * Returns whether the place is upgradeable.
	 * @return true if the place is upgradeable, otherwise false
	 */
	public boolean isUpgradeable();
	
	/**
	 * Gets the cost for the next level.
	 * @return the cost for the next level
	 */
	public double getUpgradeCost();
	
	/**
	 * Sets the upgrade costs for all levels.
	 * Note that the starting level is 0.
	 * @param costs the costs
	 */
	public void setUpgradeCosts(double[] costs);
}
