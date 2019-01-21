package com.myaudioLibrary.apirest.controller;

import com.myaudioLibrary.apirest.model.Album;
import com.myaudioLibrary.apirest.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Album getById(@PathVariable("id") Long id) {
        return albumRepository.findOne(id);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
            albumRepository.delete(id);
    }

    @RequestMapping(value = "",method = RequestMethod.POST, consumes = "application/json")
    public Album add(@RequestBody Album album) {
        //album.setArtistId(album.getArtist().getId());
        return albumRepository.save(album);
    }


}