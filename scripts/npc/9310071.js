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
        cm.sendSimple("��ã���������#rÿ�ս���#k#l��NPC��#b������ÿ��30�����ϵ���Ҷ�������ȡ500������ÿ��һ�Σ������Ƿ�Ҫ��ȡ��#k#l\r\n " +
                "#L0##r��ȡ500������ÿ��һ�Σ�#k#l\r\n");

    } else if (status == 1) {
        if (selection == 0) {
            var level = cm.getPlayer().getLevel();
            if (level < 30) {
                cm.sendOk("�ȼ�����30����");
                cm.dispose();
                return;
            }
            var marr = cm.getQuestRecord(160108);
            var data = marr.getCustomData();
            if (data == null) {
                marr.setCustomData("0");
                data = "0";
            }
            var dat = parseInt(marr.getCustomData());
            if (dat + 86400000 > cm.getCurrentTime()) {
                cm.sendOk("�Ѿ���ȡ���ˡ�");
                cm.dispose();
                return;
            } else {
                marr.setCustomData("" + cm.getCurrentTime());
                cm.gainBeans(500);
                cm.sendOk("��ȡ�ɹ���");
                cm.dispose();
                return;
            }
        }
    }
}
