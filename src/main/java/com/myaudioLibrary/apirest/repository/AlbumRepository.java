package com.myaudioLibrary.apirest.repository;

import com.myaudioLibrary.apirest.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
};
