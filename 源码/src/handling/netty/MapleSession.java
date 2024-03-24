/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handling.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.net.SocketAddress;

/**
 *
 * @author wubin
 */
public class MapleSession {

    private Channel channel;

    public MapleSession(Channel session) {
        this.channel = session;
    }

    public ChannelFuture write(Object o) {
        return channel.writeAndFlush(o);
    }

    public void close() {
        channel.close();
    }

    public SocketAddress getRemoteAddress() {
        return channel.remoteAddress();
    }

    public boolean isActive() {
        return channel.isActive();
    }

    public boolean isOpen() {
        return channel.isOpen();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
