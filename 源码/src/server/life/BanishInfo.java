/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.life;

public class BanishInfo {
    private int map;
    private String portal, msg;

    public BanishInfo(String msg, int map, String portal) {
        this.msg = msg;
        this.map = map;
        this.portal = portal;
    }

    public int getMap() {
        return map;
    }

    public String getPortal() {
        return portal;
    }

    public String getMsg() {
        return msg;
    }
}
