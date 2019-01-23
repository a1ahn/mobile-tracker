package com.example.showblocks.Event;

public class FetchedBlockEvent {
    private String id;

    public FetchedBlockEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
