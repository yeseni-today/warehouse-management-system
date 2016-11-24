package moment;

class Client {  
    public static void main(String[] args){  
        Originator ori = new Originator();  
        Caretaker caretaker = new Caretaker();  
        ori.setTime("三天前");
        ori.setLocation("水果摊");
        ori.setCharacter("莱月昂");
        ori.setEvent("e1");
        caretaker.setMemento("001",ori.createMemento());//放到管理者列表中
        
        ori.setTime("两天前");
        ori.setLocation("花园");
        ori.setCharacter("爱蜜莉雅");
        ori.setEvent("e2");
        caretaker.setMemento("002",ori.createMemento());
 
        ori.setTime("现在");
        ori.setLocation("庄园");
        ori.setCharacter("莱茵哈鲁特");
        ori.setEvent("e3");
        caretaker.setMemento("003",ori.createMemento());
        
        System.out.println("当前状态\n"+ori);

        ori.restoreMemento(caretaker.getMemento("001"));  
        System.out.println("恢复三天前的状态\n"+ori);
        ori.restoreMemento(caretaker.getMemento("002")); //向管理者要求 得到保存的对象 
        System.out.println("恢复两天前的状态\n"+ori);
    }  
}  
