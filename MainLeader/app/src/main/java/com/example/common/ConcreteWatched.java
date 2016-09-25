package com.example.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${hcc} on 2016/09/25.
 */
public class ConcreteWatched implements Watched {
    /*存放观察者*/
    List<Watcher> list = new ArrayList<>();
    private static ConcreteWatched concreteWatched;

    public static ConcreteWatched getInstance() {
        if (concreteWatched == null) {
            concreteWatched = new ConcreteWatched();
        }
        return concreteWatched;
    }

    @Override
    public void addWatcher(Watcher watcher) {
        list.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        list.remove(watcher);
    }

    @Override
    public void notifyWatchers(UpdateWatchEnum updateWatchEnum) {
        for (Watcher watcher : list) {
            if (watcher != null) {
                watcher.update(updateWatchEnum);
            }
        }
    }

    @Override
    public void remoteWatchers() {
        list.clear();
    }
}
