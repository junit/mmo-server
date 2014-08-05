

import com.manager.ManagerPool;
import com.server.game.GameServer;

public class Start {
	public static void main(String[] args) {
		ManagerPool.init();
		GameServer.getInstance().init(ManagerPool.config.getServerConfig().getGameServerConfig().getPort());
	}

}
