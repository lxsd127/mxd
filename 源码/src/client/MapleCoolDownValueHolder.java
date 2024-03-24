/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package client;

public class MapleCoolDownValueHolder {
    public int skillId;
    public long startTime;
    public long length;

    public MapleCoolDownValueHolder(int skillId, long startTime, long length) {
        super();
        this.skillId = skillId;
        this.startTime = startTime;
        this.length = length;
    }
}
