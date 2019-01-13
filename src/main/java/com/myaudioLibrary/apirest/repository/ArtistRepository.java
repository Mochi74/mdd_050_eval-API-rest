package com.myaudioLibrary.apirest.repository;

import com.myaudioLibrary.apirest.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
};
