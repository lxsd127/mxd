var status = 0;
var banMap = Array(109080000, 109080010, 109040000, 109030001, 109060000, 109010000);

function start() {
    cm.sendNext("�� ���� #b��#k. ���ڵȴ��ҵ��ֵ� #b���_#k. ����ԓ�F�����@�e...");
}


function action(mode, type, selection) {
    for (var i = 0; i < banMap.length; i++) {
        if (cm.getPlayer().getMapId() == banMap[i]) {
            cm.sendOk("�֣���Ҫ͵�ܳ�ʺȥ�ɣ�");
            cm.dispose();
        }
    }
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 2 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 1) {
            cm.sendNextPrev("��......��ԓ���N�k�����λ�ӌ��_ʼ���ܿ�......�ܶ���ȥ���c�@헻�ӣ������҂���ÿ��c��......");
        } else if (status == 2) {
            cm.sendSimple("��... ���ʲ�N�������ߣ������ҵ��ֵܕ��c������...\r\n#L0##e1.#n#b ʲ�N�ӵĻ�Ӄ���??#k#l\r\n#L1##e2.#n#b ��B��Ӄ���׌���J�R..#k#l\r\n#L2##e3.#n#b ���ˣ��҂��߰ɣ�#k#l");
        } else if (status == 3) {
            if (selection == 0) {
                cm.sendNext("���б��³�����֮�ȭh��cף�����L�꣡ȫ��C�ƌ��e���@ϲGM���������������g��������������_ֺ���K�_�����c��ӵ�����һ���邥��Ī�Ʒ��");
                cm.dispose();
            } else if (selection == 1) {
                cm.sendSimple("���S���ӹلt���@�����������_ʼ���֮ǰ������...�x�������˽�Ļ��.. #b\r\n#L0# ���K��#l\r\n#L1# �K�O����#l\r\n#L2# �Lѩ��#l\r\n#L3# �����#l\r\n#L6# ��ƿ�w#l\r\n#L4# �Ƿ��}���#l\r\n#L5# ����#l#k");
            } else if (selection == 2) {
                if (!cm.canHold()) {
                    cm.sendNext("Ո�_�J�Ƿ������п�λ��");
                } else if (cm.getChannelServer().getEvent() > -1) {
                    if (cm.haveItem(4031017)) {
                        cm.removeAll(4031017);
                    }
                    if (cm.getPlayer().getLevel() >= 10) {
                        cm.saveReturnLocation("EVENT");
                        cm.getPlayer().setChalkboard(null);
                        cm.warp(cm.getChannelServer().getEvent(), cm.getChannelServer().getEvent() == 109080000 || cm.getChannelServer().getEvent() == 109080010 ? 0 : "join00");
                    } else {
                        cm.sendOk("����_��10������ʹ�á�");
                        cm.dispose();
                        return;
                    }
                } else {
                    cm.sendNext("�����δ�_�ţ�Ո�_�J�Ƿ�������24С�r�ȅ����^һ����ӡ�Ո������ԇ��");
                }
                cm.dispose();
            }
        } else if (status == 4) {
            if (selection == 0) {
                cm.sendNext("#b[���K��]#k �Լ�#e#rGoogle#k!");
                cm.dispose();
            } else if (selection == 1) {
                cm.sendNext("#b[�K�O����] �Լ�#e#rGoogle#k!");
                cm.dispose();
            } else if (selection == 2) {
                cm.sendNext("#b[�Lѩ��]#k �Լ�#e#rGoogle#k!");
                cm.dispose();
            } else if (selection == 3) {
                cm.sendNext("#b[�����]#k �Լ�#e#rGoogle#k!");
                cm.dispose();
            } else if (selection == 6) {
                cm.sendNext("#b[��ƿ�w]#k �Լ�#e#rGoogle#k!");
                cm.dispose();
            } else if (selection == 4) {
                cm.sendNext("#b[�Ƿ��}���]#k �Լ�#e#rGoogle#k!");
                cm.dispose();
            } else if (selection == 5) {
                cm.sendNext("#b[����]#k �Լ�#e#rGoogle#k!");
                cm.dispose();
            }
        }
    }
}