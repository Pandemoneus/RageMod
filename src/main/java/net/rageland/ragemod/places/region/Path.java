package net.rageland.ragemod.places.region;

import java.util.LinkedList;

public class Path {
	private final LinkedList<WayPoint> wayPoints = new LinkedList<WayPoint>();
	
	/**
	 * Constructs a new path with two WayPoints.
	 * @param first the first WayPoint
	 * @param last the last WayPoint
	 */
	public Path(WayPoint first, WayPoint last) {
		addWayPoints(first, last);
	}
	
	/**
	 * Constructs a new path with the given WayPoints.
	 * Note that the order of the WayPoints matters.
	 * The first WayPoint will be the first in the path,
	 * the second WayPoint the second and so on.
	 * @param points the WayPoints
	 */
	public Path(WayPoint... points) {
		addWayPoints(points);
	}
	
	/**
	 * Adds WayPoints to the current end of the path.
	 * Note that null elements are not allowed.
	 * @param points the WayPoints
	 */
	public void addWayPoints(WayPoint... points) {
		if (points == null)
			return;
		
		for (WayPoint wp : points) {
			if (wp == null)
				continue;
			
			wayPoints.addLast(wp);
		}
	}
	
	/**
	 * Adds a WayPoint at the given index.
	 * Does not do anything if the index is invalid.
	 * @param point
	 * @param index
	 */
	public void addWayPoint(WayPoint point, int index) {
		if (point == null || index < 0 || index >= length())
			return;
		
		wayPoints.add(index, point);
	}
	
	/**
	 * Returns the length of the path, or to be more accurate,
	 * the amount of WayPoints in the path.
	 * @return the length of the path
	 */
	public int length() {
		return wayPoints.size();
	}
	
	/**
	 * Returns whether the path has any WayPoints or not.
	 * @return true if the path has WayPoints, otherwise false
	 */
	public boolean hasWayPoints() {
		return length() > 0;
	}
	
	/**
	 * Removes a WayPoint from the path.
	 * @param index the index of the WayPoint
	 * @return the removed WayPoint
	 */
	public WayPoint remove(final int index) {
		if (index < 0 || index >= length())
			return null;
		
		return wayPoints.remove(index);
	}
	
	/**
	 * Removes all WayPoints from the path.
	 */
	public void clear() {
		wayPoints.clear();
	}
	
	/**
	 * Returns the WayPoint at the given index.
	 * Returns null when the index is invalid.
	 * @param index the index
	 * @return the WayPoint at the given index
	 */
	public WayPoint get(final int index) {
		if (index < 0 || index >= length())
			return null;
		
		return wayPoints.get(index);
	}
	
	/**
	 * Returns the next WayPoint at the index.
	 * Returns null when the end of the path is reached.
	 * @param index the index
	 * @return the next WayPoint at the index
	 */
	public WayPoint getNext(final int index) {
		return get(index + 1);
	}
	
	/**
	 * Returns the first WayPoint of the path.
	 * Returns null when there are no WayPoints yet.
	 * @return the first WayPoint of the path
	 */
	public WayPoint getFirst() {
		if (wayPoints.isEmpty())
			return null;
		
		return wayPoints.getFirst();
	}
}
