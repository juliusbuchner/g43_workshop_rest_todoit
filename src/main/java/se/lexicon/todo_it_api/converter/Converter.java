package se.lexicon.todo_it_api.converter;

import java.util.Collection;
import java.util.List;

public interface Converter <E, F, D>{
    D toDataObject(E entity);

    E toEntity(F form);

    List<D> toDataObjects(Collection<E> entities);

    List<E> toEntities(Collection<F> forms);
}
