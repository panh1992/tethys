package org.athena.guice.jersey2;

import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.internal.Nullability;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.NamedImpl;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.internal.ConstantActiveDescriptor;

import javax.annotation.Nullable;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class BindingUtils {

    private BindingUtils() {
    }

    /**
     * Creates and returns a {@link InjectionResolver} for {@link javax.inject.Inject}.
     *
     * @see javax.inject.Inject
     * @see InjectionResolver
     */
    public static ActiveDescriptor<InjectionResolver<javax.inject.Inject>> newThreeThirtyInjectionResolverDescriptor(
            ServiceLocator locator) {
        GuiceThreeThirtyResolver resolver = new GuiceThreeThirtyResolver(locator);

        Set<Annotation> qualifiers = Collections.singleton(new NamedImpl(InjectionResolver.SYSTEM_RESOLVER_NAME));

        return newActiveDescriptor(locator, resolver, qualifiers, InjectionResolver.SYSTEM_RESOLVER_NAME,
                javax.inject.Inject.class);
    }

    /**
     * Creates and returns a {@link InjectionResolver} for {@link com.google.inject.Inject}
     *
     * @see #newThreeThirtyInjectionResolverDescriptor(ServiceLocator)
     * @see com.google.inject.Inject
     * @see InjectionResolver
     */
    public static ActiveDescriptor<InjectionResolver<com.google.inject.Inject>> newGuiceInjectionResolverDescriptor(
            ServiceLocator locator, ActiveDescriptor<? extends InjectionResolver<?>> threeThirtyResolver) {

        GuiceInjectionResolver resolver = new GuiceInjectionResolver(threeThirtyResolver);
        Set<Annotation> qualifiers = Collections.emptySet();

        return newActiveDescriptor(locator, resolver, qualifiers, GuiceInjectionResolver.GUICE_RESOLVER_NAME,
                com.google.inject.Inject.class);
    }

    /**
     * @see #newThreeThirtyInjectionResolverDescriptor(ServiceLocator)
     * @see #newGuiceInjectionResolverDescriptor(ServiceLocator, ActiveDescriptor)
     */
    private static <T extends Annotation> ActiveDescriptor<InjectionResolver<T>> newActiveDescriptor(
            ServiceLocator locator, InjectionResolver<T> resolver, Set<Annotation> qualifiers, String name,
            Class<? extends T> clazz) {

        Set<Type> contracts = Collections.singleton(new ParameterizedTypeImpl(InjectionResolver.class, clazz));

        return new ConstantActiveDescriptor<>(resolver, contracts, Singleton.class, name, qualifiers,
                DescriptorVisibility.NORMAL, 0, null, null, null,
                locator.getLocatorId(), null);
    }

    /**
     * Returns {@code true} if the given {@link Injectee} can be {@code null}.
     *
     * @see Optional
     * @see Injectee#isOptional()
     * @see Nullable
     * @see com.google.inject.Inject#optional()
     * @see Nullability#allowsNull(Annotation[])
     */
    public static boolean isNullable(Injectee injectee) {
        // HK2's optional
        if (injectee.isOptional()) {
            return true;
        }

        // Guice's optional
        AnnotatedElement element = injectee.getParent();
        if (isGuiceOptional(element)) {
            return true;
        }

        // Any @Nullable?
        int position = injectee.getPosition();

        if (element instanceof Field) {
            return Nullability.allowsNull(element.getAnnotations());
        } else if (element instanceof Method) {
            Annotation[][] annotations = ((Method) element).getParameterAnnotations();
            return Nullability.allowsNull(annotations[position]);
        } else if (element instanceof Constructor<?>) {
            Annotation[][] annotations = ((Constructor<?>) element).getParameterAnnotations();
            return Nullability.allowsNull(annotations[position]);
        }

        return false;
    }

    /**
     * Returns {@code true} if the given {@link AnnotatedElement} has a
     * {@link com.google.inject.Inject} {@link Annotation} and it's marked
     * as being optional.
     *
     * @see com.google.inject.Inject#optional()
     */
    private static boolean isGuiceOptional(AnnotatedElement element) {
        com.google.inject.Inject inject = element.getAnnotation(com.google.inject.Inject.class);

        if (inject != null) {
            return inject.optional();
        }

        return false;
    }

    /**
     * Returns {@code true} if the {@link Injectee} has a HK2 SPI
     * {@link org.jvnet.hk2.annotations.Contract} annotation.
     *
     * @see org.jvnet.hk2.annotations.Contract
     */
    public static boolean isHk2Contract(Injectee injectee) {
        Type type = injectee.getRequiredType();
        return isContact(type, org.jvnet.hk2.annotations.Contract.class);
    }


    /**
     * Returns {@code true} if the {@link Injectee} has a Jersey SPI
     * {@link org.glassfish.jersey.spi.Contract} annotation.
     *
     * @see org.glassfish.jersey.spi.Contract
     */
    public static boolean isJerseyContract(Injectee injectee) {
        Type type = injectee.getRequiredType();
        return isContact(type, org.glassfish.jersey.spi.Contract.class);
    }

    private static boolean isContact(Type type, Class<? extends Annotation> annotationType) {
        if (type instanceof Class<?>) {
            return ((Class<?>) type).isAnnotationPresent(annotationType);
        }

        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            return isContact(rawType, annotationType);
        }

        return false;
    }

    /**
     * Turns a {@link Guice} {@link com.google.inject.name.Named} into a
     * JSR-330's {@link javax.inject.Named} instance.
     */
    public static javax.inject.Named toThreeThirtyNamed(com.google.inject.name.Named qualifier) {
        return new NamedImpl(qualifier.value());
    }

    /**
     * Returns a {@link Set} of qualifier {@link Annotation}s for
     * the given {@link Key}.
     * NOTE: The {@link Set} will be either empty or will have
     * at most one {@link Annotation} in it.
     */
    public static <T> Set<Annotation> getQualifiers(Key<T> key) {
        Annotation annotation = key.getAnnotation();
        if (annotation != null) {

            // Replace 'com.google.inject.name.Named' with 'javax.inject.Named'
            if (annotation instanceof com.google.inject.name.Named) {
                annotation = toThreeThirtyNamed((com.google.inject.name.Named) annotation);
            }

            Class<? extends Annotation> type = annotation.annotationType();
            if (type.isAnnotationPresent(Qualifier.class)) {
                return Collections.singleton(annotation);
            }

            return Collections.singleton(new GuiceQualifier<>(key));
        }

        Class<? extends Annotation> annotationType = key.getAnnotationType();
        if (annotationType != null) {
            return Collections.singleton(new GuiceQualifier<>(key));
        }

        return Collections.emptySet();
    }

    /**
     * NOTE: There can be only one {@link Annotation} that is a {@link Qualifier} or {@link BindingAnnotation}.
     * They're the same but HK2 does not know about {@link BindingAnnotation}.
     *
     * @see Qualifier
     * @see BindingAnnotation
     * @see javax.inject.Named
     * @see com.google.inject.name.Named
     */
    private static Set<Annotation> getQualifiers(Injectee injectee) {
        // JSR 330's @Qualifier
        Set<Annotation> qualifiers = injectee.getRequiredQualifiers();
        if (!qualifiers.isEmpty()) {
            return qualifiers;
        }

        AnnotatedElement element = injectee.getParent();
        int position = injectee.getPosition();

        // Guice's @BindingAnnotation is the same as @Qualifier
        Annotation annotation = getBindingAnnotation(element, position);
        if (annotation != null) {
            return Collections.singleton(annotation);
        }

        return Collections.emptySet();
    }

    /**
     * Creates and returns a {@link Key} from the given {@link Injectee}.
     */
    public static Key<?> toKey(Injectee injectee) {
        Type type = injectee.getRequiredType();
        Set<Annotation> qualifiers = getQualifiers(injectee);
        return newKey(type, qualifiers);
    }

    /**
     * Creates and returns a {@link Key} for the given {@link Type} and {@link Set} of {@link Annotation}s.
     */
    private static Key<?> newKey(Type type, Set<? extends Annotation> qualifiers) {
        if (qualifiers.isEmpty()) {
            return Key.get(type);
        }

        // There can be only one qualifier.
        if (qualifiers.size() == 1) {
            for (Annotation first : qualifiers) {
                return Key.get(type, first);
            }
        }

        return null;
    }

    /**
     * Returns a {@link BindingAnnotation} for the given {@link AnnotatedElement} and position.
     */
    private static Annotation getBindingAnnotation(AnnotatedElement element, int position) {
        if (element instanceof Field) {
            return getBindingAnnotation(element.getAnnotations());
        }

        if (element instanceof Method) {
            Annotation[][] annotations = ((Method) element).getParameterAnnotations();
            return getBindingAnnotation(annotations[position]);
        }

        if (element instanceof Constructor<?>) {
            Annotation[][] annotations = ((Constructor<?>) element).getParameterAnnotations();
            return getBindingAnnotation(annotations[position]);
        }

        return null;
    }

    /**
     * Returns the first {@link Annotation} from the given array that
     * is a {@link BindingAnnotation}.
     *
     * @see BindingAnnotation
     */
    private static Annotation getBindingAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (type.isAnnotationPresent(BindingAnnotation.class)) {
                return annotation;
            }
        }

        return null;
    }

    /**
     * @see ReflectionHelper#getNameFromAllQualifiers(Set, AnnotatedElement)
     */
    public static String getNameFromAllQualifiers(Set<Annotation> qualifiers, AnnotatedElement element) {
        return ReflectionHelper.getNameFromAllQualifiers(qualifiers, element);
    }

}
