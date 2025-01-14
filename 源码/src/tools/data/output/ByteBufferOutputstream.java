/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools.data.output;

import io.netty.buffer.ByteBuf;

public class ByteBufferOutputstream implements ByteOutputStream {

    private final ByteBuf bb;

    public ByteBufferOutputstream(final ByteBuf bb) {
        super();
        this.bb = bb;
    }

    @Override
    public void writeByte(final byte b) {
        this.bb.writeByte(b);
    }
}
