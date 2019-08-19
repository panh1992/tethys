package org.athena.guice.jersey2;

import com.google.inject.Guice;
import com.google.inject.Key;

import javax.inject.Qualifier;
import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * A {@link Qualifier} that is backed by a {@link Guice} {@link Key}.
 * NOTE: This class exists just for completeness and debugging.
 *
 * @see Qualifier
 * @see Key
 */
public class GuiceQualifier<T> implements Qualifier, Serializable {

    private static final long serialVersionUID = 0;

    private final Key<T> key;

    public GuiceQualifier(Key<T> key) {
        this.key = key;
    }

    /**
     * Returns the {@link Key}.
     */
    public Key<T> getKey() {
        return key;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return key.getAnnotationType();
    }

    @Override
    public int hashCode() {
        return annotationType().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;

        } else if (annotationType().isInstance(o)) {
            return true;

        } else if (!(o instanceof GuiceQualifier<?>)) {
            return false;
        }

        GuiceQualifier<?> other = (GuiceQualifier<?>) o;
        return annotationType().equals(other.annotationType());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + key + "]";
    }

}