package com.example.demo.small.mock;

import com.example.demo.common.service.port.UuidHolder;

public class TestUuidHolder implements UuidHolder {

    private final String uuid;

    public TestUuidHolder(String uuid) {
        this.uuid = uuid;
    }

    public String random() {
        return this.uuid;
    }
}
