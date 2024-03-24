/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package client;

public class CharacterNameAndId {
    private int id;
    private String name, group;

    public CharacterNameAndId(int id, String name, String group) {
        super();
        this.id = id;
        this.name = name;
	this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }
}
