package com.cloudpi.cloudpi_backend.configuration;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.Query;
import org.hibernate.query.spi.QueryImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class RandomLongIdGenerator implements IdentifierGenerator, Configurable {

    public static final String generatorName = "myGenerator";
    private final Integer queueSize = 100;
    private final ArrayBlockingQueue<Long> idQueue = new ArrayBlockingQueue<>(queueSize);
    private Type type;
    private final Random random = new Random();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

        if(idQueue.isEmpty()) {
            while (idQueue.size() < 90) {
                addIdsToQueue(session, obj, 100 - idQueue.size());
            }
        }

        return idQueue.poll();
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.type = type;
    }

    private void addIdsToQueue(SharedSessionContractImplementor session, Object obj, int capacity) {
        List<Long> randomIds = new ArrayList<>(capacity);
        for(int i = 0; i < capacity; i++) {
            long candidate = random.nextLong();
            while (randomIds.contains(candidate)) {
                candidate = random.nextLong();
            }
            randomIds.add(candidate);
        }

        Query<Long> query = session.createNamedQuery("SELECT :id FROM :table WHERE :id IN :ids");

        var entityPersister = session.getEntityPersister(type.getName(), obj);
        query.setParameter("table", type.getName());
        query.setParameter("id", entityPersister.getIdentifierPropertyName());
        query.setParameter("ids", randomIds);

        var idAlreadyInTable = query.getResultList();
        for(long id : randomIds) {
            if(!idAlreadyInTable.contains(id)) {
                idQueue.offer(id);
            }
        }
    }
}
