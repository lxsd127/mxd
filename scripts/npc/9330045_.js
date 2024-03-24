/* Kedrick
	Fishking King NPC
*/

var status = -1;
var sel;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }

    if (status == 0) {
	cm.sendSimple("������ʲô?\n\r #b#L0#���뵽���㳡#v4220084##l \n\r #L1#������ͨ���#v2300000##l \n\r #L2#�����������#v3011000##l \n\r #L3#ʹ�������ͷ#v5350000##l \n\r #L4#����˵������#v4161004##l \n\r");//#L5##i2210006:#ó��500�� (���������� [���ޣ�30��])#l
    } else if (status == 1) {
	sel = selection;
	if (sel == 0) {
	    if (cm.haveItem(5340000) || cm.haveItem(5340001)) {
		if (cm.haveItem(3011000)) {
		    cm.saveLocation("FISHING");
		    cm.warp(741000200);
		    cm.dispose();
		} else {
		    cm.sendNext("������е����β��ܵ���!");
		    cm.safeDispose();
		}
	    } else {
		cm.sendNext("������е���Ͳ��ܵ���!");
		cm.safeDispose();
	    }
	} else if (sel == 1) {
	    cm.sendYesNo("��Ҫ5000��ҹ���120����������빺��?");
	} else if (sel == 2) {
	    if (cm.haveItem(3011000)) {
		cm.sendNext("���Ѿ���һ�ѵ������ˡ�ÿ����ɫ��ֻ����1��������.");
	    } else {
		if (cm.canHold(3011000) && cm.getMeso() >= 50000) {
		    cm.gainMeso(-50000);
		    cm.gainItem(3011000, 1);
		    cm.sendNext("���ֵ���~");
		} else {
		    cm.sendOk("�����Ƿ����㹻�Ľ�һ��㹻�ı�����.");
		}
	    }
	    cm.safeDispose();
	} else if (sel == 3) {
	    if (cm.canHold(2300001,120) && cm.haveItem(5350000,1)) {
		if (!cm.haveItem(2300001)) {
		    cm.gainItem(2300001, 120);
		    cm.gainItem(5350000,-1);
		    cm.sendNext("���ֵ���~");
		} else {
		    cm.sendNext("���Ѿ����˵�����ն�.");
		}
	    } else {
		cm.sendOk("���鱳���ռ����û�и߼������ͷ����̳ǹ���.");
	    }
	    cm.safeDispose();
	} else if (sel == 4) {
	    cm.sendOk("����Ҫ10�����ϣ�����͡���������ν�����㳡���㽫��ÿ1����һ�Ρ����泡��¼�����������㲶׽��¼!");
	    cm.safeDispose();
	} else if (sel == 5) {
	    if (cm.haveItem(4000518, 500)) {
		if (cm.canHold(1142146)) {
		    cm.gainItem(4000518, -500);
		    cm.gainItemPeriod(1142146, 1, 30);
		    cm.sendOk("Ŷ���Ҳ���һ�����˲�����Щ���ڵ�������㡣�������������� #b������ѫ��#k!")
		} else {
		    cm.sendOk("�����Ƿ����㹻�����ռ�.");
		}
	    } else {
		cm.sendOk("����� 500 #i4000518:# �ƽ��㵰��ȡһ������������!")
	    }
	    cm.safeDispose();
	}
    } else if (status == 2) {
	if (sel == 1) {
	    if (cm.canHold(2300000,120) && cm.getMeso() >= 5000) {
		if (!cm.haveItem(2300000)) {
		    cm.gainMeso(-5000);
		    cm.gainItem(2300000, 120);
		    cm.sendNext("���ֵ���~");
		} else {
		    cm.sendNext("���Ѿ����˵�����ն�.");
		}
	    } else {
		cm.sendOk("�����Ƿ����㹻�����ռ�.");
	    }
	    cm.safeDispose();
	}
    }
}