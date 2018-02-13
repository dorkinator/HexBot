package random;

import script.ScriptProperties;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Dorkinator on 6/17/2017.
 */
public class RandomConstant {
	private String seed;
	private String constant;
	private long duration = 0;
	private int min;
	private int max;
	public RandomConstant(String constant, int min, int max){
		this.seed = (String) ScriptProperties.properties.get("username");
		this.constant = constant;
		this.min = min;
		this.max = max;
	}

	public RandomConstant(String constant, int min, int max, long duration){
		this.seed = (String) ScriptProperties.properties.get("username");
		this.constant = constant;
		this.min = min;
		this.max = max;
		this.duration = duration;
	}

	private long timeOffset(){
		return (long)(nextDouble(seed+constant+min+max+(System.currentTimeMillis()/duration))*duration);
	}

	private double nextDouble(String input){
		String hash = hash(input);
		final int ln = 6;
		final double max = Math.pow(16,ln);
		return ((double)Integer.parseInt(hash.substring(hash.length()-ln,hash.length()), 16))/max;
	}

	public double nextDouble(){
		return nextDouble(seed + constant + min + max + ((System.currentTimeMillis()+timeOffset()) / duration));
	}

	public int get(){
		return (int)((nextDouble()*(double)(max-min))+(double)min);
	}

	private static String hash(String input){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update((input).toString().getBytes("UTF-8")); // Change this to "UTF-16" if needed
			byte[] digest = md.digest();
			return String.format("%064x", new java.math.BigInteger(1, digest));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws InterruptedException {
		RandomConstant r = new RandomConstant("Dorkinator", 10, 250, 3000);
		for(int i = 0; i < 30; i++){
			System.out.println(r.get()+":"+r.nextDouble()+":"+r.timeOffset());
			Thread.sleep(1000);
		}
	}
}
