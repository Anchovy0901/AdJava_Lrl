package without;

import java.util.Random;

/**
 * 生产者
 */
class Producer implements Runnable {
	private Storage storage;

	public Producer(Storage storage) {
		this.storage = storage;
	}

	public void run() {
		int i = 0;
		Random r = new Random();
		while(i<10)
		{
			i++;
			Product product = new Product(i, "电话" + r.nextInt(100));
			storage.push(product);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
}
