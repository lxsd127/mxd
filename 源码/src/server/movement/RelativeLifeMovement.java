/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.movement;

import java.awt.Point;
import tools.data.MaplePacketLittleEndianWriter;

public class RelativeLifeMovement extends AbstractLifeMovement {
    private short unk;
    private Point pixelsPerSecond;

    public RelativeLifeMovement(int type, Point position, int duration, int newstate) {
        super(type, position, duration, newstate);
    }

    public void setUnk(short unk) {
        this.unk = unk;
    }

    public short getUnk() {
        return unk;
    }
    
    public void setPixelsPerSecond(Point wobble) {
        this.pixelsPerSecond = wobble;
    }

    @Override
    public void serialize(MaplePacketLittleEndianWriter lew) {
        lew.write(getType());
        lew.writePos(pixelsPerSecond);
        lew.write(getNewstate());
        lew.writeShort(getDuration());
    }
}
