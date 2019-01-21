package com.myaudioLibrary.apirest.controller;

import com.myaudioLibrary.apirest.model.Artist;
import com.myaudioLibrary.apirest.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping("/count")
    public Long countArtist() {
        return artistRepository.count();
    }


    @RequestMapping(value="")
    public List<Artist> getAll() {
        return artistRepository.findAll();
    }
    @RequestMapping(value="/{id}")
    public Artist getById(@PathVariable("id") Long id) {
        Artist result = artistRepository.findOne(id);
        if(result==null) {/*id not found return 404 error */
   //         throw new ResponseStatusException(
   //                 HttpStatus.NOT_FOUND, "Artist Not Found");
        }
        return result;


    }

    @RequestMapping(value="", params = "name")
    public Artist getByName(@RequestParam("name") String name) {
        return artistRepository.findByNameIgnoreCase(name);
    }


    @RequestMapping(value = "",params = "page" )
    public Page<Artist> getAllByPage(@RequestParam("page") Integer page,
                                      @RequestParam("size") Integer size,
                                      @RequestParam("sortProperty") String paramSort,
                                      @RequestParam("sortDirection") Sort.Direction direction) {
        Pageable pageRequest = new PageRequest(page, size, direction, paramSort);
        return artistRepository.findAll(pageRequest);
    }

    @RequestMapping(value = "",params = { "name","page", "size" ,"sortProperty","sortDirection"})
    public Page<Artist> getAllByPage(@RequestParam("name") String name,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     @RequestParam("sortProperty") String paramSort,
                                     @RequestParam("sortDirection") Sort.Direction direction) {
        Pageable pageRequest = new PageRequest(page, size, direction, paramSort);
        return artistRepository.findByNameIsContaining(name,pageRequest);
    }

    @RequestMapping(value = "",method = RequestMethod.POST, consumes = "application/json")
    public Artist create(@RequestBody Artist artist) {
        return artistRepository.save(artist);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT, consumes = "application/json")
    public Artist modify(@RequestBody Artist artist) {
        return artistRepository.save(artist);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE, consumes = "application/json")
    public void delete(@PathVariable("id") Long id, @RequestBody Artist artist) {
        if(artist.getId()==id) {
            artistRepository.delete(id);
        }
    }




}