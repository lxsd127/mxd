/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server;

import client.inventory.Equip;
import client.inventory.EquipAdditions;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import tools.Pair;
import javax.swing.*;
import java.awt.*;

public class ItemInformation {
    public List<Integer> scrollReqs = null, questItems = null, incSkill = null;
    public short slotMax, itemMakeLevel;
    public Equip eq = null;
    public Map<String, Integer> equipStats;
    public double price = 0.0;
    public int itemId, wholePrice, monsterBook, stateChange, meso, questId, totalprob, replaceItem, mob, cardSet, create, flag;
    public String name, desc, msg, replaceMsg, afterImage;
    public byte karmaEnabled;
    public List<StructRewardItem> rewardItems = null;
    public EnumMap<EquipAdditions, Pair<Integer, Integer>> equipAdditions = null;
    public Map<Integer, Map<String, Integer>> equipIncs = null;
    public ImageIcon imageIcon; //用于存储图片
    public JLabel imageLabel; //使用JLabel显示图片
}
