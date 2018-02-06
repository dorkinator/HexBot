package script.nodescript;

/**
 * Created by Dorkinator on 2/2/2018.
 */
public abstract class Node {
	private String name;

	public Node(String name){
		this.name = name;
	}

	public abstract boolean activate();
	public abstract int execute();

	public String getName(){
		return this.name;
	}
}
