/*
	NPC Name: 		Adobis
	Map(s): 		El Nath : Entrance to Zakum Altar
	Description: 		Zakum battle starter
*/
var status = 0;

function action(mode, type, selection) {
	if (cm.getPlayer().getMapId() == 211042200) {
		if (selection < 100) {
			cm.sendSimple("#r#L100#Zakum#l\r\n#L101#Chaos Zakum#l");
		} else {
			if (selection == 100) {
				cm.warp(211042300,0);
			} else if (selection == 101) {
				cm.warp(211042301,0);
			}
			cm.dispose();
		}
		return;
	} else if (cm.getPlayer().getMapId() == 211042401) {
    switch (status) {
	case 0:
		if (cm.getPlayer().getLevel() < 100) {
			cm.sendOk("��ĵȼ�����ﵽ100������.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 2) {
			cm.sendOk("Chaos Zakum is not available till Magic is fixed.");
			cm.dispose();
			return;
		}
	    var em = cm.getEventManager("ChaosZakum");

	    if (em == null) {
		cm.sendOk("The event isn't started, please contact a GM.");
		cm.safeDispose();
		return;
	    }
	var prop = em.getProperty("state");
	    var marr = cm.getQuestRecord(160102);
	    var data = marr.getCustomData();
	    if (data == null) {
		marr.setCustomData("0");
	        data = "0";
	    }
	    var time = parseInt(data);
	if (prop == null || prop.equals("0")) {
	    var squadAvailability = cm.getSquadAvailability("ChaosZak");
	    if (squadAvailability == -1) {
		status = 1;
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Chaos Zakum in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
		cm.sendYesNo("������Ȥ��ΪԶ���ӵĶӳ���");

	    } else if (squadAvailability == 1) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Chaos Zakum in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
		// -1 = Cancelled, 0 = not, 1 = true
		var type = cm.isSquadLeader("ChaosZak");
		if (type == -1) {
		    cm.sendOk("Զ�����Ѿ������������µǼǡ�");
		    cm.safeDispose();
		} else if (type == 0) {
		    var memberType = cm.isSquadMember("ChaosZak");
		    if (memberType == 2) {
			cm.sendOk("�㱻��ֹ�μ�Զ���ӡ�");
			cm.safeDispose();
		    } else if (memberType == 1) {
			status = 5;
			cm.sendSimple("������ʲô? \r\n#b#L0#�鿴Զ����#l \r\n#b#L1#����Զ����#l \r\n#b#L2#�˳�Զ����#l");
		    } else if (memberType == -1) {
			cm.sendOk("Զ�����Ѿ������������µǼǡ�");
			cm.safeDispose();
		    } else {
			status = 5;
			cm.sendSimple("������ʲô?\r\n#b#L0#�鿴Զ����#l \r\n#b#L1#����Զ����#l \r\n#b#L2#�˳�Զ����#l");
		    }
		} else { // Is leader
		    status = 10;
		    cm.sendSimple("������ʲô? \r\n#b#L0#�鿴Զ����#l \r\n#b#L1#�Ƴ�Զ����Ա#l \r\n#b#L2#�༭�����б�#l \r\n#r#L3#��ʼ��ս����#l");
		// TODO viewing!
		}
	    } else {
			var eim = cm.getDisconnected("ChaosZakum");
			if (eim == null) {
				var squd = cm.getSquad("ChaosZak");
				if (squd != null) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Chaos Zakum in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
					cm.sendYesNo("�Բ���,�����Ѿ���ʼ����ս������ս��!\r\n" + squd.getNextPlayer());
					status = 3;
				} else {
					cm.sendOk("�Բ���,�����Ѿ���ʼ����ս������ս��!");
					cm.safeDispose();
				}
			} else {
				cm.sendYesNo("��Ը��������Զ���ӵ�ս������?");
				status = 2;
			}
	    }
	} else {
			var eim = cm.getDisconnected("ChaosZakum");
			if (eim == null) {
				var squd = cm.getSquad("ChaosZak");
				if (squd != null) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Chaos Zakum in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
					cm.sendYesNo("�Բ���,�����Ѿ���ʼ����ս������ս��!\r\n" + squd.getNextPlayer());
					status = 3;
				} else {
					cm.sendOk("�Բ���,�����Ѿ���ʼ����ս������ս��!");
					cm.safeDispose();
				}
			} else {
				cm.sendYesNo("��Ը��������Զ���ӵ�ս������?");
				status = 2;
			}
	}
	    break;
	case 1:
	    	if (mode == 1) {
			if (cm.registerSquad("ChaosZak", 5, " has been named the Leader of the squad (Chaos). If you would you like to join please register for the Expedition Squad within the time period.")) {
				cm.sendOk("You have been named the Leader of the Squad. For the next 5 minutes, you can add the members of the Expedition Squad.");
			} else {
				cm.warp(280030000,1);
			}
	    	} else {
			cm.sendOk("��������ΪԶ���Ӷӳ��Ļ��������̸̸.")
	    	}
	    cm.safeDispose();
	    break;
	case 2:
		if (!cm.reAdd("ChaosZakum", "ChaosZak")) {
			cm.sendOk("����... ������һ��.");
		}
		cm.dispose();
		break;
	case 3:
		if (mode == 1) {
			var squd = cm.getSquad("ChaosZak");
			if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
				squd.setNextPlayer(cm.getPlayer().getName());
				cm.sendOk("���Ѿ��������ֳ�.");
			}
		}
		cm.dispose();
		break;
	case 5:
	    if (selection == 0) {
		if (!cm.getSquadList("ChaosZak", 0)) {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		    cm.safeDispose();
		} else {
		    cm.dispose();
		}
	    } else if (selection == 1) { // join
		var ba = cm.addMember("ChaosZak", true);
		if (ba == 2) {
		    cm.sendOk("�������������Ժ����ԡ�");
		    cm.safeDispose();
		} else if (ba == 1) {
		    cm.sendOk("���ѳɹ�������顣");
		    cm.safeDispose();
		} else {
		    cm.sendOk("���Ѽ�����顣");
		    cm.safeDispose();
		}
	    } else {// withdraw
		var baa = cm.addMember("ChaosZak", false);
		if (baa == 1) {
		    cm.sendOk("���ѳɹ��˳����顣");
		    cm.safeDispose();
		} else {
		    cm.sendOk("�㲻�Ƕ����е�һԱ��");
		    cm.safeDispose();
		}
	    }
	    break;
	case 10:
	    if (selection == 0) {
		if (!cm.getSquadList("ChaosZak", 0)) {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		}
		cm.safeDispose();
	    } else if (selection == 1) {
		status = 11;
		if (!cm.getSquadList("ChaosZak", 1)) {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		cm.safeDispose();
		}

	    } else if (selection == 2) {
		status = 12;
		if (!cm.getSquadList("ChaosZak", 2)) {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		cm.safeDispose();
		}

	    } else if (selection == 3) { // get insode
		if (cm.getSquad("ChaosZak") != null) {
		    var dd = cm.getEventManager("ChaosZakum");
		    dd.startInstance(cm.getSquad("ChaosZak"), cm.getMap(), 160102);
		    cm.dispose();
		} else {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		    cm.safeDispose();
		}
	    }
	    break;
	case 11:
	    cm.banMember("ChaosZak", selection);
	    cm.dispose();
	    break;
	case 12:
	    if (selection != -1) {
		cm.acceptMember("ChaosZak", selection);
	    }
	    cm.dispose();
	    break;
    }
	} else {
    switch (status) {
	case 0:
		if (cm.getPlayer().getLevel() < 50) {
			cm.sendOk("�ȼ��������50��������ս����");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 4) {
			cm.sendOk("��ս����ֻ����1��2��4Ƶ����");
			cm.dispose();
			return;
		}
	    var em = cm.getEventManager("ZakumBattle");

	    if (em == null) {
		cm.sendOk("����δ֪�������Ժ����ԡ�");
		cm.safeDispose();
		return;
	    }
	var prop = em.getProperty("state");
	    var marr = cm.getQuestRecord(160101);
	    var data = marr.getCustomData();
	    if (data == null) {
		marr.setCustomData("0");
	        data = "0";
	    }
	    var time = parseInt(data);
	if (prop == null || prop.equals("0")) {
	    var squadAvailability = cm.getSquadAvailability("ZAK");
	    if (squadAvailability == -1) {
		status = 1;
	    if (time + (1 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Zakum in the past 6 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 360000)));
		cm.dispose();
		return;
	    }
		cm.sendYesNo("������Ȥ��ΪԶ���ӵĶӳ���");

	    } else if (squadAvailability == 1) {
	    if (time + (1 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Zakum in the past 6 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 360000)));
		cm.dispose();
		return;
	    }
		// -1 = Cancelled, 0 = not, 1 = true
		var type = cm.isSquadLeader("ZAK");
		if (type == -1) {
		    cm.sendOk("Զ�����ѽ����������µǼǡ�");
		    cm.safeDispose();
		} else if (type == 0) {
		    var memberType = cm.isSquadMember("ZAK");
		    if (memberType == 2) {
			cm.sendOk("�㱻��ֹ�μ�Զ���ӡ�");
			cm.safeDispose();
		    } else if (memberType == 1) {
			status = 5;
			cm.sendSimple("������ʲô?\r\n#b#L0#�鿴Զ����#l \r\n#b#L1#����Զ����#l \r\n#b#L2#�˳�Զ����#l");
		    } else if (memberType == -1) {
			cm.sendOk("Զ�����ѽ����������µǼǡ�.");
			cm.safeDispose();
		    } else {
			status = 5;
			cm.sendSimple("������ʲô? \r\n#b#L0#�鿴Զ����#l \r\n#b#L1#����Զ����#l \r\n#b#L2#�˳�Զ����#l");
		    }
		} else { // Is leader
		    status = 10;
		    cm.sendSimple("������ʲô?\r\n#b#L0#�鿴Զ����#l \r\n#b#L1#�Ƴ�Զ���ӳ�Ա#l \r\n#b#L2#�༭�����б�#l \r\n#r#L3#��ʼ��ս����#l");
		// TODO viewing!
		}
	    } else {
			var eim = cm.getDisconnected("ZakumBattle");
			if (eim == null) {
				var squd = cm.getSquad("ZAK");
				if (squd != null) {
	    if (time + (1 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Zakum in the past 6 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 360000)));
		cm.dispose();
		return;
	    }
					cm.sendYesNo("�Բ���,�����Ѿ���ʼ����ս������ս��!\r\n" + squd.getNextPlayer());
					status = 3;
				} else {
					cm.sendOk("�Բ���,�����Ѿ���ʼ����ս������ս��!");
					cm.safeDispose();
				}
			} else {
				cm.sendYesNo("��Ը��������Զ���ӵ�ս������?");
				status = 1;
			}
	    }
	} else {
			var eim = cm.getDisconnected("ZakumBattle");
			if (eim == null) {
				var squd = cm.getSquad("ZAK");
				if (squd != null) {
	    if (time + (1 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Zakum in the past 6 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 360000)));
		cm.dispose();
		return;
	    }
					cm.sendYesNo("�Բ���,�����Ѿ���ʼ����ս������ս��!\r\n" + squd.getNextPlayer());
					status = 3;
				} else {
					cm.sendOk("�Բ���,�����Ѿ���ʼ����ս������ս��!");
					cm.safeDispose();
				}
			} else {
				cm.sendYesNo("��Ը��������Զ���ӵ�ս������?");
				status = 1;
			}
	}
	    break;
	case 1:
	    	if (mode == 1) {
			if (cm.registerSquad("ZAK", 5, " �ѱ�����ΪԶ���Ӷӳ������ڣ�����������������ʱ����ڵǼǵ�Զ����.")) {
				cm.sendOk("���Ѿ�������ΪԶ���Ӷӳ����ڽ�������5���ӣ�����Թ���Զ���ӵĳ�Ա��");
			} else {
				cm.sendOk("����Զ����ʱ������һ������");
			}
	    	} else {
			cm.sendOk("��������ΪԶ���Ӷӳ��Ļ���������̸̸.")
	    	}
	    cm.safeDispose();
	    break;
	case 2:
		if (!cm.reAdd("ZakumBattle", "ZAK")) {
			cm.sendOk("����... ���Ժ����ԡ�");
		}
		cm.safeDispose();
		break;
	case 3:
		if (mode == 1) {
			var squd = cm.getSquad("ZAK");
			if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
				squd.setNextPlayer(cm.getPlayer().getName());
				cm.sendOk("���Ѿ��������ֳ�.");
			}
		}
		cm.dispose();
		break;
	case 5:
	    if (selection == 0) {
		if (!cm.getSquadList("ZAK", 0)) {
		    cm.sendOk("Due to an unknown error, the request for squad has been denied.");
		    cm.safeDispose();
		} else {
		    cm.dispose();
		}
	    } else if (selection == 1) { // join
		var ba = cm.addMember("ZAK", true);
		if (ba == 2) {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		    cm.safeDispose();
		} else if (ba == 1) {
		    cm.sendOk("���ѳɹ�������顣");
		    cm.safeDispose();
		} else {
		    cm.sendOk("�����Ƕ����һ���֡�");
		    cm.safeDispose();
		}
	    } else {// withdraw
		var baa = cm.addMember("ZAK", false);
		if (baa == 1) {
		    cm.sendOk("���ѳɹ����˳��˶��顣");
		    cm.safeDispose();
		} else {
		    cm.sendOk("�㲻�Ƕ����е�һԱ��");
		    cm.safeDispose();
		}
	    }
	    break;
	case 10:
	    if (selection == 0) {
		if (!cm.getSquadList("ZAK", 0)) {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		}
		cm.safeDispose();
	    } else if (selection == 1) {
		status = 11;
		if (!cm.getSquadList("ZAK", 1)) {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		cm.safeDispose();
		}

	    } else if (selection == 2) {
		status = 12;
		if (!cm.getSquadList("ZAK", 2)) {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		cm.safeDispose();
		}

	    } else if (selection == 3) { // get insode
		if (cm.getSquad("ZAK") != null) {
		    var dd = cm.getEventManager("ZakumBattle");
		    dd.startInstance(cm.getSquad("ZAK"), cm.getMap(), 160101);
		    cm.dispose();
		} else {
		    cm.sendOk("����δ֪�Ĵ��󣬶Զ����Ҫ�󱻾ܾ���.");
		    cm.safeDispose();
		}
	    }
	    break;
	case 11:
	    cm.banMember("ZAK", selection);
	    cm.dispose();
	    break;
	case 12:
	    if (selection != -1) {
		cm.acceptMember("ZAK", selection);
	    }
	    cm.dispose();
	    break;
    }
	}
}