/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools.data.output;

import java.awt.Point;

public interface LittleEndianWriter {
    public void writeZeroBytes(final int i);
    public void write(final int b);
    public void writeInt(final int i);
    public void writeShort(final int i);
    
    public void write(final byte b[]);
    public void write(final byte b);

    public void writeShort(final short s);
    
    public void writeLong(final long l);

    void writeAsciiString(final String s);
    void writeAsciiString(String s, final int max);
    void writePos(final Point s);
    void writeMapleAsciiString(final String s);
}
