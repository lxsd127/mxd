/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools.data.output;

import java.io.ByteArrayOutputStream;

public class BAOSByteOutputStream implements ByteOutputStream {
    private ByteArrayOutputStream baos;

    public BAOSByteOutputStream(final ByteArrayOutputStream baos) {
        super();
        this.baos = baos;
    }

    @Override
    public void writeByte(final byte b) {
        baos.write(b);
    }
}
