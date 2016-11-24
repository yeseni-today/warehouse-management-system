package moment;

class Client {
    public static void main(String[] args) {
        Originator ori = new Originator();
        Caretaker caretaker = new Caretaker();
        ori.setTime("a1");
        ori.setLocation("a2");
        ori.setCharacter("a3");
        ori.setEvent("a4");
        System.out.println("初始化状态\n" + ori);
        caretaker.setMemento("001", ori.createMemento());

        ori.setTime("b1");
        ori.setLocation("b2");
        ori.setCharacter("b3");
        ori.setEvent("b4");
        caretaker.setMemento("002", ori.createMemento());

        ori.setTime("c1");
        ori.setLocation("c2");
        ori.setCharacter("c3");
        ori.setEvent("c4");
        caretaker.setMemento("003", ori.createMemento()); //放到管理者列表中

        System.out.println("当前状态\n" + ori);
        ori.restoreMemento(caretaker.getMemento("001"));
        System.out.println("恢复001后状态\n" + ori);
        ori.restoreMemento(caretaker.getMemento("002")); //向管理者要求 得到保存的对象 
        System.out.println("恢复002后状态\n" + ori);
    }
}  
