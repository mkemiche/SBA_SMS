package jpa.interfaces;

import com.sun.istack.Nullable;

import java.util.List;

/**
 * @author mkemiche
 * @created 08/05/2021
 */

@FunctionalInterface
public interface FindAllRecords<T, E> {
    List<T> getAllRecords(@Nullable E e);
}
