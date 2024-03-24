var status = -1;
var minLevel = 51; // 35
var maxLevel = 200; // 65

var minPartySize = 5;
var maxPartySize = 6;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
            return;
        }
        status--;
    }
    if (cm.getMapId() == 920010000) { //inside orbis pq
        cm.sendOk("���Ǳ��������� ��Ҫ20���Ƶ���Ƭ!");
        cm.dispose();
        return;
    }
    if (status == 0) {
        for (var i = 4001044; i < 4001064; i++) {
            cm.removeAll(i); //holy
        }
        if (cm.getParty() == null) { // No Party
            cm.sendSimple("�����Ӳ�����һ��Ҫ��...:\r\n\r\n#rҪ��: " + minPartySize + " ��ӳ�Ա, ���г�Ա�ȼ������� " + minLevel + " ���ȼ� " + maxLevel + ".#b\r\n#L0#��Ҫ�һ�Ů������#l");
        } else if (!cm.isLeader()) { // Not Party Leader
            cm.sendSimple("�������ִ����Ӷ�Ů�ޣ����� #b�ӳ�#k �����ҽ�̸��#b\r\n#L0#��Ҫ�һ�Ů������#l");
        } else {
            // Check if all party members are within PQ levels
            var party = cm.getParty().getMembers();
            var mapId = cm.getMapId();
            var next = true;
            var levelValid = 0;
            var inMap = 0;
            var it = party.iterator();

            while (it.hasNext()) {
                var cPlayer = it.next();
                if ((cPlayer.getLevel() >= minLevel) && (cPlayer.getLevel() <= maxLevel)) {
                    levelValid += 1;
                } else {
                    next = false;
                }
                if (cPlayer.getMapid() == mapId) {
                    inMap += (cPlayer.getJobId() == 900 ? 6 : 1);
                }
            }
            if (party.size() > maxPartySize || inMap < minPartySize) {
                next = false;
            }
            if (next) {
                var em = cm.getEventManager("OrbisPQ");
                if (em == null) {
                    cm.sendSimple("����δ֪�������Ժ����ԡ�#b\r\n#L0#��Ҫ�һ�Ů������#l");
                } else {
                    var prop = em.getProperty("state");
                    if (prop.equals("0") || prop == null) {
                        em.startInstance(cm.getParty(), cm.getMap(), 120);
                        cm.dispose();
                        return;
                    } else {
                        cm.sendSimple("���������Ѿ��ڽ���#r�������#k�� �볢�Ը���Ƶ����ȴ�����������ɡ�#b\r\n#L0#��Ҫ�һ�Ů������#l");
                    }
                }
            } else {
                cm.sendSimple("�����Ӳ�����һ��Ҫ��...:\r\n\r\n#rҪ��: " + minPartySize + " ��ӳ�Ա, ���г�Ա�ȼ������� " + minLevel + " ���ȼ� " + maxLevel + ".#b\r\n#L0#��Ҫ�һ�Ů������#l");
            }
        }
    } else { //broken glass
        if (selection == 0) {
            if (!cm.canHold(1082232, 1)) {
                cm.sendOk("�����ˡ�");
            } else if (cm.haveItem(4001158, 40)) {
                cm.gainItem(1082232, 1, true);
                cm.gainItem(4001158, -40, true);
            } else {
                cm.sendOk("��û��40�� #t4001158#.");
            }
        }
        cm.dispose();

    }
}