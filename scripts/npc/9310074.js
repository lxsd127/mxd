function start() {
    var Editing = false //false �_ʼ
    if (Editing) {
        cm.sendOk("�S����");
        cm.dispose();
        return;
    }
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else if (mode == 0) {
        status--;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendSimple("�˴��һ� #b- ��Ҷ -#k#l #r1��1�һ���#k#l\r\n " +
                "#r��Ҷ#k#l#i4001126##r��÷�ʽ:#k#l\r\n " +
                "#r���κι����м��ʵ���#k#l\r\n " +
                "\r\n " +
                "#b��ǰ���:#k#l#r" + cm.getPlayer().getCSPoints(1) + "#k#l\r\n " +
                "#b��ǰ��Ҷ:#k#l#r" + cm.haveItemQuantity(4001126) + "#k#l\r\n " +
                "#L0##b�÷�Ҷ�һ����#k#r����1��1#k#l\r\n");

    } else if (status == 1) {
        if (selection == 0) {
            cm.sendGetNumber("�����÷�Ҷ�һ����ٵ��", 1, 1, 1000);
        }
    } else if (status == 2) {
        qty = selection;
        if (qty <= 0) {
            cm.sendOk("��������");
            cm.dispose();
            return;
        }
        if (qty > 1000) {
            cm.sendOk("��������");
            cm.dispose();
            return;
        }
        if (!cm.haveItem(4001126, qty)) {
            cm.sendOk("�������㣡");
            cm.dispose();
            return;
        }
        cm.gainItem(4001126, -qty);
        cm.getPlayer().modifyCSPoints(1, qty);
        cm.sendOk("�һ�" + qty + "���ɹ�");
        cm.dispose();
        return;
    }
}
