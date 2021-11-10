package com.epam.esm.linkbuilder.impl;

import com.epam.esm.linkbuilder.LinkBuilder;
import org.springframework.hateoas.RepresentationModel;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * @author Ivan Velichko
 * @date 03.11.2021 12:17
 */
public abstract class AbstractLinkBuilder<T extends RepresentationModel<T>> implements LinkBuilder<T> {
    protected static final String DELETE = "delete";
    protected static final String UPDATE = "update";
    protected static final String SELF = "self";

    protected void addIdLink(Class<?> controllerClass, T entity, long id, String linkName) {
        entity.add(linkTo(controllerClass).slash(id).withRel(linkName));
    }

    protected void addIdLinks(Class<?> controllerClass, T entity, long id, String... linkNames) {
        Arrays.stream(linkNames).forEach(linkName -> addIdLink(controllerClass, entity, id, linkName));
    }
}
