package com.example.FloorLayout.repo;

import com.example.FloorLayout.domain.Layout;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LayoutRepo extends CrudRepository<Layout, Long> {
    Layout findLayoutById(Integer id);

    void deleteById(Integer id);

    List <Layout> findByName(String filter);
}
