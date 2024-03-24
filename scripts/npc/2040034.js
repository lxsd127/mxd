/*
	Red Sign - 101st Floor Eos Tower (221024500)
*/

var status = -1;
var minLevel = 30; // 35
var maxLevel = 200; // 65

var minPartySize = 5; //CHANGE after BB
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
	cm.removeAll(4001022);
	cm.removeAll(4001023);
	if (cm.getParty() == null) { // No Party
	    cm.sendOk("������Ҫ: #r\r\n" + minPartySize + " λ��Ա.��� " + minLevel + " ��,��� " + maxLevel + "��.");
	} else if (!cm.isLeader()) { // Not Party Leader
	    cm.sendOk("�����������������#b�ӳ�#k����̸.#b");
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
		var em = cm.getEventManager("LudiPQ");
		if (em == null) {
		    cm.sendOk("����δ֪�������Ժ����ԡ�");
		} else {
		    var prop = em.getProperty("state");
		    if (prop.equals("0") || prop == null) {
			em.startInstance(cm.getParty(), cm.getMap(), 70);
			cm.removeAll(4001022);
			cm.removeAll(4001023);
			cm.dispose();
			return;
		    } else {
			cm.sendOk("���������Ѿ���������#r���������#k�볢�Ի�Ƶ�����ߵ�����������ɡ�");
		    }
		}
	    } else {
		cm.sendOk("��ã�������ս�����������𣿽����ܵ������ƣ���ȷ��������ʱ���������ġ�������Ҫ: #r\r\n" + minPartySize + " λ��Ա.��� " + minLevel + " ��,��� " + maxLevel + "��.");
	    }
	}
	cm.dispose();
}