package web.astar;

import java.util.Set;

/**
 * Created by Dorkinator on 4/9/2018.
 */
public interface Pathable<T> {

	public Pathable getParent();
	public void setParent(T t);

	public int getGScore();
	public int getFScrore();

	public void setGScore(int score);
	public void setFScore(int score);

	public int distance(T p);

	public Set<T> getValidNeighbors();
}
