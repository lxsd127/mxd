/* 	Aramia
 * 	Henesys fireworks NPC
 */

var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (cm.getClient().getChannel() != 1) {
        cm.sendNext("�ûֻ����Ƶ��1����.");
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendNext("��~�Ұ����֡���֪����������ڣ����������ռ�Ȼ����ң����ǿ�������һ���̻���������еĻ�ҩͰ����ӹ������ϻ�û�ҩͰ��");
    } else if (status == 1) {
        cm.sendSimple("ÿ������ռ�����Ļ�ҩͰ���ҿ������������̻�! \n\r #b#L0# ������ҩͰ��#l#k \n\r #b#L1# �鿴�ռ��Ļ�ҩͰ�Ľ��ȡ�#l#k");
    } else if (status == 2) {
        if (selection == 1) {
            cm.sendNext("��ҩͰ����״̬ \n\r #B" + cm.getKegs() + "# \n\r ��������ռ��������ǿ��Կ�ʼ���̻�...");
            cm.safeDispose();
        } else if (selection == 0) {
            cm.sendGetNumber("������˻�ҩͰ����ô������� #b��ҩͰ#k ���С��һ���һ���õı�����Ը����Ҷ���? \n\r #b< ������ҩͰ�� : #c4001128# >#k", 0, 0, 10000);
        }
    } else if (status == 3) {
        var num = selection;
        if (num == 0) {
            cm.sendOk("����Ҫ��ҩͰ��ʼ�������̻�....\r\n ������һ�룬����˵����");
        } else if (cm.haveItem(4001128, num)) {
            cm.gainItem(4001128, -num);
            cm.giveKegs(num);
            cm.sendOk("�����˸��ҵ���õ����ǵĻ�ҩͰ��");
        } else {
            cm.sendOk("����Ҫ��ҩͰ��ʼ�������̻�....\r\n ������һ�룬����˵����");
        }
        cm.safeDispose();
    }
}