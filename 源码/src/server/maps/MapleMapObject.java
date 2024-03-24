/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.maps;

import client.MapleClient;
import constants.GameConstants;
import java.awt.Point;

public abstract class MapleMapObject {
    public abstract MapleMapObjectType getType();
    public abstract void sendSpawnData(final MapleClient client); 
    public abstract void sendDestroyData(final MapleClient client);
    private Point position = new Point();
    private int objectId;

    public Point getPosition() {
        return new Point(position);
    }

    public Point getTruePosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int id) {
        this.objectId = id;
    }

    public int getRange() {
	return GameConstants.maxViewRangeSq();
    }
}
