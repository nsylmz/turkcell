package com.ns.deneme.neo4j.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.ns.deneme.neo4j.domain.MenuItem;

public interface MenuItemRepository extends GraphRepository<MenuItem> {

}