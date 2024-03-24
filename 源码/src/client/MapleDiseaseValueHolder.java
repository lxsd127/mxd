/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package client;

import java.io.Serializable;

public class MapleDiseaseValueHolder implements Serializable {
    private static final long serialVersionUID = 9179541993413738569L;
    public long startTime;
    public long length;
    public MapleDisease disease;

    public MapleDiseaseValueHolder(final MapleDisease disease, final long startTime, final long length) {
        this.disease = disease;
        this.startTime = startTime;
        this.length = length;
    }
}
