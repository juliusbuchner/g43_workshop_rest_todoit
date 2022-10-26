package se.lexicon.todo_it_api.converter;

import java.util.Collection;

public interface Converter <E, F, D>{
    D toDataObject(E entity);

    E toEntity(F form);

    Collection<D> toDataObjects(Collection<E> entities);

    Collection<E> toEntities(Collection<F> forms);
}
