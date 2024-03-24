/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/

package client;

import java.io.Serializable;

public class SkillEntry implements Serializable {
    private static final long serialVersionUID = 9179541993413738569L;
    public int skillevel;
    public byte masterlevel;
    public long expiration;

    public SkillEntry(final int skillevel, final byte masterlevel, final long expiration) {
        this.skillevel = skillevel;
        this.masterlevel = masterlevel;
        this.expiration = expiration;
    }
}
