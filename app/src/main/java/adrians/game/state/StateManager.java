package adrians.game.state;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Stack;

import adrians.framework.GameMainActivity;

/**
 * Created by pierre on 02/03/16.
 */
public class StateManager {
    private volatile static Stack<State> stateStack = new Stack<>();
    private static Context context;
    private static Activity activity;
    private static GoogleSignInAccount signedInAccount;

    public static int GetFileActivityCode = 42;

    public synchronized static void pushState(State state) {
        stateStack.push(state);
    }

    public static State getCurrentState() {
        if (stateStack.empty())
            return null;
        return stateStack.peek();
    }

    public synchronized static void popState() {
        stateStack.pop();
        stateStack.peek().onResume();
    }

    public synchronized static void changeState(State state) {
        if (!stateStack.isEmpty()) {
            stateStack.pop();
            stateStack.push(state);
        }
    }

    public static void setContext(Context context) {
        StateManager.context = context;
    }

    public static Context getContext() {
        return StateManager.context;
    }

    public static void setActivity(GameMainActivity gameMainActivity) {
        StateManager.activity = gameMainActivity;
    }

    public static Activity getActivity() {
        return StateManager.activity;
    }

    public static void setSignedInAccount(GoogleSignInAccount signInAccount) {
        StateManager.signedInAccount = signInAccount;
    }

    public static GoogleSignInAccount getSignedInAccount() {
        return StateManager.signedInAccount;
    }
}
