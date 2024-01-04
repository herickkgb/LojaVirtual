package com.herick.lojavirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.herick.lojavirtual.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
