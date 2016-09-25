package com.example.common;

/**
 * Created by ${hcc} on 2016/09/25.
 */
public interface Watched {
    void addWatcher(Watcher watcher);

    void removeWatcher(Watcher watcher);

    void notifyWatchers(UpdateWatchEnum updateWatchEnum);

    void remoteWatchers();
}
