var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        cm.removeAll(4001117);
        cm.removeAll(4001120);
        cm.removeAll(4001121);
        cm.removeAll(4001122);
        cm.sendSimple("��������ñ�ӿ���ͨ��ս��Ʒ��������Ŷ��\r\n#b#L0#��Ҫ�뿪�����ˡ�#l\r\n#L1#�һ���������ñ�ӡ�#l#k");
    } else if (status == 1) {
        if (selection == 0) {
            cm.gainItem(4001123, 1);
            cm.gainHdExp_PQ();
            cm.getPlayer().endPartyQuest(1204);
            cm.warp(251010404, 0);
        } else { //TODO JUMP
            if (cm.haveItem(1002574, 1)) {
                cm.sendOk("���Ѿ�ӵ�к�������ñ�ӡ�");
            } else if (cm.haveItem(1002573, 1)) {
                if (cm.haveItem(4001123, 20)) {
                    if (cm.canHold(1002574, 1)) {
                        cm.gainItem(1002573, -1);
                        cm.gainItem(4001123, -20);
                        cm.gainItem(1002574, 1);
                        cm.sendOk("�������Ǻ�������ñ�ӡ�");
                    } else {
                        cm.sendOk("����һ����ı����Ƿ��пո�");
                    }
                } else {
                    cm.sendOk("��û��20��ս��Ʒ��");
                }
            } else if (cm.haveItem(1002572, 1)) {
                if (cm.haveItem(4001123, 20)) {
                    if (cm.canHold(1002573, 1)) {
                        cm.gainItem(1002572, -1);
                        cm.gainItem(4001123, -20);
                        cm.gainItem(1002573, 1);
                        cm.sendOk("�������Ǻ�������ñ�ӡ�");
                    } else {
                        cm.sendOk("����һ����ı����Ƿ��пո�");
                    }
                } else {
                    cm.sendOk("��û��20��ս��Ʒ��");
                }
            } else if (cm.haveItem(1002571, 1)) {
                if (cm.haveItem(4001123, 20)) {
                    if (cm.canHold(1002572, 1)) {
                        cm.gainItem(1002571, -1);
                        cm.gainItem(4001123, -20);
                        cm.gainItem(1002572, 1);
                        cm.sendOk("�������Ǻ�������ñ�ӡ�");
                    } else {
                        cm.sendOk("����һ����ı����Ƿ��пո�");
                    }
                } else {
                    cm.sendOk("��û��20��ս��Ʒ��");
                }
            } else {
                if (cm.haveItem(4001123, 20)) {
                    if (cm.canHold(1002571, 1)) {
                        cm.gainItem(4001123, -20);
                        cm.gainItem(1002571, 1);
                        cm.sendOk("�������Ǻ�������ñ�ӡ�");
                    } else {
                        cm.sendOk("����һ����ı����Ƿ��пո�");
                    }
                } else {
                    cm.sendOk("��û��20��ս��Ʒ��");
                }
            }
        }
        cm.dispose();
    }
}