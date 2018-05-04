package web.astar;

import web.astar.pathable.Path;

import java.util.*;

/**
 * Created by Dorkinator on 4/9/2018.
 */
public abstract class AStar{

	public static <T extends Pathable> Path findPath(T start, T end){
		Set<T> closedSet = new HashSet<>();
		Set<T> openSet = new HashSet<>();


		openSet.add(start);

		start.setGScore(0);
		start.setFScore(start.distance(end));

		while(!openSet.isEmpty()){
			T current = getLowestFScore(openSet);
			if(current.equals(end)){
				return new Path(getNodePath(current));
			}

			openSet.remove(current);
			closedSet.add(current);
			for(T i:(Set<T>)current.getValidNeighbors()){
				if(closedSet.contains(i)){
					continue;
				}
				if(!openSet.contains(i)){
					openSet.add(i);
				}

				int tentativeGScore = current.getGScore() + current.distance(i);
				if(tentativeGScore >= i.getGScore()){
					continue;
				}
				i.setParent(current);
				i.setGScore(tentativeGScore);
				i.setFScore(i.getGScore() + i.distance(end));
			}
		}
		return null;
	}

	private static <T extends Pathable> T getLowestFScore(Set<T> set){
		T best = null;
		for(T i:set){
			if(best == null){
				best = i;
				continue;
			}
			if(i.getFScrore() <= best.getFScrore()){
				best = i;
			}
		}
		return best;
	}

	private static <T extends Pathable> List<T> getNodePath(T node){
		List<T> out = new ArrayList<>();
		while(node != null){
			out.add(0, node);
			node = (T)node.getParent();
		}
		return out;
	}
}
