package com.project.code.Repo;

import com.project.code.Model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Review entity using MongoDB.
 * Inherits standard CRUD operations from MongoRepository.
 */
@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    // 2. Add custom query methods:

    /**
     * Retrieves a list of reviews filtered by store ID and product ID.
     * * @param storeId the ID of the store
     * @param productId the ID of the product
     * @return a list of matching Review documents
     */
    List<Review> findByStoreIdAndProductId(Long storeId, Long productId);
}