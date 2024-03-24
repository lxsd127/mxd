/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.maps;

public enum SummonMovementType {
    STATIONARY(0), //octo etc
    FOLLOW(1), //4th job mage
    WALK_STATIONARY(2), //reaper
    CIRCLE_FOLLOW(3), //bowman summons 
    CIRCLE_STATIONARY(4); //gavi only

    private final int val;

    private SummonMovementType(int val) {
        this.val = val;
    }

    public int getValue() {
        return val;
    }
}
