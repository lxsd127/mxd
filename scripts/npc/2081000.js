
/**
	Chief Tatamo - Leafre(240000000)
**/

var section;
var temp;
var cost;
var count;
var menu = "";
var itemID = new Array(4000226, 4000229, 4000236, 4000237, 4000261, 4000231, 4000238, 4000239, 4000241, 4000242, 4000234, 4000232, 4000233, 4000235, 4000243);
var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        if (status > 2) {
            if (section == 0) {
                cm.sendOk("�����ؿ��ǡ�һ���������˛Q��������֪����");
            } else {
                cm.sendOk("����ɣ�Ȼ������֪����ěQ����");
            }
            cm.safeDispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendSimple("���������᣿\r\n#L0##bħ������#k#l\r\n#L1##bΪ����ľ����ж�#k#l\r\n#L2##b����ת����");
    } else if (status == 1) {
        section = selection;
        if (section == 0) {
            cm.sendSimple("��Ҫ�Ұ����㣿��\r\n#L0##b���������һЩ #t4031346#.#k#l");
        } else if (section == 1) {
            cm.sendNext("���õĽ�������Ǵ峤��ְ��������Ҫ������õĵ��ߡ�����Ϊ�˴�����׳�����ľ�帽���ռ����ĵ�����");
		} else if (section == 2) {
			cm.sendNext("����ҪΪ������ת��׼����?? ����Ҫ#t4031348#��");
        }
    } else if (status == 2) {
        if (section == 0) {
            cm.sendGetNumber("#b#t4031346##k ��Ҫ����ٸ�����", 1, 1, 99);
		} else if (section == 2) {
			if (cm.canHold()) {
			if (cm.haveItem(4031348)) {
				status = 3;
				cm.sendNext("���Ѿ�����#t4031348#����ô�F����Ҫ���ҵ�����ħ����#t4031348#�����ת����ĵ��ߡ�");
			} else {
				cm.sendNext("�뵽�����44����NPC����#t4031348#��");
				cm.safeDispose();
			}
			} else {
				cm.sendNext("������ı����Ƿ�������");
				cm.safeDispose();
			}
        } else {
            for (var i = 0; i < itemID.length; i++) {
                menu += "\r\n#L" + i + "##b#t" + itemID[i] + "##k#l";
            }
            cm.sendNext("������������ֵ����أ�" + menu);
            //cm.safeDispose();
        }
    } else if (status == 3) {
        if (section == 0) {
            if (selection == 0) {
                cm.sendOk("�Ҳ�������0����");
                cm.safeDispose();
            } else {
                temp = selection;
                cost = temp * 30000;
                cm.sendYesNo("��Ҫ�� #b" + temp + " #t4031346##k ���������� #b" + cost + " ���#k. ��ȷ��Ҫ���򣿣�?");
            }
        } else {
            temp = selection;
            if (cm.haveItem(itemID[temp])) {
                //cm.sendGetNumber("How many #b#t" + itemID[temp] + "#k's would you like to donate?\r\n#b< Owned : #c" + itemID[temp] + "# >#k", 0, 0, "#c" + itemID[temp] + "#");
                cm.sendGetNumber("��Ҫ����ٸ� #b#t" + itemID[temp] + "#k'�һ����ܺõĳ��͵ģ�", 1, 1, 999);
            } else {
                cm.sendNext("�Ҳ���Ϊ�����@���ߡ�");
                cm.safeDispose();
            }
        }
    } else if (status == 4) {
        if (section == 0) {
            if (cm.getMeso() < cost || !cm.canHold(4031346)) {
                cm.sendOk("��ȷ���Ƿ����㹻�Ľ�Һͱ�����λ��");
            } else {
                cm.sendOk("�ټ�~");
                cm.gainItem(4031346, temp);
                cm.gainMeso(-cost);
            }
            cm.safeDispose();
		} else if (section == 2) {
			if(!cm.canHold(4031860,2) || !cm.canHold(4031861,2)){
				cm.sendNext("The space doesnt enough .");
				cm.dispose();
				return;
			}
			if (cm.haveItem(4031348)) { //2nd check need item
				cm.gainItem(4031348,-1);
				cm.gainItem(4031860,1);
				cm.gainItem(4031861,1);
				cm.sendOk("��ϲ���Ѿ����#t4031860# x1 #t4031861# x1");
			} else {
				cm.sendOk("������]��#t4031348# 0.0");
			}
			cm.safeDispose();
        } else {
            count = selection;
            cm.sendYesNo("��ȷ���������� #b" + count + " #t" + itemID[temp] + "##k?");
        }
    } else if (status == 5) {
        if (count == 0 || !cm.haveItem(itemID[temp], count)) {
            cm.sendNext("��ȷ��������Ŀ�Ƿ��㹻��");
        } else {
            cm.gainItem(itemID[temp], -count);
            cm.sendNext("��л���������");
        }
        cm.safeDispose();
    }
}