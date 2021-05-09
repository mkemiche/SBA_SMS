package jpa.interfaces;

/**
 * @author mkemiche
 * @created 08/05/2021
 */

@FunctionalInterface
public interface Delete<E> {
    void remove(E e);
}
