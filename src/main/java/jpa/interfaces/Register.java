package jpa.interfaces;

import jpa.exceptions.CourseAlreadyRegistredException;
import jpa.exceptions.StudentNotFoundException;
import jpa.exceptions.UserValidationFailedException;
import org.jetbrains.annotations.NotNull;

/**
 * @author mkemiche
 * @created 08/05/2021
 */

@FunctionalInterface
public interface Register<T1, T2> {
        void register(@NotNull T1 t1, T2 t2) throws StudentNotFoundException, UserValidationFailedException, CourseAlreadyRegistredException;
}
