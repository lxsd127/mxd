/* Author: Xterminator
	NPC Name: 		Peter
	Map(s): 		Maple Road: Entrance - Mushroom Town Training Camp (3)
	Description: 	Takes you out of Entrace of Mushroom Town Training Camp
*/
var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	cm.sendNext("���Ѿ���������е�ѵ�����ɵú�.���ƺ��Ѿ�׼���ÿ�ʼ���ó��ˣ��õģ��һ������Ƶ���һ���ط�.");
    } else if (status == 1) {
	cm.sendNextPrev("�����ס��һ�����뿪���������һ����������Ĵ�ׯ���ðɣ��ټ�!");
    } else if (status == 2) {
	cm.warp(40000, 0);
	cm.gainExp(3);
	cm.dispose();
    }
}