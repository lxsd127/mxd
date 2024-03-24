/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools.data.input;

import java.awt.Point;

public interface LittleEndianAccessor {

    byte readByte();

    int readByteAsInt();

    char readChar();

    short readShort();

    int readInt();

    long readLong();

    void skip(int num);

    byte[] read(int num);

    float readFloat();

    double readDouble();

    String readAsciiString(int n);

    String readMapleAsciiString();

    String readNullTerminatedAsciiString();

    Point readPos();

    long getBytesRead();

    long available();

    String toString(final boolean b);
}
