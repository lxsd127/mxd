var equiplist;
var cashlist;
var str = "";
var isok;
var modea = 0;

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
        cm.sendSimple("��ã�����ɾ��װ���������������ߵ�NPC��\r\n" +
                "#L0#ɾ��װ��������#l\r\n" +
                "#L1#ɾ������������#l\r\n");

    } else if (status == 1) {
        if (selection == 0) {
            equiplist = cm.getEquiplist();
            if (equiplist != null) {
                for (var i = 0; i < equiplist.size(); i++) {
                    str += "#L" + i + "##i" + equiplist.get(i).getItemId() + "##t" + equiplist.get(i).getItemId() + "##k\r\n";
                }
            }
            cm.sendSimple("��ѡ����Ҫ�������·������������·����޷��һء�����ж����ͬ���·��������ȶ���������ǰ��Ķ�����\r\n" + str);
            moba = 1;

        }
        if (selection == 1) {
            cashlist = cm.getCashlist();
            if (cashlist != null) {
                for (var i = 0; i < cashlist.size(); i++) {
                    str += "#L" + i + "##i" + cashlist.get(i).getItemId() + "##t" + cashlist.get(i).getItemId() + "##k\r\n";
                }
            }
            cm.sendSimple("��ѡ����Ҫ�������·������������·����޷��һء�����ж����ͬ���·��������ȶ���������ǰ��Ķ�����\r\n" + str);
            moba = 2;

        }

    } else if (status == 2) {
        if (moba == 1) {
            select = selection;
            isok = cm.removeItem(equiplist.get(select).getItemId());
            if (isok) {
                cm.sendOk("��ɾ���õ��ߣ�");
            } else {
                cm.sendOk("ɾ��ʧ�ܣ��뱨�����Ա��");
            }
            cm.dispose();

        } else {
            cm.dispose();
        }
        if (moba == 2) {
            select = selection;
            isok = cm.removeItem(cashlist.get(select).getItemId());
            if (isok) {
                cm.sendOk("��ɾ���õ��ߣ�");
            } else {
                cm.sendOk("ɾ��ʧ�ܣ��뱨�����Ա��");
            }
            cm.dispose();

        } else {
            cm.dispose();
        }
    }
}
