/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools.data.input;

import java.io.IOException;

public interface SeekableInputStreamBytestream extends ByteInputStream {
    void seek(long offset) throws IOException;
    long getPosition() throws IOException;
    String toString(final boolean b);
}
