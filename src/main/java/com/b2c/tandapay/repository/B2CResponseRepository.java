package com.b2c.tandapay.repository;
import com.b2c.tandapay.dto.B2CResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface B2CResponseRepository extends MongoRepository<B2CResponse, String> {
}
