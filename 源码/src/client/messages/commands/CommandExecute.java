/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package client.messages.commands;

import client.MapleClient;
import constants.ServerConstants.CommandType;

public abstract class CommandExecute {
    public abstract int execute(MapleClient c, String[] splitted);

    enum ReturnValue {
        DONT_LOG,
        LOG;
    }

    public CommandType getType() {
        return CommandType.NORMAL;
    }

    public static abstract class TradeExecute extends CommandExecute {

        @Override
        public CommandType getType() {
            return CommandType.TRADE;
        }
    }
}
