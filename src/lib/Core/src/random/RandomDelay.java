package random;

/**
 * Created by Dorkinator on 2/2/2018.
 */
public enum RandomDelay {
	SHORT {
		@Override
		public int get() {
			return Random.nextInt(60,240);
		}
	};


	public abstract int get();
}
