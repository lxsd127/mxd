function enter(pi) {
    if (pi.getMapId() == 240060100) {
        var em = pi.getEventManager("HorntailBattle");
        if (em != null) {
            var prop = em.getProperty("preheadCheck");
            if (prop != null && prop.equals("2")) {
                pi.mapMessage(6, "������о޴�����������ڿ�����")
                em.setProperty("preheadCheck", "3");
            }
        }
    }
}