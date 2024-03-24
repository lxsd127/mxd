/* Author: Xterminator
	NPC Name: 		VIP Cab
	Map(s): 		Victoria Road : Lith Harbor (104000000)
	Description: 		Takes you to Ant Tunnel Park
*/
var status = 0;
var cost;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 1 && mode == 0) {
	cm.sendNext("����������ﻹ�����Ư���ľ��㣬�������ȥ���϶��㳡����ӭ��ʱʹ�����ǵĳ��⳵����");
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	cm.sendNext("����~���������Ǽ����⳵����ͬ�ڴ���֮��������һ����а����Ǹ����ṩ���߼��ķ�����˳����е����ֻҪ֧��#b10,000���#k�����ǾͻὫ����ȫѸ�ٵ��͵�#b���϶��㳡#k�����ǵȼ�̫�ͽ�ȥ���Σ���Ƿ�Ҫ��ȥ�أ�");
    } else if (status == 1) {
	var job = cm.getJob();
	if (job == 0 || job == 2000 || job == 1000) {
	    cm.sendYesNo("���ֵĻ��۸����#b9��#k�Żݡ����϶��㳡��λ�ڽ������м���Թ������������24Сʱ�ŵ������Ƿ�Ҫ��#b1,000���#k��ȥ���϶��㳡��");
	    cost = 1000;
	} else {
	    cm.sendYesNo("���϶��㳡��λ�ڽ������м���Թ������������24Сʱ�ŵ������Ƿ�Ҫ��#b10,000���#k��ȥ���϶��㳡��");
	    cost = 10000;
	}
    } else if (status == 2) {
	if (cm.getMeso() < cost) {
	    cm.sendNext("�����û���㹻�Ľ�ң������Ļ����Ҳ���Ϊ�����");
	} else {
	    cm.gainMeso(-cost);
	    cm.warp(105070001, 0);
	}
	cm.dispose();
    }
}