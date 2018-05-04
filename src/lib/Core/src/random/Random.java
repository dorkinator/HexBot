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
		return r.nextInt(max+1);
	}

	public static int nextInt(int min, int max){
		int diff = max-min;
		return Random.nextInt(diff)+min;
	}

	public static double nextDouble(){
		return r.nextDouble();
	}

	public static boolean nextBoolean() {
		return r.nextBoolean();
	}
}
