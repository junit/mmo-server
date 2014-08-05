package ${pkg}.handler;

import com.game.message.struct.Handler;
import com.game.account.struct.Account;
import org.apache.log4j.Logger;

public class ${name}Handler extends Handler{
	private static Logger logger = Logger.getLogger(${name}Handler.class);
    @Override
    public void exec() {
        ${pkg}.message.${name}Message msg = (${pkg}.message.${name}Message)this.getMessage();
        Account account = ManagerPool.account.getAccount(this.getContext());
    }
}
