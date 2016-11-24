package moment;

import java.util.HashMap;
import java.util.Map;

class Caretaker {
    private Map<String, Memento> memMap = new HashMap<String, Memento>();

    public Memento getMemento(String index) {
        return memMap.get(index); //return 一个 memento对象 
    }

    public void setMemento(String index, Memento memento) {
        this.memMap.put(index, memento);
    }
}  