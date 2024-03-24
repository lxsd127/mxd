var status = 0;
var jobId;
var jobName;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 2) {
        cm.sendOk("������.");
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        if (cm.getJob() == 0) {
            if (cm.getPlayer().getLevel() >= 8) {
                cm.sendNext("�����Ϊһλ#rħ��ʦ#k ?");
            } else {
                cm.sendOk("�㻹û�о߱���Ϊ#rħ��ʦ#k���ʸ�")
                cm.dispose();
            }
        } else {
            if (cm.getPlayer().getLevel() >= 30 && cm.getJob() == 200) { // ��ʦ
                if (cm.haveItem(4031012, 1)) {
                    if (cm.haveItem(4031012, 1)) {
                        status = 20;
                        cm.sendNext("��֪�����Ѿ������תְ����!");
                    } else {
                        if (!cm.haveItem(4031009)) {
                            cm.gainItem(4031009, 1);
                        }
                        cm.sendOk("��ȥ�� #r��ʦתְ�̹�#k.")
                        cm.dispose();
                    }
                } else {
                    status = 10;
                    cm.sendNext("����������תְ��׼��.���ҿ���...");
                }
            } else if (cm.getPlayer().getLevel() >= 70 && cm.getJob() == 210 || cm.getJob() == 220 || cm.getJob() == 230) {
				if(cm.haveItem(4031057, 1)){
			    cm.sendOk("�������һ�����飬����ȥ�� #b³��#k.λ�ڱ���ѩ��#b���Ϲ���#k!");
               } if (cm.haveItem(4031059, 1)) {
                    cm.gainItem(4031057, 1);
                    cm.gainItem(4031059, -1);
                   //cm.warp(211000001, 0);
                    cm.sendOk("�������һ������,����ȥ��#b³��#k,λ�ڱ���ѩ��#b���Ϲ���#k");
                } else {
                    cm.sendOk("��,#b#h0##k!����Ҫһ��#b�ڷ�#k.��ȥ�����Ԫ�ռ��ø���.");
                }
                cm.dispose();
            } else {
                cm.sendOk("ħ��ʦ��������.��������������ǿ��...");
                cm.dispose();
            }
        }
    } else if (status == 1) {
        cm.sendNextPrev("һ��תְ�˾Ͳ��ܷ��ڡ�");
    } else if (status == 2) {
        cm.sendYesNo("�����Ҫ��Ϊһλ #r��ʦ#k ?");
    } else if (status == 3) {
        if (cm.getJob() == 0) {
            cm.changeJob(200); // ��ʦ
			cm.resetStats(4, 4, 4, 4);
        }
        cm.gainItem(1372005, 1);
        cm.sendOk("תְ�ɹ� !");
        cm.dispose();
    } else if (status == 11) {
        cm.sendNextPrev("�����ѡ����Ҫתְ��Ϊһλ#r��ʦ(��,��)#k,#r��ʦ(��,��)#k ��#r��ʦ#k.");
    } else if (status == 12) {
        cm.askAcceptDecline("�����ұ����Ȳ�����,��׼�������� ?");
    } else if (status == 13) {
        cm.gainItem(4031009, 1);
        //cm.warp(101020000);
        cm.sendOk("��ȥ��#b��ʦ��ת�̹�#k����#bħ�����ֱ���#k�����Ϸ�,����������!");
        cm.dispose();
    } else if (status == 21) {
        cm.sendSimple("����Ҫ��Ϊʲô?#b\r\n#L0#��ʦ(��,��)#l\r\n#L1#��ʦ(��,��)#l\r\n#L2#��ʦ#l#k");
    } else if (status == 22) {
        if (selection == 0) {
            jobName = "��ʦ(��,��)";
            jobId = 210; // FP
        } else if (selection == 1) {
            jobName = "��ʦ(��,��)";
            jobId = 220; // IL
        } else {
            jobName = "��ʦ";
            jobId = 230; // CLERIC
        }
        cm.sendYesNo("�����Ҫ��Ϊһλ #r" + jobName + "#k?");
    } else if (status == 23) {
        cm.changeJob(jobId);
        cm.gainItem(4031012, -1);
        cm.sendOk("תְ�ɹ� !");
        cm.dispose();
    }
}