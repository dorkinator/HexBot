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
	},
	BREAK_DELAY{
		@Override
		public int get() {
			return Math.min(13000,Random.nextInt(0,15000)+Random.nextInt(0,16000));
		}
	};



	public abstract int get();
}
