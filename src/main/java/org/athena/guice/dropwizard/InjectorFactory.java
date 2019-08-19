package org.athena.guice.dropwizard;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

import java.util.List;

public interface InjectorFactory {

    Injector create(final Stage stage, final List<Module> modules);

}
