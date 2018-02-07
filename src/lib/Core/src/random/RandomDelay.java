package random;

/**
 * Created by Dorkinator on 2/2/2018.
 */
public enum RandomDelay {
	NODE_TICK {
		@Override
		public int get() {
			return Random.nextInt(0,44);
		}
	},
	SHORT {
		@Override
		public int get() {
			return Random.nextInt(60,240);
		}
	};


	public abstract int get();
}
