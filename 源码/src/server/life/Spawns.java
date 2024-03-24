/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.life;

import java.awt.Point;
import server.maps.MapleMap;

public abstract class Spawns {
    public abstract MapleMonsterStats getMonster();
    public abstract byte getCarnivalTeam();
    public abstract boolean shouldSpawn(long time);
    public abstract int getCarnivalId();
    public abstract MapleMonster spawnMonster(MapleMap map);
    public abstract int getMobTime();
    public abstract Point getPosition();
    public abstract int getF();
    public abstract int getFh();
}
