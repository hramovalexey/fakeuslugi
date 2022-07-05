package com.fakeuslugi.daoutil;


import org.hibernate.query.Query;


/**
 * Abstract class representing abstract method design template. Serves for processing of various
 * simple database HQL requests containing one returning type and one input parameter
 *
 * Instances of this class are supposed to be used as a singletons but this behavior is not mandatory
 *
 * @param <T> the type of the returned object
 * @param <S> the type of the input parameter
 */

public abstract class SimpleQueryProcessorAbstract<T, S> {

    protected abstract Query<T> buildQuery(Class<T> entity, String fieldName);
    protected abstract void setQueryParam(Query<T> query, S paramValue);
    protected abstract T getResult(Query<T> query);

    public T processQuery(Class<T> entity, String fieldName, S paramValue) {
        Query<T> query = buildQuery(entity, fieldName);
        setQueryParam(query, paramValue);
        T result = getResult(query);
        return result;
    }
}
