package com.kgc.sauw.core.registry;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.utils.ID;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Set;

/**
 * {@code Registry} class for simplified registration of different types of objects.
 *
 * @author Kirbo
 * @version 2.0
 */

public class Registry<I extends RegistryObject> {
    /**
     * Array of objects
     */
    private final ArrayList<I> objects = new ArrayList<>();
    /**
     * Registry group id
     */
    private final String groupID;


    /**
     * @param groupID  group id ({@linkplain com.kgc.sauw.core.utils.ID#registeredIdGroup(String, int) ID.registeredIdGroup(String, int)})
     * @param maxCount max id's count
     */
    public Registry(String groupID, int maxCount) {
        this.groupID = groupID;
        ID.registeredIdGroup(groupID, maxCount);
    }

    /**
     * @param groupID group id ({@linkplain com.kgc.sauw.core.utils.ID#registeredIdGroup(String, int) ID.registeredIdGroup(String, int)})
     */
    public Registry(String groupID) {
        this(groupID, 1200);
    }


    /**
     * A method for those who are too lazy to specify the id in the registration method.
     * Object must have {@linkplain RegistryMetadata this} annotation
     *
     * @param object object 🗿
     */
    public void register(I object) {
        if (!object.getClass().isAnnotationPresent(RegistryMetadata.class))
            throw new IllegalStateException("Object must have annotation");
        RegistryMetadata annotation = object.getClass().getAnnotation(RegistryMetadata.class);
        register(object, annotation.package_(), annotation.id());
    }

    /**
     * Registers an object
     *
     * @param object   object 🗿
     * @param package_ this is not a java package it is objects-package, can be any string
     * @param id       object string-id
     */
    public void register(I object, String package_, String id) {
        object.setId(GameContext.get(package_).registerId(groupID + ":" + id));
        object.setStringId(id);
        object.setNamespace(package_);
        object.setFullId(package_ + ":" + id);
        object.init();
        objects.add(object);
        Gdx.app.log("Registry", "registered " + groupID + ", with id \"" + object.getStringId() + "\", integer id " + object.id);

    }

    /**
     * Registers all objects with an {@linkplain RegistryMetadata annotation} from the package.
     * <p>
     * It works but doesn't work
     *
     * @param package_ java-package
     */
    @SuppressWarnings("unchecked")
    public void registerFromPackage(String package_) {
        Reflections reflections = new Reflections(package_);
        Set<Class<? extends RegistryObject>> classes = reflections.getSubTypesOf(RegistryObject.class);
        for (Class<? extends RegistryObject> object : classes) {
            if (object.isAnnotationPresent(RegistryMetadata.class)) {
                try {
                    register((I) object.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param id integer-id
     * @return true if it has an object, false if it doesn't
     */
    public boolean has(int id) {
        for (I object : objects) {
            if (object.id == id) return true;
        }
        return false;
    }

    /**
     * @param id integer-id
     * @return object by id
     */
    public I get(int id) {
        return objects.get(id);
    }

    /**
     * @param package_ objects-package
     * @return ArrayList of objects in the same package
     */
    public ArrayList<I> getAllObjectsFromPackage(String package_) {
        ArrayList<I> arrayList = new ArrayList<>();
        for (I object : objects) {
            if (object.namespace.equals(package_)) arrayList.add(object);
        }
        return arrayList;
    }

    /**
     * WHY?
     *
     * @return ArrayList with all objects (lol)
     */
    public ArrayList<I> getObjects() {
        return objects;
    }
}
