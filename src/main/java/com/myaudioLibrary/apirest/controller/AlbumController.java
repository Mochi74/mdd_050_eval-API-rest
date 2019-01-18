package com.myaudioLibrary.apirest.controller;

import com.myaudioLibrary.apirest.model.Album;
import com.myaudioLibrary.apirest.model.Artist;
import com.myaudioLibrary.apirest.repository.AlbumRepository;
import com.myaudioLibrary.apirest.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping(value="/{id}")
    public Album getById(@PathVariable("id") Long id) {
        return albumRepository.findOne(id);
    }
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE, consumes = "application/JSON")
    public void delete(@PathVariable("id") Long id,@RequestBody Album album) {
        if (album.getId()==id) {
            albumRepository.delete(id);
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST, consumes = "application/JSON")
    public Album add(@RequestBody Album album) {
        //album.setArtistId(album.getArtist().getId());
        return albumRepository.save(album);
    }


}