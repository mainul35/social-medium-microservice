package com.mainul35.bsuserinfo.initialize;

public interface InitializeData {

    void initialize();

    default void doCleanUp () {

    }
}
