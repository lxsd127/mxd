function action(mode, type, selection) {
    if (cm.getPlayer().getMapId() == 920011200) { //exit
    for (var i = 4001044; i < 4001064; i++) {
        cm.removeAll(i); //holy
    }
    cm.warp(200080101);
    cm.dispose();
    return;
    }
    var em = cm.getEventManager("OrbisPQ");
    if (em == null) {
    cm.sendOk("�ű���������ϵ����Ա��");
    cm.dispose();
    return;
    }
    if (!cm.isLeader()) {
    cm.sendOk("��ֻ�ܸ���Ķӳ�˵����");
    cm.dispose();
    return;
    }
     if (em.getProperty("pre").equals("0")) {
		if(cm.getPlayer().haveItem(4001063, 20) && cm.getPlayer().getMapId() == 920010000) {
			cm.givePartyExp(8000);
			cm.gainItem(4001063, -20);
			em.setProperty("pre", "1");
		} else{
			cm.sendNext("�ұ�Զ�ž������������������ռ��������ҳ�ȥ��");
			cm.dispose();
			return;
		}
    }
    switch(cm.getPlayer().getMapId()) {
    case 920010000:
            clear();
        cm.warpParty(920010000, 2);
        break;
    case 920010100:
        if (em.getProperty("stage").equals("6")) {
        if (em.getProperty("finished").equals("0")) {
            cm.warpParty(920010800); //GARDEN.  
        } else {
            cm.sendOk("лл��������ǣ�������Ů��˵����");
			cm.dispose();
        }
        } else {
        cm.sendOk("���ռ�����Ů��������Ƭ��Ȼ��������̸��������һ�顣");
		cm.dispose();
        }
        break;
    case 920010200: //walkway
        if (!cm.haveItem(4001050,30)) {
		cm.sendOk("����Ҫ#b#t4001050# 30��#k��Ŀǰ��#c4001050#����");
		cm.dispose();
        } else {
        cm.removeAll(4001050);
        cm.gainItem(4001044,1); //first piece
        cm.givePartyExp(9000);
        clear();
        }
        break;
    case 920010300: //storage
        if (!cm.haveItem(4001051,15)) {
        cm.sendOk("����Ҫ#b#t4001051# 15��#k��Ŀǰ��#c4001051#����");
		cm.dispose();
        } else {
        cm.removeAll(4001051);
        cm.gainItem(4001045,1); //second piece
        cm.givePartyExp(12000);
        clear();
        }
        break;
    case 920010400: //lobby
        if (em.getProperty("stage3").equals("0")) {
        cm.sendOk("���ҵ�����ĳ�Ƭ���������������ֺв���\r\n#v4001056#������\r\n#v4001057#����һ\r\n#v4001058#���ڶ�\r\n#v4001059#������\r\n#v4001060#������\r\n#v4001061#������\r\n#v4001062#������\r\n");
        } else if (em.getProperty("stage3").equals("1")) {
        if (cm.canHold(4001046,1)) {
            cm.gainItem(4001046,1); //third piece
            //cm.givePartyExp(7500);
            clear();
            em.setProperty("stage3", "2");
        } else {
            cm.sendOk("�����һЩ�ռ䡣");
        }
        } else {
        cm.sendOk("лл�㡣");
        }
        break;
    case 920010500: //sealed
        if (em.getProperty("stage4").equals("0")) {
        var players = Array();
        var total = 0;
        for (var i = 0; i < 3; i++) {
            var z = cm.getMap().getNumPlayersInArea(i);
            players.push(z);
            total += z;
        }
        if (total < 5) {
            cm.sendOk("��Ҫ5�����վ��ƽ̨�ϡ�");
			cm.dispose();
        } else {
            var num_correct = 0;
            for (var i = 0; i < 3; i++) {
            if (em.getProperty("stage4_" + i).equals("" + players[i])) {
                num_correct++;
            }
            }
            if (num_correct == 3) {
            if (cm.canHold(4001047,1)) {
				clear();
                cm.gainItem(4001047,1); //fourth
                cm.givePartyExp(40000);
				em.setProperty("stage4", "1");
            } else {
                cm.sendOk("�����һЩ�ռ䡣");
            }
            } else {
				cm.showEffect(true, "quest/party/wrong_kor");
				cm.playSound(true, "Party1/Failed");
            if (num_correct > 0) {
                cm.sendOk("һ��ƽ̨����ȷ�ġ�");
				cm.dispose();
            } else {
                cm.sendOk("����ƽ̨���Ǵ�ġ�");
				cm.dispose();
            }
            }
        }
        } else {
        cm.sendOk("��ô���Ѿ����ˣ�");
		cm.dispose();
        }
        cm.dispose();
        break;
    case 920010600: //lounge
        if (!cm.haveItem(4001052,40)) {
		cm.sendOk("����Ҫ#b#t4001052# 40��#k��Ŀǰ��#c4001052#����");
		cm.dispose();
        } else {
        cm.removeAll(4001052);
        cm.gainItem(4001048,1); //fifth piece
        //cm.givePartyExp(7500);
        clear();
        }
        break;
    case 920010700: //on the way up
        if (em.getProperty("stage6").equals("0")) {
        var react = Array();
        var total = 0;
            for(var i = 0; i < 5; i++) {
            if (cm.getMap().getReactorByName("" + (i + 1)).getState() > 0) {
            react.push("1");
            total += 1;
            } else {
            react.push("0");
            }
            }
        if (total != 2) {
            cm.sendOk("��Ҫ���������ڶ����ش���Ŀ��");
			cm.dispose();
        } else {
            var num_correct = 0;
            for (var i = 0; i < 5; i++) {
            if (em.getProperty("stage62_" + i).equals("" + react[i])) {
                num_correct++;
            }
            }
            if (num_correct == 5) {
            if (cm.canHold(4001049,1)) {
                    clear();
                cm.gainItem(4001049,1); //sixth
                cm.givePartyExp(50000);
                    em.setProperty("stage6", "1");
            } else {
                cm.sendOk("�����һЩ�ռ䡣");
				cm.dispose();
            }
            } else {
                    cm.showEffect(true, "quest/party/wrong_kor");
                    cm.playSound(true, "Party1/Failed");
            if (num_correct >= 3) {
                cm.sendOk("һ���ܸ�����ȷ�ġ�");
				cm.dispose();
            } else {
                cm.sendOk("�����ܸ˶��Ǵ���ġ�");
				cm.dispose();
            }
            }
        }
        } else {
        cm.sendOk("лл�㡣");
        }
        break;
    case 920010800:
        cm.warpParty(920010100);
        break;
    case 920010900:
        cm.sendNext("�������ļ���������ܻᷢ��һЩ�óԵĶ��������������֮�⣬�Ҳ���Ϊ������ʲô���������"); 
        break;
    case 920011000:
        cm.sendNext("�������صķ�����������ܻᷢ��һЩ�óԵĶ��������������֮�⣬�Ҳ���Ϊ������ʲô���������"); 
        break;
    }
    cm.dispose();
}

function clear() {
    cm.showEffect(true, "quest/party/clear");
    cm.playSound(true, "Party1/Clear");
}