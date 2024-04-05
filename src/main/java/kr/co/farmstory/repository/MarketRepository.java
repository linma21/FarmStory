package kr.co.farmstory.repository;

import kr.co.farmstory.entity.Product;
import kr.co.farmstory.repository.custom.MarketRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Product, Integer>, MarketRepositoryCustom {
}
