function enter(pi) {
    if (pi.getMapId() == 240060000) {
        var em = pi.getEventManager("HorntailBattle");
        if (em != null) {
            var prop = em.getProperty("preheadCheck");
            if (prop != null && prop.equals("0")) {
                pi.mapMessage(6, "������о޴�����������ڿ�����");
                em.setProperty("preheadCheck", "2");
            }
        }
    }
}