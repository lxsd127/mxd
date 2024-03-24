/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package handling.channel.handler;

import client.MapleClient;
import scripting.NPCScriptManager;
import tools.data.LittleEndianAccessor;

public class UserInterfaceHandler {

    public static final void CygnusSummon_NPCRequest(final MapleClient c) {
        if (c.getPlayer().getJob() == 2000) {
            NPCScriptManager.getInstance().start(c, 1202000);
        } else if (c.getPlayer().getJob() == 1000) {
            NPCScriptManager.getInstance().start(c, 1101008);
        }
    }

    public static final void InGame_Poll(final LittleEndianAccessor slea, final MapleClient c) {      
    }

    public static final void ShipObjectRequest(final int mapid, final MapleClient c) {
    }
}
