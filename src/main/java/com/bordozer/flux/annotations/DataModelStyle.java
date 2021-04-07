package com.bordozer.flux.annotations;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Value.Style(builder = "new", create = "new")
@Target({ElementType.TYPE, ElementType.PACKAGE, ElementType.ANNOTATION_TYPE})
public @interface DataModelStyle {

}
