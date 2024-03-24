/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/

package tools.data.input;

import java.io.IOException;

public class GenericSeekableLittleEndianAccessor extends GenericLittleEndianAccessor implements SeekableLittleEndianAccessor {
    private final SeekableInputStreamBytestream bs;

    public GenericSeekableLittleEndianAccessor(final SeekableInputStreamBytestream bs) {
        super(bs);
        this.bs = bs;
    }

    @Override
    public final void seek(final long offset) {
        try {
            bs.seek(offset);
        } catch (IOException e) {
            System.err.println("Seek failed" + e);
        }
    }

    @Override
    public final long getPosition() {
        try {
            return bs.getPosition();
        } catch (IOException e) {
            System.err.println("getPosition failed" + e);
            return -1;
        }
    }


    @Override
    public final void skip(final int num) {
        seek(getPosition() + num);
    }
}
