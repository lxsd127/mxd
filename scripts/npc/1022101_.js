/* RED 1st impact
 Rooney
 Made by Daenerys
 */
var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else
    if (status == 0) {
        cm.sendNext("��������������ĺ�æ���������·���ҵ���һЩ����ʱ�䣬��ô�����ң�������鵽һ�����������ط���ʥ��С��!");
        cm.dispose();
        status--;
    }
    if (status == 0) {
        cm.sendYesNo("���뿴���ܶ�ð�������Ҹ������������߰� !");
    } else if (status == 1) {
        cm.warp(209000000, 16);
        cm.dispose();
    }
}