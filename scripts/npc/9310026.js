var status = -1;
//�߼����ְٱ���
var itemId = 5220040;


function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode <= 0) {
        cm.dispose();
    } else {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (0 == status) {
            cm.sendNext("��ӭʹ�ø߼����ְٱ��䣬���������Ӧ�о���Ŷ��\nʹ��ǰ��ȷ�����ı����Ƿ����㹻�Ŀռ䣡");
        } else if (1 == status) {
            cm.sendYesNo("ȷ��ʹ��#v " + itemId + "#����һ����Ϸ��");
        } else if (2 == status) { //�齱
            if (!cm.haveItem(itemId)) {
                cm.sendOk("�Բ�����û��#v " + itemId + "#�����顣");
                cm.dispose();
                return;
            }
            var result = cm.seniorBox();

            //��;ȫ���㲥
            //cm.info(typeof (result.get(2).intValue()));
            if (cm.seniorBoxlevel() == 3 || cm.seniorBoxlevel() == 4) {
                //cm.info("yes");
                cm.gainItem(5220040, -1);
                cm.gainGachaponItema(cm.seniorBoxitemId(), 1, "�߼����ְٱ���");
                cm.sendOk("��ϲ����#v " + cm.seniorBoxitemId() + "#���������̫����,����Ŭ����");
            } else {
                //cm.info("no");
                cm.gainItem(5220040, -1);
                cm.gainItem(cm.seniorBoxitemId(), 1);
                cm.sendOk("��ϲ����#v " + cm.seniorBoxitemId() + "#����Ҫ�����¸��󽱵����㣡");
            }

            cm.dispose();
        } else {
            cm.dispose();
        }
    }
}
