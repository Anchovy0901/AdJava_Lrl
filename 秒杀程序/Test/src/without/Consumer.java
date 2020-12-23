package without;

/**
 * Ïû·ÑÕß
 */
class Consumer implements Runnable {
	private Storage storage;

	public Consumer(Storage storage) {
		this.storage = storage;
	}

	public void run() {
		int i = 0;
		while(i<10)
		{
			i++;
			storage.pop();
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		
	}
}
