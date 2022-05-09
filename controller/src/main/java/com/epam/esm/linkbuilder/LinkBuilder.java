package com.epam.esm.linkbuilder;

import org.springframework.hateoas.RepresentationModel;

/**
 * @author Ivan Velichko
 * @date 03.11.2021 12:19
 */
public interface LinkBuilder<T extends RepresentationModel<T>> {
    void addLinks(T t);
}
