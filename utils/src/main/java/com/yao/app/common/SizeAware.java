package com.yao.app.common;

public interface SizeAware {

    long getUsedHeapSpace();

    long getUsedStackSpace();
}
