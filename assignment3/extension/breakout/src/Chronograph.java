public class Chronograph {
	
	private static final long ns2s = (long)1e9;
	
	private int FPS_LOCK;
	private int FRAME_TIME;
	private double deltaTime;
	
	private long prevTime;
	private long updateTime;
	private long sleepTime;

	// default initializer
	
	public Chronograph(int FPS_LOCK) {
		prevTime = System.nanoTime();
		this.FPS_LOCK = FPS_LOCK;
		this.FRAME_TIME = (int)(1e9 / FPS_LOCK);
		this.deltaTime = 1.0/FPS_LOCK;
		this.updateTime = 0;
		this.sleepTime = 0;
	}
	
	public void setFPS(int FPS) {
		this.FPS_LOCK = FPS;
		this.FRAME_TIME = (int)(1e9 / FPS_LOCK);
	}
	
	public int FPS() {
		return (int)Math.round(1.0/deltaTime);
	}

	public int FPSLimit() {
		return FPS_LOCK;
	}
	
	public double dt() {
		return deltaTime;
	}
	
	public void sync() {
		
		updateTime = System.nanoTime() - prevTime;
		sleepTime = FRAME_TIME - updateTime;
		sleep(sleepTime);
		
		long now = System.nanoTime();
		deltaTime = (double)(now - prevTime)/ns2s;
		prevTime = now;
	
	}
	
	// This funtion waits for the specified amount of nanosecond before exiting/
	// Thread.sleep() and pause() are not accurate enough for this application
	public static void sleep(long ns) {
		long now = System.nanoTime();
	    while (System.nanoTime() - now < ns) {
	    	// waiting till right time
	    }
	}
	
}
