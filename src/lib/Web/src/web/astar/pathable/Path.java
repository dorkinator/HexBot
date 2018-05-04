package web.astar.pathable;

import web.astar.Pathable;

import java.util.List;

/**
 * Created by Dorkinator on 4/10/2018.
 */
public class Path <T extends Pathable>{
	List<T> path;
	int pathCost = Integer.MIN_VALUE;
	public Path(List<T> path){
		this.path = path;
	}

	public int getPathCost(){
		if(pathCost > Integer.MIN_VALUE || path == null){
			return pathCost;
		}else{
			T last = null;
			pathCost = 0;
			for(T i:path){
				if(last != null) {
					pathCost += (i.distance(last) * 10);
				}
				last = i;
			}
			return pathCost;
		}
	}

	public List<T> getPath(){
		return this.path;
	}

}
