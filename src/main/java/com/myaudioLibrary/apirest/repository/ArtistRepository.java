package com.myaudioLibrary.apirest.repository;

import com.myaudioLibrary.apirest.model.Artist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByNameIgnoreCase(String name);
    Page<Artist> findByNameIsContaining(String name, Pageable pr);
};
