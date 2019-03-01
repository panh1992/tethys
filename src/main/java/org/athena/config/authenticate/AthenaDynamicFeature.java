package org.athena.config.authenticate;

import com.google.common.base.Strings;

import javax.ws.rs.Path;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.lang.reflect.Method;

/**
 * 通过数据库配置 角色 资源 权限 对应关系  扩展
 */
public class AthenaDynamicFeature implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {

        Class clazz = resourceInfo.getResourceClass();

        String path = null;
        if (clazz.isAnnotationPresent(Path.class)) {
            Path classPath = (Path) clazz.getAnnotation(Path.class);
            path = classPath.value();
        }

        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(Path.class)) {
            Path methodPath = method.getAnnotation(Path.class);
            path = path + methodPath.value();
        }

        if (!Strings.isNullOrEmpty(path)) {
            System.out.println("资源路径:" + path);
        }


        System.out.println(context.hashCode() + context.toString());

    }

}
