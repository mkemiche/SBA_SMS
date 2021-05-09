package jpa.interfaces;

/**
 * @author mkemiche
 * @created 08/05/2021
 */

@FunctionalInterface
public interface Update<T> {
    void update(T t);
}
