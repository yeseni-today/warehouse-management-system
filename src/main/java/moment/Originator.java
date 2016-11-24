package moment;

//带有恢复功能的Bean
class Originator {
    private String time = "";
    private String location = "";
    private String character = "";
    private String event = "";

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Memento createMemento() {
        return new Memento(BeanUtils.backupProp(this)); //new新对象保存状态信息  通过构造方法 同时返回引用放入管理者列表
    }

    public void restoreMemento(Memento memento) {
        BeanUtils.restoreProp(this, memento.getStateMap());
    }

    public String toString() {
        return time + "   " + location + "   " + character + "   " + event;
    }
}  