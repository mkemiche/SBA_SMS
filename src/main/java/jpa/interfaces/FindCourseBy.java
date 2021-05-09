package jpa.interfaces;

import java.util.List;

/**
 * @author mkemiche
 * @created 08/05/2021
 */

@FunctionalInterface
public interface FindCourseBy<T, E> {
    List<T> findCourseBy(E elt);
}
