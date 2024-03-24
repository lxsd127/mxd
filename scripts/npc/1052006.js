/**
	Jake - Victoria Road : Subway Ticketing Booth (103000100)
**/

var meso = new Array(500, 1200, 2000);
var item = new Array(4031036, 4031037, 4031038);
var selector;
var menu = "";

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
	cm.dispose();
	return;
    } else if (status == 1 && mode == 0) {
	cm.sendNext("����Խ���,һ��������Ʊ,�������ƺ���ϲ������Խ�������.�й�����豸,������̫�����������,������ѵ���Լ���׼����Ȼ�������");
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	if (cm.getPlayerStat("LVL") <= 19) {
	    cm.sendNext("����Խ���,һ��������Ʊ,�������ƺ���ϲ������Խ�������.�й�����豸,������̫�����������,������ѵ���Լ���׼����Ȼ�����.");
	    cm.dispose();
	} else {
	    for(var x=0; x < 3; x++) {
		if (cm.getPlayerStat("LVL") >= 20 && cm.getPlayerStat("LVL") <= 29) {
		    menu += "\r\n#L" + x + "##b���� B" + (x+1) + "#k#l";
		    break;
		} else if (cm.getPlayerStat("LVL") >= 30 && cm.getPlayerStat("LVL") <= 39 && x < 2) {
		    menu += "\r\n#L" + x + "##b���� B" + (x+1) + "#k#l";
		} else {
		    menu += "\r\n#L" + x + "##b���� B" + (x+1) + "#k#l";
		}
	    }
	    cm.sendSimple("����빺��Ʊ���ܽ���,һ���㹺����,�����ͨ���ұߵĴ��Ž������.������ʲô?" + menu);
	}
    } else if (status == 1) {
	selector = selection;
	selection += 1;
	cm.sendYesNo("���������Ʊ�� #b���� B" + selection + "#k? ���Ứ���� " + meso[selector] + " ���. �ڹ���֮ǰ����ȷ���㱳�����㹻�Ŀռ䡣");
    } else if (status == 2) {
	if (cm.getMeso() < meso[selector]) {
	    cm.sendNext("���Ƿ����㹻�Ľ�ҡ�");
	    cm.dispose();
	} else {
	    if (selector == 0) {
		cm.sendNext("���������Ʊ�ڲ���Ʊ������˵1����һЩ������Ʒ��������ô������壬���еĵط���������ˡ�ף����ˡ�");
	    } else if (selector == 1) {
		cm.sendNext("���������Ʊ���в���Ʊ������˵2�����ѵõģ�������Ʒ��������ô������壬���еĵط���������ˡ���ע�ⰲȫ��");
	    } else {
		cm.sendNext("���������Ʊ���в���Ʊ������˵3���зǳ������ģ��ǳ�������Ŀ��������ô������壬���еĵط���������ˡ�ף����ˡ�");
	    }
	    cm.gainMeso(-meso[selector]);
	    cm.gainItem(item[selector], 1);
	    cm.dispose();
	}
    }
}