/* RED 1st impact
    Rupi
    Made by Daenerys
*/

var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    cm.sendNext("�㻹��һЩδ��ɵ��£��������߳�ȥ֮ǰ�����������һ��ƣ�������飬����һ�����������.");
            cm.dispose();
        status--;
    }
    if (status == 0) {
	    cm.sendYesNo("��û�б����Ҫ�����ţ������ȥ������ǵĻ����ҿ��԰����ͻ�ȥ��������ô��ģ������ȥ��?");
    } else if (status == 1) {
	    cm.warp(101000000);
		cm.dispose();
    }
}