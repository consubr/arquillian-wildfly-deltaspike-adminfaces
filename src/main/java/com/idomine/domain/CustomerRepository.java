package com.idomine.domain;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface CustomerRepository extends EntityRepository<Customer, Long>
{

    
}
