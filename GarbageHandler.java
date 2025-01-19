
public class GarbageHandler extends Thread {
	public void run() {
		while (true) {
			try {
				Thread.sleep(30000);
			} catch (Exception e) {
				System.out.println("Error in SYS.GC!!! >>> "+e);
			}
			System.gc();
		}
		//So complicated I know!!!
		//Despite this, it is VERY important that we keep this for performance.
	}
}
