/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.maps;

public abstract class AnimatedMapleMapObject extends MapleMapObject {
    private int stance;

    public int getStance() {
        return stance;
    }

    public void setStance(int stance) {
        this.stance = stance;
    }

    public boolean isFacingLeft() {
        return getStance() % 2 != 0;
    }

    public int getFacingDirection() {
        return Math.abs(getStance() % 2);
    }
}
