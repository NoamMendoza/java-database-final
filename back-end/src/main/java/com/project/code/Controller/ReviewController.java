package com.project.code.Controller;

import com.project.code.Model.Customer;
import com.project.code.Model.Review;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // 3. getReviews Method
    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(@PathVariable Long storeId, @PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        List<Review> reviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);
        List<Map<String, Object>> filteredReviews = new ArrayList<>();

        for (Review review : reviews) {
            Map<String, Object> reviewMap = new HashMap<>();
            
            // Fetch customer name using the customerId from the review
            Customer customer = customerRepository.findById(review.getCustomerId()).orElse(null);
            String customerName = (customer != null) ? customer.getName() : "Anonymous";

            reviewMap.put("comment", review.getComment());
            reviewMap.put("rating", review.getRating());
            reviewMap.put("customerName", customerName);
            
            filteredReviews.add(reviewMap);
        }

        response.put("reviews", filteredReviews);
        return response;
    }
}