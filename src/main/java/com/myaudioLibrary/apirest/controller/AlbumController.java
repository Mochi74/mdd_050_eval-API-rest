package com.myaudioLibrary.apirest.controller;

import com.myaudioLibrary.apirest.model.Album;
import com.myaudioLibrary.apirest.repository.AlbumRepository;
import com.myaudioLibrary.apirest.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Album getById(@PathVariable("id") Long id) {
        Album a=albumRepository.findOne(id);
        if (a==null) {
            /* check if album exist */
            throw new EntityNotFoundException("album with id:" + id + " does not exist !");
        }
        return a;
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Album a=albumRepository.findOne(id);
        if (a==null) {
            /* check if album exist */
            throw new EntityNotFoundException("album with id:" + id + " does not exist !");
        }
        albumRepository.delete(id);
    }

    @RequestMapping(value = "",method = RequestMethod.POST, consumes = "application/json")
    public Album add(@RequestBody Album album) {
        //album.setArtistId(album.getArtist().getId());
        if (artistRepository.findOne(album.getArtist().getId()) == null) {
            throw new EntityNotFoundException("Artist with id:" + album.getArtist().getId() + " does not exist !");
        }
        if (album.getTitle().equals("")) {
            throw new IllegalArgumentException("Album title can't be empty !");
        }
        return albumRepository.save(album);
    }


}