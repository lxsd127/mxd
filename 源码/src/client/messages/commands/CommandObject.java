/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package client.messages.commands;

import client.MapleCharacter;
import client.MapleClient;
import constants.ServerConstants.CommandType;

public class CommandObject {
    /** what {@link MapleCharacter#gm} level is required to use this command */
    private int gmLevelReq;
    /** what gets done when this command is used */
    private CommandExecute exe;

    public CommandObject(CommandExecute c, int gmLevel) {
        exe = c;
        gmLevelReq = gmLevel;
    }

    /**
     * Call this to apply this command to the specified {@link MapleClient}
     * with the specified arguments.
     *
     * @param c the MapleClient to apply this to
     * @param splitted the arguments
     * @return See {@link CommandExecute#execute}
     */
    public int execute(MapleClient c, String[] splitted) {
        return exe.execute(c, splitted);
    }

    public CommandType getType() {
        return exe.getType();
    }

    /**
     * Returns the GMLevel needed to use this command.
     *
     * @return the required GM Level
     */
    public int getReqGMLevel() {
        return gmLevelReq;
    }
}
