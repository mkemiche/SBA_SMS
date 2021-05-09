package jpa.interfaces;

/**
 * @author mkemiche
 * @created 08/05/2021
 */

@FunctionalInterface
public interface Create<T> {
    void create(T t);
}
