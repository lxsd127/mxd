/* RED 1st impact
    Vasily (Maple Return skill)
    Made by Daenerys
*/
var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    cm.sendNext("�㻹��һЩ������?");
            cm.dispose();
        status--;
    }
    if (status == 0) {
	    cm.sendYesNo("�ô�����������ۣ��������Ҫ�������н��������飬������ȴ���á���׼���ó�������?");
	} else if (status == 1) {
	    cm.warp(104000000,0);
		cm.dispose();
    }
  }