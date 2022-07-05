package com.fakeuslugi.daoutil;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
