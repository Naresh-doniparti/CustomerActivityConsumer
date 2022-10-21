package com.wf.wimt.CustomerActivityConsumer.repository;

import com.wf.wimt.CustomerActivityConsumer.model.CustomerActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerActivityRepository extends MongoRepository<CustomerActivity, String> {
}
