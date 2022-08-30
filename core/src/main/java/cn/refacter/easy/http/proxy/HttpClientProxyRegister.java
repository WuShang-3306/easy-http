package cn.refacter.easy.http.proxy;

import cn.refacter.easy.http.annotations.HttpRequest;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:26
 */
public class HttpClientProxyRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = this.getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(HttpRequest.class));
        Set<String> basePackages = this.getBasePackages(metadata);
        for (String basePackage : basePackages) {
            candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
        }
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                // verify annotated class is an interface
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Map<String, Object> attributes = annotationMetadata
                        .getAnnotationAttributes(HttpRequest.class.getCanonicalName());
                try {
                    this.register(registry, annotationMetadata, attributes);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Set<String> basePackages = new HashSet<>();
        basePackages.add(
                ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        return basePackages;
    }

    private void register(BeanDefinitionRegistry registry,
                          AnnotationMetadata annotationMetadata, Map<String, Object> attributes) throws ClassNotFoundException {
        String className = annotationMetadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition(HttpClientProxyFactoryBean.class);
        String name = this.getName(annotationMetadata);
        definition.addPropertyValue("name", name);
        definition.addPropertyValue("contextId", name);
        definition.addPropertyValue("type", className);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        String alias = name + "needProxy";
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, className);
        beanDefinition.setPrimary(true);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className,
                new String[] { alias });
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    private String getName(AnnotationMetadata annotationMetadata) throws ClassNotFoundException {
        String name = annotationMetadata.getClassName();
        Class<?> aClass = Class.forName(name);
        String simpleName = aClass.getSimpleName();
        simpleName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
        return simpleName;
    }

    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
