package com.b2c.tandapay.repository;
import com.b2c.tandapay.model.B2CRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface B2CRequestRepository extends MongoRepository<B2CRequest, String> {
}
