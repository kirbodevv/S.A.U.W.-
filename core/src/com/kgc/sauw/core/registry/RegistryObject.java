package com.kgc.sauw.core.registry;

public abstract class RegistryObject {
    protected int id;
    protected String fullId;
    protected String stringId;
    protected String namespace;

    public int getId() {
        return id;
    }

    public String getStringId() {
        return stringId;
    }

    public String getNamespace() {
        return namespace;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected void setStringId(String stringId) {
        this.stringId = stringId;
    }

    protected void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    protected void setFullId(String fullId) {
        this.fullId = fullId;
    }

    public String getFullId() {
        return fullId;
    }

    /**
     * The method is called after the object is registered
     */
    public abstract void init();
}
