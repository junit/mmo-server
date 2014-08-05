import com.game.client.thread.ClientThread;
import com.game.manager.ManagerPool;
import com.game.thread.pool.FixedPoolExecutor;


public class Start {

	public static void main(String[] args) {
		ManagerPool.init();
		FixedPoolExecutor executor = new FixedPoolExecutor(32, 1000000);
		for (int i = 0; i < ManagerPool.config.getRobot(); ++i) {
			executor.execute(new ClientThread(i, ManagerPool.config.getIp(), ManagerPool.config.getPort()));
		}
	}

}
