package com.myaudioLibrary.apirest.controller;

import com.myaudioLibrary.apirest.model.Artist;
import com.myaudioLibrary.apirest.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping("/count")
    public Long countArtist() {
        return artistRepository.count();
    }

    @RequestMapping("/{id}")
    public Artist getById(@PathVariable("id") Long id) {
        return artistRepository.findOne(id);
    }

}