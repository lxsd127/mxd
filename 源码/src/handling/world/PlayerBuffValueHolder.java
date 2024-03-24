/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package handling.world;

import client.MapleBuffStat;
import java.io.Serializable;
import java.util.Map;
import server.MapleStatEffect;

public class PlayerBuffValueHolder implements Serializable {
    private static final long serialVersionUID = 9179541993413738569L;
    public long startTime;
    public int localDuration, cid;
    public MapleStatEffect effect;
    public Map<MapleBuffStat,Integer> statup;

    public PlayerBuffValueHolder(final long startTime, final MapleStatEffect effect, final Map<MapleBuffStat,Integer> statup, int localDuration, int cid) {
        this.startTime = startTime;
        this.effect = effect;
	this.statup = statup;
	this.localDuration = localDuration;
	this.cid = cid;
    }
}
