/*
	Hotel Receptionist - Sleepywood Hotel(105040400)
*/

var status = 0;
var regcost = 5000;
var vipcost = 10000;
var tempvar;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
	status++; if (mode == 0 && status == 1) {
	cm.dispose();
	return;
    } if (mode == 0 && status == 2) {
	cm.sendNext("��ϸ�������!");
	cm.dispose();
	return;
    }
    if (status == 0) {
	cm.sendNext("��ӭ��������֮���ùݡ����Ǿ���Ϊ���ṩ��õķ�����������ˣ���������Ϣһ����Σ�");
    }
    if (status == 1) {
	cm.sendSimple("�����ṩ���ַ��䣬��ѡ������Ҫ��\r\n#b#L0#��ͨɣ�÷�#l\r\n#L1#�߼�ɣ�÷�#l");
    }
    if (status == 2) {
	tempvar = selection;
	if (tempvar == 0) {
	    cm.sendYesNo("��ѡ������ͨɣ�÷������HP��MP��ظ��úܿ죬��Ҳ���������湺����Ʒ����ȷ��Ҫ������?����#r���5000��");
	}
	if (tempvar == 1) {
	    cm.sendYesNo("��ѡ���˸߼�ɣ�÷������HP��MP���һ��ɣ���һظ��ø��죬Ҳ�����������ҵ��������Ʒ����ȷ��Ҫ����������#r���10000��");
	}
    }
    if (status == 3) {
	if (tempvar == 0) {
	    if (cm.getMeso() >= regcost) {
		cm.warp(105040401);
		cm.gainMeso(-regcost);
	    } else {
		cm.sendNext("�ܱ�Ǹ�����������ƺ�û���㹻�Ľ�ҡ�������Ҫ��" + regcost + " ��Ҳ���ȥ��ͨɣ�÷���");
	    }
	} if (tempvar == 1) {
	    if (cm.getMeso() >= vipcost) {
		cm.warp(105040402);
		cm.gainMeso(-vipcost);
	    } else {
		cm.sendNext("�ܱ�Ǹ�����������ƺ�û���㹻�Ľ�ҡ�������Ҫ��" + regcost + "��Ҳ���ȥ�߼�ɣ�÷���");
	    }
	}
	cm.dispose();
    }
}
