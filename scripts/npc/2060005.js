var status = -1;
var minLevel = 70;
var maxLevel = 200;

var minPartySize = 1;
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

    if (status == 0) {
	if (cm.getParty() == null) { // No Party
	    cm.sendOk("�������������");
	} else if (!cm.isLeader()) { // Not Party Leader
	    cm.sendOk("����볢�Ա���Ұ������ #b��ӳ�������#k ̸̸��#b#l");
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
		    inMap += (1);
		}
	    }
	    if (party.size() > maxPartySize || inMap < minPartySize) {
		next = false;
	    }
	    if (next) {
		var em = cm.getEventManager("ProtectPig");
		if (em == null) {
		    cm.sendSimple("����δ֪�������Ժ����ԡ�#b#l");
		} else {
		    var prop = em.getProperty("state");
		    if (prop.equals("0") || prop == null) {
        for (var i = 4001095; i < 4001099; i++) {
	    cm.givePartyItems(i, 0, true);
	}
        for (var i = 4001100; i < 4001101; i++) {
	    cm.givePartyItems(i, 0, true);
	}
			em.startInstance(cm.getParty(), cm.getMap());
			cm.dispose();
			return;
		    } else {
			cm.sendSimple("�Ѿ��� #r����һ��#k ��ȥ��ս�ˣ����Ժ����ԡ�#b#");
		    }
		}
	    } else {
		cm.sendSimple("�����������û�дﵽҪ��:\r\n\r\n#r���ٵĳ�Ա: " + minPartySize + " ȫ���ȼ������� " + minLevel + " �� " + maxLevel + ".#b#l");
	    }
	}
	}
}