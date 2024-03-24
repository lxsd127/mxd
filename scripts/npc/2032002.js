/* 
 * 
 * Adobis's Mission I: Unknown Dead Mine (280010000)
 * 
 * Zakum PQ NPC (the one and only)
 */

var status = -1;
var selectedType;
var scrolls;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }

    if (status == 0) {
	cm.sendSimple("��ô�������Ѽ�������#b\r\n#L0#������Ӧ����ʲô��#l\r\n#L1#�Ѿ��Ѽ�������Ʒ��#l\r\n#L2#��Ҫ�뿪���#l");
    } else if (status == 1) {
	selectedType = selection;
	if (selection == 0) {
	    cm.sendNext("�׳գ�����Ҫ�������Ŀ����ʲô����Ҫ�ռ�ʲô����֪������������ʲô����һ��ȥ�ɣ�")
	    cm.safeDispose();
	} else if (selection == 1) {
	    if (!cm.haveItem(4001018)) { //documents
		cm.sendNext("ƭ��ѽ���������û���")
		cm.safeDispose();
	    } else {
		if (!cm.haveItem(4001015, 30)) { //documents
		    cm.sendYesNo("So, you brought the fire ore with you? In that case, I can give you and your party a piece of it that should be more than enough to make the core of Zakum. Make sure your whole party has room in their inventory before proceeding.");
		    scrolls = false;
		} else {
		    cm.sendYesNo("So, you brought the fire ore and the documents with you? In that case, I can give you and your party a piece of it that should be more than enough to make the core of Zakum. As well, since you brought the documents with you, I can also give you a special item which will bring you to the mine's entrance at any time. Make sure your whole party has room in their inventory before proceeding.");
		    scrolls = true;
		}
	    }
	} else if (selection == 2) {
	    cm.sendYesNo("��ȷ��Ҫ�˳������������ӳ���һ�����뿪��ӣ���ô����������޷�������ȥ���Ƿ�����˳���")
	}
    } else if (status == 2) {
	var eim = cm.getEventInstance();
	if (selectedType == 1) {
				
	    cm.gainItem(4001018, -1);
	    if (scrolls) {
		cm.gainItem(4001015, -30);
	    }
	    //give items/exp
	    cm.givePartyItems(4031061, 1);
	    if (scrolls) {
		cm.givePartyItems(2030007, 5);
		cm.givePartyExp(20000);
	    } else {
		cm.givePartyExp(12000);
	    }
				
	    //clear PQ

	    if (eim != null) {
	    	eim.finishPQ();
	    }
	    cm.dispose();
	} else if (selectedType == 2) {
	if (eim != null) {
	    if (cm.isLeader())
		eim.disbandParty();
	    else
		eim.leftParty(cm.getChar());
	} else {
		cm.warp(280090000, 0);
	}
	    cm.dispose();
	}
    }
}