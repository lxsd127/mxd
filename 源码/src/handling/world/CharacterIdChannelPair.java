/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package handling.world;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class CharacterIdChannelPair implements Externalizable, Comparable<CharacterIdChannelPair> {
    private int charid = 0;
    private int channel = 1;

    public CharacterIdChannelPair() {
    }

    public CharacterIdChannelPair(int charid, int channel) {
        super();
        this.charid = charid;
        this.channel = channel;
    }

    public int getCharacterId() {
        return charid;
    }

    public int getChannel() {
        return channel;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        charid = in.readInt();
        channel = in.readByte();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(charid);
        out.writeByte(channel);
    }

    @Override
    public int compareTo(CharacterIdChannelPair o) {
        return channel - o.channel;
    }
}
