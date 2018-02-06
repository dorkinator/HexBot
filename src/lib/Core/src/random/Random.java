package random;

/**
 * Created by Dorkinator on 2/2/2018.
 */
public class Random {

	private static java.util.Random r = new java.util.Random();

	public static int nextInt(){
		return r.nextInt();
	}

	public static int nextInt(int max){
		return r.nextInt(max);
	}

	public static int nextInt(int min, int max){
		return r.nextInt(max-min)+min;
	}
}
