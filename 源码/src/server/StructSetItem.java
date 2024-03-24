/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StructSetItem {
    public byte completeCount, setItemID;
    public Map<Integer, SetItem> items = new LinkedHashMap<Integer, SetItem>();
    public List<Integer> itemIDs = new ArrayList<Integer>();

    public static class SetItem {
        public int incPDD, incMDD, incSTR, incDEX, incINT, incLUK, incACC, incPAD, incMAD, incSpeed, incMHP, incMMP, incMHPr, incMMPr, incAllStat,
		option1, option2, option1Level, option2Level;
    }

    public Map<Integer, SetItem> getItems() {
        return new LinkedHashMap<Integer, SetItem>(items);
    }
}
