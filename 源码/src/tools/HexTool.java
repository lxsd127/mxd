/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools;

import constants.ServerConfig;
import java.io.ByteArrayOutputStream;
import io.netty.buffer.ByteBuf;

/**
 * Provides a class for manipulating hexadecimal numbers.
 *
 * @author Frz
 * @since Revision 206
 * @version 1.0
 */
public class HexTool {

    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Turns a byte into a hexadecimal string.
     *
     * @param byteValue The byte to convert.
     * @return The hexadecimal representation of <code>byteValue</code>
     */
    public static final String toString(final byte byteValue) {
        final int tmp = byteValue << 8;
        char[] retstr = new char[]{HEX[(tmp >> 12) & 0x0F], HEX[(tmp >> 8) & 0x0F]};
        return String.valueOf(retstr);
    }

    /**
     * Turns a <code>org.apache.mina.common.ByteBuffer</code> into a hexadecimal
     * string.
     *
     * @param buf The <code>org.apache.mina.common.ByteBuffer</code> to convert.
     * @return The hexadecimal representation of <code>buf</code>
     */
    public static final String toString(ByteBuf buf) {
        byte[] array = new byte[buf.readableBytes()];
        buf.getBytes(0, array);
        String ret = toString(array);
        buf.setBytes(0, array);
        return ret;
    }

    /**
     * Turns an integer into a hexadecimal string.
     *
     * @param intValue The integer to transform.
     * @return The hexadecimal representation of <code>intValue</code>.
     */
    public static final String toString(final int intValue) {
        return Integer.toHexString(intValue);
    }

    /**
     * Turns an array of bytes into a hexadecimal string.
     *
     * @param bytes The bytes to convert.
     * @return The hexadecimal representation of <code>bytes</code>
     */
    public static final String toString(final byte[] bytes) {
        StringBuilder hexed = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            hexed.append(toString(bytes[i]));
            hexed.append(' ');
        }
        return hexed.substring(0, hexed.length() - 1);
    }

    /**
     * Turns an array of bytes into a ASCII string. Any non-printable characters
     * are replaced by a period (<code>.</code>)
     *
     * @param bytes The bytes to convert.
     * @return The ASCII hexadecimal representation of <code>bytes</code>
     */
    public static final String toStringFromAscii(final byte[] bytes) {
        byte[] ret = new byte[bytes.length];
        for (int x = 0; x < bytes.length; x++) {
            if (bytes[x] < 32 && bytes[x] >= 0) {
                ret[x] = '.';
            } else {
                int chr = (bytes[x]) & 0xFF;
                ret[x] = (byte) chr;
            }
        }
        try {
            String str = new String(ret, ServerConfig.ASCII);
            return str;
        } catch (Exception e) {
        }
        return "";
    }

    public static final String toPaddedStringFromAscii(final byte[] bytes) {
        final String str = toStringFromAscii(bytes);
        StringBuilder ret = new StringBuilder(str.length() * 3);
        for (int i = 0; i < str.length(); i++) {
            ret.append(str.charAt(i));
            ret.append("  ");
        }
        return ret.toString();
    }

    /**
     * Turns an hexadecimal string into a byte array.
     *
     * @param hex The string to convert.
     * @return The byte array representation of <code>hex</code>
     */
    public static byte[] getByteArrayFromHexString(final String hex) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nexti = 0;
        int nextb = 0;
        boolean highoc = true;
        outer:
        for (;;) {
            int number = -1;
            while (number == -1) {
                if (nexti == hex.length()) {
                    break outer;
                }
                char chr = hex.charAt(nexti);
                if (chr >= '0' && chr <= '9') {
                    number = chr - '0';
                } else if (chr >= 'a' && chr <= 'f') {
                    number = chr - 'a' + 10;
                } else if (chr >= 'A' && chr <= 'F') {
                    number = chr - 'A' + 10;
                } else {
                    number = -1;
                }
                nexti++;
            }
            if (highoc) {
                nextb = number << 4;
                highoc = false;
            } else {
                nextb |= number;
                highoc = true;
                baos.write(nextb);
            }
        }
        return baos.toByteArray();
    }

    public static final String getOpcodeToString(final int op) {
        return "0x" + StringUtil.getLeftPaddedStr(Integer.toHexString(op).toUpperCase(), '0', 4);
    }
}
