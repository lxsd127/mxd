/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools;

import java.awt.Point;
import java.util.List;

public class AttackPair {
    public int objectid;
    public Point point;
    public List<Pair<Integer, Boolean>> attack;

    public AttackPair(int objectid, List<Pair<Integer, Boolean>> attack) {
        this.objectid = objectid;
        this.attack = attack;
    }

    public AttackPair(int objectid, Point point, List<Pair<Integer, Boolean>> attack) {
        this.objectid = objectid;
        this.point = point;
        this.attack = attack;
    }
}
