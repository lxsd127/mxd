/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package handling.world;

import client.MapleCharacter;
import handling.channel.ChannelServer;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class MapleMessenger implements Serializable {
    private static final long serialVersionUID = 9179541993413738569L;
    private MapleMessengerCharacter[] members = new MapleMessengerCharacter[3];
    private String[] silentLink = new String[3];
    private int id;

    public MapleMessenger(int id, MapleMessengerCharacter chrfor) {
        this.id = id;
        addMem(0, chrfor);
    }

    public void addMem(int pos, MapleMessengerCharacter chrfor) {
        if (members[pos] != null) {
            return;
        }
        members[pos] = chrfor;
    }

    public boolean containsMembers(MapleMessengerCharacter member) {
        return getPositionByName(member.getName()) < 4;
    }

    public void addMember(MapleMessengerCharacter member) {
        int position = getLowestPosition();
        if (position > -1 && position < 4) {
            addMem(position, member);
        }
    }

    public void removeMember(MapleMessengerCharacter member) {
        final int position = getPositionByName(member.getName());
        if (position > -1 && position < 4) {
            members[position] = null;
        }
    }

    public void silentRemoveMember(MapleMessengerCharacter member) {
        final int position = getPositionByName(member.getName());
        if (position > -1 && position < 4) {
            members[position] = null;
            silentLink[position] = member.getName();
        }
    }

    public void silentAddMember(MapleMessengerCharacter member) {
        for (int i = 0; i < silentLink.length; i++) {
            if (silentLink[i] != null && silentLink[i].equalsIgnoreCase(member.getName())) {
                addMem(i, member);
                silentLink[i] = null;
                return;
            }
        }
    }

    public void updateMember(MapleMessengerCharacter member) {
        for (int i = 0; i < members.length; i++) {
            MapleMessengerCharacter chr = members[i];
            if (chr != null && chr.equals(member)) {
                members[i] = null;
                addMem(i, member);
                return;
            }
        }
    }

    public int getLowestPosition() {
        for (int i = 0; i < members.length; i++) {
            if (members[i] == null) {
                return i;
            }
        }
        return 4;
    }

    public int getPositionByName(String name) {
        for (int i = 0; i < members.length; i++) {
            MapleMessengerCharacter messengerchar = members[i];
            if (messengerchar != null && messengerchar.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return 4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return 31 + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MapleMessenger other = (MapleMessenger) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    public Collection<MapleMessengerCharacter> getMembers() {
        return Arrays.asList(members);
    }
    public boolean isMonitored() {
        int ch = -1; 
        for (MapleMessengerCharacter m : members) {
	    if (m != null) {
                ch = World.Find.findChannel(m.getName());
                if (ch != -1) {
                    MapleCharacter mc = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(m.getName());
                    if (mc != null && mc.getClient() != null && mc.getClient().isMonitored()) {
                        return true;
		    }
                }
            }
        }
        return false;
    }
    
    public String getMemberNamesDEBUG() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < members.length;i++) {
	    if (members[i] != null) {
                sb.append(members[i].getName());
                if (i != members.length - 1)
                    sb.append(',');
	    }
        }
        return sb.toString();
    }
}

