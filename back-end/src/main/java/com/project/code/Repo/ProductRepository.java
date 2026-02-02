package com.project.code.Repo;

import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 2. Add custom query methods:

    // findAll: retrieve all products.
    List<Product> findAll();

    // findByCategory: retrieve products by their category.
    List<Product> findByCategory(String category);

    // findByPriceBetween: retrieve products within a price range.
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // findBySku: retrieve a product by its SKU.
    Product findBySku(String sku);

    // findByName: retrieve a product by its name.
    Product findByName(String name);

    // findByNameLike: retrieve products by a name pattern for a specific store.
    @Query("SELECT p FROM Product p JOIN p.inventories i WHERE i.store.id = :storeId AND p.name LIKE %:pname%")
    List<Product> findByNameLike(@Param("storeId") Long storeId, @Param("pname") String pname);
}