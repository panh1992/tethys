package org.athena.guice.jersey2;

import com.google.inject.Binding;
import com.google.inject.Guice;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * An {@link ActiveDescriptor} that is backed by a {@link Guice} {@link Binding}.
 */
public class GuiceBindingDescriptor<T> extends AbstractActiveDescriptor<T> {

    private final Class<?> clazz;

    private final Binding<T> binding;

    public GuiceBindingDescriptor(Type type, Class<?> clazz, Set<Annotation> qualifiers, Binding<T> binding) {
        super(Collections.singleton(type), GuiceScope.class, BindingUtils.getNameFromAllQualifiers(qualifiers, clazz),
                qualifiers, DescriptorType.CLASS, DescriptorVisibility.NORMAL, 0, false,
                null, null, Collections.emptyMap());
        this.clazz = clazz;
        this.binding = binding;
        setImplementation(clazz.getName());
    }

    @Override
    public Class<?> getImplementationClass() {
        return clazz;
    }

    @Override
    public Type getImplementationType() {
        return clazz;
    }

    @Override
    public T create(ServiceHandle<?> root) {
        return binding.getProvider().get();
    }

    @Override
    public boolean isReified() {
        return true;
    }

    @Override
    public Set<Annotation> getQualifierAnnotations() {
        Set<Annotation> qualifierAnnotations = super.getQualifierAnnotations();
        return qualifierAnnotations.isEmpty() ? qualifierAnnotations : lenientSet(qualifierAnnotations);
    }

    private static <E> Set<E> lenientSet(Collection<E> entries) {
        LenientHashSet<E> lenientHashSet = new LenientHashSet<>();
        lenientHashSet.addAll(entries);
        return lenientHashSet;
    }

    /**
     * Because sun's runtime version of Annotation doesn't know anything about GuiceQualifier we
     * switch the equality check around so that equals in GuiceQualifier is used instead.
     * Ugly workaround until hk2 supports something similar to guice's AnnotationTypeStrategy.
     */
    @SuppressWarnings("serial")
    private static class LenientHashSet<E> extends HashSet<E> {

        @Override
        public boolean containsAll(Collection<?> foreignEntries) {
            for (Object foreignEntry : foreignEntries) {
                boolean matched = false;
                for (E containedEntry : this) {
                    if (containedEntry.equals(foreignEntry)) {
                        matched = true;
                        break;
                    }
                }

                if (!matched) {
                    return false;
                }
            }
            return true;
        }

    }
}