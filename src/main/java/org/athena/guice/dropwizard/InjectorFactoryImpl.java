package org.athena.guice.dropwizard;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

import java.util.List;

public class InjectorFactoryImpl implements InjectorFactory {

    @Override
    public Injector create(final Stage stage, final List<Module> modules) {
        return Guice.createInjector(stage,modules);
    }

}
