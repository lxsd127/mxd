/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools.data.input;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamByteStream implements ByteInputStream {
    private final InputStream is;
    private long read = 0;

    public InputStreamByteStream(final InputStream is) {
        this.is = is;
    }

    @Override
    public final int readByte() {
        int temp;
        try {
            temp = is.read();
            if (temp == -1) {
                throw new RuntimeException("EOF");
            }
            read++;
            return temp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public final long getBytesRead() {
        return read;
    }

    @Override
    public final long available() {
        try {
            return is.available();
        } catch (IOException e) {
            System.err.println("ERROR" + e);
            return 0;
        }
    }

    @Override
    public final String toString(final boolean b) { //?
        return toString();
    }
}
