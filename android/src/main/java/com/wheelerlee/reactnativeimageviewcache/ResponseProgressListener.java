package com.wheelerlee.reactnativeimageviewcache;

public interface ResponseProgressListener {
    public void update(long received, long total, int progress);
}
