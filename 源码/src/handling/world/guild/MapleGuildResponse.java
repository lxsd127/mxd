/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package handling.world.guild;

import tools.packet.MaplePacketCreator;

public enum MapleGuildResponse {
    ALREADY_IN_GUILD(0x28),
    NOT_IN_CHANNEL(0x2A),
    NOT_IN_GUILD(0x2D);
    
    private int value;

    private MapleGuildResponse(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    public byte[] getPacket() {
        return MaplePacketCreator.genericGuildMessage((byte) value);
    }
}
