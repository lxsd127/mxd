/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.maps;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import tools.Pair;

public class MapleReactorStats {
    private Point tl;
    private Point br;
    private Map<Byte, StateData> stateInfo = new HashMap<Byte, StateData>();
	
    public void setTL(Point tl) {
        this.tl = tl;
    }

    public void setBR(Point br) {
        this.br = br;
    }

    public Point getTL() {
        return tl;
    }

    public Point getBR() {
        return br;
    }

    public void addState(byte state, int type, Pair<Integer, Integer> reactItem, byte nextState, int timeOut, byte canTouch) {
        stateInfo.put(state, new StateData(type, reactItem, nextState, timeOut, canTouch));
    }

    public byte getNextState(byte state) {
        StateData nextState = stateInfo.get(state);
        if (nextState != null) {
            return nextState.getNextState();
        } else {
            return -1;
        }
    }

    public int getType(byte state) {
        StateData nextState = stateInfo.get(state);
        if (nextState != null) {
            return nextState.getType();
        } else {
            return -1;
        }
    }

    public Pair<Integer, Integer> getReactItem(byte state) {
        StateData nextState = stateInfo.get(state);
        if (nextState != null) {
            return nextState.getReactItem();
        } else {
            return null;
        }
    }

    public int getTimeOut(byte state) {
        StateData nextState = stateInfo.get(state);
        if (nextState != null) {
            return nextState.getTimeOut();
        } else {
            return -1;
        }
    }
	
    public byte canTouch(byte state) {
        StateData nextState = stateInfo.get(state);
        if (nextState != null) {
            return nextState.canTouch();
        } else {
            return 0;
        }
    }

    private static class StateData {
        private int type, timeOut;
        private Pair<Integer, Integer> reactItem;
        private byte nextState, canTouch;

        private StateData(int type, Pair<Integer, Integer> reactItem, byte nextState, int timeOut, byte canTouch) {
            this.type = type;
            this.reactItem = reactItem;
            this.nextState = nextState;
            this.timeOut = timeOut;
	    this.canTouch = canTouch;
        }

        private int getType() {
            return type;
        }

        private byte getNextState() {
            return nextState;
        }

        private Pair<Integer, Integer> getReactItem() {
            return reactItem;
        }

        private int getTimeOut() {
            return timeOut;
        }
		
	private byte canTouch() {
	    return canTouch;
	}
    }
}
