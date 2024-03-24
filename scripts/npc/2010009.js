/*
	This file is part of the OdinMS Maple Story Server
	Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc> 
					   Matthias Butz <matze@odinms.de>
					   Jan Christian Meyer <vimes@odinms.de>

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU Affero General Public License as
	published by the Free Software Foundation version 3 as published by
	the Free Software Foundation. You may not use, modify or distribute
	this program under any other version of the GNU Affero General Public
	License.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Affero General Public License for more details.

	You should have received a copy of the GNU Affero General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * Guild Alliance NPC
 */

var status;
var choice;
var guildName;
var partymembers;

function start() {
	//cm.sendOk("The Guild Alliance is currently under development.");
	//cm.dispose();
	partymembers = cm.getPartyMembers();
	status = -1;
	action(1,0,0);
}

function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
	}
	if (status == 0) {
		cm.sendSimple("����!�ҽ�#b������#k��\r\n#b#L0#������Ҽ���������ʲô��#l\r\n#L1#Ҫ�����������˵Ļ�Ӧ����ô����#l\r\n#L2#��������������ˡ�#l\r\n#L3#�������Ӽ������˵ļ���������#l\r\n#L4#�����ɢ�������ˡ�#l");
	} else if (status == 1) {
		choice = selection;
	    if (selection == 0) {
		    cm.sendOk("�������˽���ֻ��˵�������һЩ��ҵ���ˡ�������һ���γ�һ���Ӵ�ļ��塣�Ҹ��������Щ���˼��塣");
			cm.dispose();
		} else if (selection == 1) {
			cm.sendOk("���Ҫ�����������ˣ�������2��������峤��ӡ���Ե���ӳ�����Ϊ�½��������˵��峤��");
			cm.dispose();
		} else if(selection == 2) {
			if (cm.getPlayer().getParty() == null || partymembers == null || partymembers.size() != 2 || !cm.isLeader()) {
				cm.sendOk("��ȷ����������ֻ��2����һ��Ҫ���˵ļ����峤��Ӻ��ٺ���˵����"); //Not real text
				cm.dispose();
			} else if (partymembers.get(0).getGuildId() <= 0 || partymembers.get(0).getGuildRank() > 1) {
				cm.sendOk("�㲻�ܴ����������ˡ���Ϊ��û�м��塣");
				cm.dispose();
			} else if (partymembers.get(1).getGuildId() <= 0 || partymembers.get(1).getGuildRank() > 1) {
				cm.sendOk("���������ƺ���һλ��Աû�м��塣");
				cm.dispose();
			} else {
				var gs = cm.getGuild(cm.getPlayer().getGuildId());
				var gs2 = cm.getGuild(partymembers.get(1).getGuildId());
				if (gs.getAllianceId() > 0) {
					cm.sendOk("���Ѿ�����һ���������С���ˣ������ټ���������");
					cm.dispose();
				} else if (gs2.getAllianceId() > 0) {
					cm.sendOk("�������еĳ�Ա�Ѿ�����һ�������˵ĳ�Ա��");
					cm.dispose();
				} else if (cm.partyMembersInMap() < 2) {
					cm.sendOk("��ȷ��������е���һ����Һ�����ͬһ��ͼ��");
					cm.dispose();
				} else
                			cm.sendYesNo("��~��������Ȥ����һ���������ˣ�");
			}
		} else if (selection == 3) {
			if (cm.getPlayer().getGuildRank() == 1 && cm.getPlayer().getAllianceRank() == 1) {
				cm.sendYesNo("�������˼���������Ҫ֧��20,000,000���. ��ȷ��Ҫ������?"); //ExpandGuild Text
			} else {
			    cm.sendOk("ֻ�����˶ӳ��ſ����������˼���������");
				cm.dispose();
			}
		} else if(selection == 4) {
			if (cm.getPlayer().getGuildRank() == 1 && cm.getPlayer().getAllianceRank() == 1) {
				cm.sendYesNo("��ȷ��Ҫ��ɢ��ļ������ˣ�");
			} else {
				cm.sendOk("ֻ�����˶ӳ��ſ��Խ�ɢ�������ˡ�");
				cm.dispose();
			}
		}
	} else if(status == 2) {
	    if (choice == 2) {
		    cm.sendGetText("��������Ҫ�����������˵����ơ�(���13���ֽ�)");
		} else if (choice == 3) {
			if (cm.getPlayer().getGuildId() <= 0) {
				cm.sendOk("�㲻�����Ӳ����ڵļ������ˡ�");
				cm.dispose();
			} else {
				if (cm.addCapacityToAlliance()) {
					cm.sendOk("��ɹ������������˼���������");
				} else {
					cm.sendOk("�ܱ�Ǹ������������˼������������������ټ������ӡ�");
				}
				cm.dispose();
			}
		} else if (choice == 4) {
			if (cm.getPlayer().getGuildId() <= 0) {
				cm.sendOk("�㲻�ܽ�ɢ�����ڵļ������ˡ�");
				cm.dispose();
			} else {
				if (cm.disbandAlliance()) {
					cm.sendOk("���������Ѿ�����ɢ�������Ҫ�ٴδ��������ٺ���˵����");
				} else {
					cm.sendOk("��ɢ�������˳������Ժ����ԡ�");
				}
				cm.dispose();
			}
		}
	} else if (status == 3) {
		guildName = cm.getText();
	    cm.sendYesNo("��ȷ��ʹ��#b"+ guildName + "#k��Ϊ�������˵�������");
	} else if (status == 4) {
			if (!cm.createAlliance(guildName)) {
				cm.sendNext("������Ʋ��ܱ�ʹ�ã��볢���������ơ�"); //Not real text
				status = 1;
				choice = 2;
			} else
				cm.sendOk("�ɹ������˼������ˡ�");
			cm.dispose();
	}
}