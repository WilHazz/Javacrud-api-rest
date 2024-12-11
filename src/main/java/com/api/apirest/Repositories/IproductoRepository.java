package com.api.apirest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.apirest.Entities.Producto;

public interface IproductoRepository extends JpaRepository<Producto, Long> {

}
