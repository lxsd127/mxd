/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools.data.input;

public interface ByteInputStream {
    int readByte();
    long getBytesRead();
    long available();
    String toString(final boolean b);
}
