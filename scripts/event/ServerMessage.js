
var Message = new Array(
        "�벻Ҫʹ�÷Ƿ����򣬲�Ȼ���ܵ������Ʋá�",
        "������ܹ������ܸ�npc�Ի�,���������� @�⿨/@ea ������쳣״̬.");

var setupTask;

function init() {
    scheduleNew();
}

function scheduleNew() {
    setupTask = em.schedule("start", 900000);
}

function cancelSchedule() {
    setupTask.cancel(false);
}

function start() {
    scheduleNew();
    em.broadcastYellowMsg("[ð�յ�ONLINE ����]  " + Message[Math.floor(Math.random() * Message.length)]);
}