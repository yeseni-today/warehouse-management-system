package moment;

import java.util.Map;

class Memento {
    private Map<String, Object> stateMap;

    public Memento(Map<String, Object> map) {
        this.stateMap = map;
    }

    public Map<String, Object> getStateMap() {
        return stateMap;
    }

    public void setStateMap(Map<String, Object> stateMap) {
        this.stateMap = stateMap;
        System.out.println("use!!");
    }
}  