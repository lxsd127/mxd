/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package client;

import database.DBConPool;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import tools.FileoutputUtil;
import tools.Pair;
import tools.data.MaplePacketLittleEndianWriter;

public class MapleKeyLayout implements Serializable {

    private static final long serialVersionUID = 9179541993413738569L;
    private boolean changed = false;
    private Map<Integer, Pair<Byte, Integer>> keymap;

    public MapleKeyLayout() {
        keymap = new HashMap<Integer, Pair<Byte, Integer>>();
    }

    public MapleKeyLayout(Map<Integer, Pair<Byte, Integer>> keys) {
        keymap = keys;
    }

    public final Map<Integer, Pair<Byte, Integer>> Layout() {
        changed = true;
        return keymap;
    }

    public final void unchanged() {
        changed = false;
    }

    public final void writeData(final MaplePacketLittleEndianWriter mplew) {
        mplew.write(keymap.isEmpty() ? 1 : 0);
        if (keymap.isEmpty()) {
            return;
        }
        Pair<Byte, Integer> binding;
        for (int x = 0; x < 89; x++) {
            binding = keymap.get(Integer.valueOf(x));
            if (binding != null) {
                mplew.write(binding.getLeft());
                mplew.writeInt(binding.getRight());
            } else {
                mplew.write(0);
                mplew.writeInt(0);
            }
        }
    }

    public final void saveKeys(final int charid, Connection con) throws SQLException {
        if (!changed) {
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM keymap WHERE characterid = ?");
            ps.setInt(1, charid);
            ps.execute();
            ps.close();
            if (keymap.isEmpty()) {
                return;
            }
            boolean first = true;
            StringBuilder query = new StringBuilder();

            for (Entry<Integer, Pair<Byte, Integer>> keybinding : keymap.entrySet()) {
                if (first) {
                    first = false;
                    query.append("INSERT INTO keymap VALUES (");
                } else {
                    query.append(",(");
                }
                query.append("DEFAULT,");
                query.append(charid).append(",");
                query.append(keybinding.getKey().intValue()).append(",");
                query.append(keybinding.getValue().getLeft().byteValue()).append(",");
                query.append(keybinding.getValue().getRight().intValue()).append(")");
            }
            ps = con.prepareStatement(query.toString());
            ps.execute();
            ps.close();
        } catch (Exception se) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", se);
        }
    }
}
