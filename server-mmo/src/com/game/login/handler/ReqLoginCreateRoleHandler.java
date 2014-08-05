package com.game.login.handler;

import com.game.account.struct.Account;
import com.game.login.message.ResLoginCreateRoleMessage;
import com.game.message.struct.Handler;
import com.game.role.struct.Role;
import com.manager.ManagerPool;
import com.message.util.MessageUtil;

public class ReqLoginCreateRoleHandler extends Handler{
    @Override
    public void exec() {
        com.game.login.message.ReqLoginCreateRoleMessage msg = (com.game.login.message.ReqLoginCreateRoleMessage)this.getMessage();
        Account account = ManagerPool.account.getAccount(this.getContext());
        Role role = ManagerPool.role.createRole(account, msg.getName());
        
        ResLoginCreateRoleMessage ret = new ResLoginCreateRoleMessage();
        ret.setRet((byte) 1);
        if (role != null) {
        	ret.setRet((byte) 0);
        	ret.getRoles().add(role.getBriefInfo());
        }
        MessageUtil.send(account, ret);
    }
}
