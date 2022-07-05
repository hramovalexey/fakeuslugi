package com.fakeuslugi.daoutil;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * Implementation of {@link SimpleQueryProcessorAbstract} class for HQL requests processing like:
 * from Entity as t where s.fieldName = :request
 * @param <T> the type of the returned object
 * @param <S> the type of the input parameter
 */

public class QueryFromWhereUniqueProcessor<T, S> extends SimpleQueryProcessorAbstract<T, S> {

    private SessionFactory sessionFactory;

    public QueryFromWhereUniqueProcessor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected Query<T> buildQuery(Class<T> entity, String fieldName) {
        return sessionFactory.getCurrentSession().createQuery(String.format
                ("from %s as t where t.%s = :request", entity.getName(), fieldName));
    }

    @Override
    protected void setQueryParam(Query<T> query, S paramValue) {
        query.setParameter("request", paramValue);
    }

    @Override
    protected T getResult(Query<T> query) {
        return query.uniqueResult();
    }
}
