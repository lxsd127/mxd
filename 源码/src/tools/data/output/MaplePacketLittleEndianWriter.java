/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools.data.output;

import java.io.ByteArrayOutputStream;
import tools.HexTool;

public class MaplePacketLittleEndianWriter extends GenericLittleEndianWriter {
    private final ByteArrayOutputStream baos;

    public MaplePacketLittleEndianWriter() {
        this(32);
    }

    public MaplePacketLittleEndianWriter(final int size) {
        this.baos = new ByteArrayOutputStream(size);
        setByteOutputStream(new BAOSByteOutputStream(baos));
    }

    public final byte[] getPacket() {
        return baos.toByteArray();
    }

    @Override
    public final String toString() {
        return HexTool.toString(baos.toByteArray());
    }
}
