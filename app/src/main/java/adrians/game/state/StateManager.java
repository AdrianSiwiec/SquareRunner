package adrians.game.state;

import java.util.Stack;

/**
 * Created by pierre on 02/03/16.
 */
public class StateManager {
    private volatile static Stack<State> stateStack = new Stack<>();
    public synchronized static void pushState(State state) {
        stateStack.push(state);
    }
    public static State getCurrentState() {
        if(stateStack.empty())
            return null;
        return stateStack.peek();
    }
    public synchronized static void popState() {
        stateStack.pop();
        stateStack.peek().onResume();
    }
    public synchronized static void changeState(State state) {
        if(!stateStack.isEmpty()) {
            stateStack.pop();
            stateStack.push(state);
        }
    }
}
