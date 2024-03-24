

function action(mode, type, selection) {
    cm.removeAll(4001117);
    cm.removeAll(4001120);
    cm.removeAll(4001121);
    cm.removeAll(4001122);
    if (cm.getPlayer().getParty() == null || !cm.isLeader()) {
        cm.sendOk("���öӳ�������̸����");
    } else {
        var party = cm.getPlayer().getParty().getMembers();
        var mapId = cm.getPlayer().getMapId();
        var next = true;
        var size = 0;
        var it = party.iterator();
        while (it.hasNext()) {
            var cPlayer = it.next();
            var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
            if (ccPlayer == null || ccPlayer.getLevel() < 55 || ccPlayer.getLevel() > 200) {
                next = false;
                break;
            }
            size += (ccPlayer.isGM() ? 4 : 1);
        }
        if (next && size >= 3) {
            var em = cm.getEventManager("Pirate");
            if (em == null) {
                cm.sendOk("����δ֪�������Ժ����ԡ�");
            } else {
                var prop = em.getProperty("state");
                if (prop.equals("0") || prop == null) {
                    em.startInstance(cm.getPlayer().getParty(), cm.getPlayer().getMap(), 120);
                } else {
                    cm.sendOk("Ŀǰ�ж�����ִ��������������Ƶ��������Ⱥ�");
                }
            }
        } else {
            cm.sendOk("��Ҫ3��������,�ȼ�������55����100����");
        }
    }
    cm.dispose();
}