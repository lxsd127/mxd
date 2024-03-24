/*  NPC : �𼪠�
 ������ 4�D �΄��_��
 �؈D���a (240010501)
 */

var status = -1;
var pass = false;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;

    if (status == 0) {
        if (!(cm.getJob() == 311 || cm.getJob() == 321)) {
            cm.sendOk("Ϊʲô��Ҫ����??��������Ҫ���ҹ���ʲô����??");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getLevel() < 120) {
            cm.sendOk("��ȼ���δ����120��.");
            cm.dispose();
            return;
        } else {
            if (cm.getQuestStatus(6924) == 2) {
                pass = true;
            }
            if (cm.getJob() == 311) {
                cm.sendSimple("��ϲ�����ʸ�4ת. \r\n��������4ת��??\r\n#b#L0#�����Ϊ����.#l\r\n#b#L1#������һ��...#l");
            } else if (cm.getJob() == 321) {
                cm.sendSimple("��ϲ�����ʸ�4ת. \r\n��������4ת��??\r\n#b#L0#�����Ϊ������.#l\r\n#b#L1#������һ��...#l");
            } else {
                cm.sendOk("�ðɣ���������Ҫ4ת�鷳�������ҡ�");
                cm.dispose();
                return;
            }
        }
    } else if (status == 1) {
        if (selection == 1) {
            cm.sendOk("�ðɣ���������Ҫ4ת�鷳�������ҡ�");
            cm.dispose();
            return;
        }
        if (cm.getPlayerStat("RSP") > (cm.getPlayerStat("LVL") - 120) * 3) {
            cm.sendOk("��ļ��ܵ�����û����..");
            cm.dispose();
            return;
        }
        if (pass) {
            cm.sendNext("������ת��");
        } else {
            if (!cm.haveItem(4031860) || !cm.haveItem(4031861)) {
                cm.sendOk("����Ҫ#t4031860# x1 #t4031861# x1��");
                cm.dispose();
                return;
            } else {
                cm.sendNext("������ת��");
            }
        }
    } else if (status == 2) {
        if (cm.canHold(2280003)) {
            cm.gainItem(2280003, 1);
            if (cm.getJob() == 311) {
                cm.changeJob(312);
                cm.teachSkill(3120005, 0, 10);
                cm.teachSkill(3121007, 0, 10);
                cm.teachSkill(3121002, 0, 10);
                cm.gainItem(4031860, -1);
                cm.gainItem(4031861, -1);
                cm.sendNext("��ϲ���Ϊ #b����#k.������һЩ����С����^^");
            } else {
                cm.changeJob(322);
                cm.teachSkill(3221006, 0, 10);
                cm.teachSkill(3220004, 0, 10);
                cm.teachSkill(3221002, 0, 10);
                cm.gainItem(4031860, -1);
                cm.gainItem(4031861, -1);
                cm.sendNext("��ϲ���Ϊ #b������#k.������һЩ����С����^^");
            }
        } else {
            cm.sendOk("��ı���û�ж������λ��");
            cm.dispose();
            return;
        }

    } else if (status == 3) {
        cm.sendNext("��Ҫ������һ��ȡ�������Ŭ����");
    } else if (status == 4) {
        cm.sendNextPrev("������Ϊ�١�");
        cm.dispose();
    }
}