/* Author: Xterminator
	NPC Name: 		Regular Cab
	Map(s): 		Victoria Road : Kerning City (103000000)
	Description: 		Kerning City Cab
*/
var status = 0;
var maps = Array(104000000, 102000000, 101000000, 100000000, 120000000);
var cost = Array(1000, 1000, 1000, 1000, 1000, 1000);
var costBeginner = Array(100, 100, 100, 100, 100, 100);
var show;
var sCost;
var selectedMap = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 1 && mode == 0) {
	cm.dispose();
	return;
    } else if (status >= 2 && mode == 0) {
	cm.sendNext("����������ﻹ�����Ư���ľ��㣬�������ȥ�����ط�����ӭ��ʱʹ�����ǵĳ��⳵����");
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	cm.sendNext("����~���������ִ��а͡����벻���ֿ���ְ�ȫ�ĵ��������ط�ȥ����ô��ʹ�����ǵĳ��⳵�ɡ��������Ͻ����͵�����ȥ�ĵط����۸�ܱ���Ŷ��");
    } else if (status == 1) {
	var job = cm.getJob();
	if (job == 0 || job == 1000 || job == 2000) {
	    var selStr = "���ֵĻ��۸����#b9��#k�Żݡ���ѡ�����Ŀ�ĵذɡ�#b";
	    for (var i = 0; i < maps.length; i++) {
		selStr += "\r\n#L" + i + "##m" + maps[i] + "# (" + costBeginner[i] + " ���)#l";
	    }
	} else {
	    var selStr = "��ѡ�����Ŀ�ĵذɡ�����Ŀ�ĵز�ͬ������Ҳ������ͬ��#b";
	    for (var i = 0; i < maps.length; i++) {
		selStr += "\r\n#L" + i + "##m" + maps[i] + "# (" + cost[i] + " ���)#l";
	    }
	}
	cm.sendSimple(selStr);
    } else if (status == 2) {
	var job = cm.getJob();
	if (job == 0 || job == 1000 || job == 2000) {
	    sCost = costBeginner[selection];
	    show = costBeginner[selection];
	} else {
	    sCost = cost[selection];
	    show = cost[selection];
	}
	cm.sendYesNo("����������������Ѿ����������ȷ��Ҫȥ #b#m" + maps[selection] + "##k�� Ʊ���� #b" + show + " ���#k.");
	selectedMap = selection;
    } else if (status == 3) {
	if (cm.getMeso() < sCost) {
	    cm.sendNext("�����û���㹻�Ľ�ң������Ļ����Ҳ���Ϊ�����");
	} else {
	    cm.gainMeso(-sCost);
	    cm.warp(maps[selectedMap]);
	}
	cm.dispose();
    }
}