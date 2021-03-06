package org.demoiselle.lifecycle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotate other annotations with this one to
 * mark them as lifecycle annotations, meaning
 * the lifecycle processor of the framework will
 * read them and fire events based on their represented
 * lifecycle stages.
 *
 * @author SERPRO
 */
@Inherited
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface LifecycleAnnotation {

}
