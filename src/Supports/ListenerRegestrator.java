package Supports;

import java.util.ArrayList;
import java.util.List;

public class ListenerRegestrator {

    List listListeners = new ArrayList();

    public void addListener(ListenerSupport listenerSupport){
        this.listListeners.add(listenerSupport);
    }

    protected void supportAction(int numberOfSupport){
        ListenerSupport listenerSupport = (ListenerSupport) listListeners.get(numberOfSupport);
        listenerSupport.useSupport();
    }

}
