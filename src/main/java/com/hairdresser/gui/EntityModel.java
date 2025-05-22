package com.hairdresser.gui;

import java.io.Serializable;

public abstract class EntityModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public abstract String toJson();

    @Override
    public abstract String toString();
}