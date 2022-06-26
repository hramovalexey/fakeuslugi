package com.fakeuslugi.dao;

import com.fakeuslugi.TestEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Repository
@Transactional
public class TestDao {
    @Autowired
    private SessionFactory sessionFactory;


    public void saveTest(TestEntity ent) {
        sessionFactory.getCurrentSession().save(ent);
    }

    public TestEntity findById(long idRequest) {
        Query<TestEntity> query = sessionFactory.getCurrentSession().createQuery("from TestEntity as t where t.id = :idRequest");
        query.setParameter("idRequest", idRequest);
        TestEntity result = query.uniqueResult();
        return result;
    }
}
