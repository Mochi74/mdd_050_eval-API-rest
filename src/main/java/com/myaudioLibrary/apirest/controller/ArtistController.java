package com.myaudioLibrary.apirest.controller;

import com.myaudioLibrary.apirest.model.Artist;
import com.myaudioLibrary.apirest.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
        Artist a = artistRepository.findOne(id);
        if(a==null) {/*id not found return 404 error */
            throw new EntityNotFoundException("artist with id:"+id+"does not exist !");
        }
        return a;


    }

    @RequestMapping(value="", params = "name")
    public Artist getByName(@RequestParam("name") String name) {
        Artist a= artistRepository.findByNameIgnoreCase(name);
        if (a == null) {/*name not found return 404 error */
            throw new EntityNotFoundException("artist with name: " + name + " is not found !");
        }
        return a;
    }


    @RequestMapping(value = "",params = "page" )
    public Page<Artist> getAllByPage(@RequestParam("page") Integer page,
                                      @RequestParam("size") Integer size,
                                      @RequestParam("sortProperty") String paramSort,
                                      @RequestParam("sortDirection") Sort.Direction direction) {
        Pageable pageRequest = new PageRequest(page, size, direction, paramSort);
        Page<Artist> p=artistRepository.findAll(pageRequest);
        return p;
    }

    @RequestMapping(value = "",params = { "name","page", "size" ,"sortProperty","sortDirection"})
    public Page<Artist> getAllByPage(@RequestParam("name") String name,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     @RequestParam("sortProperty") String paramSort,
                                     @RequestParam("sortDirection") Sort.Direction direction) {
        Pageable pageRequest = new PageRequest(page, size, direction, paramSort);
        Page<Artist> p = artistRepository.findByNameIsContaining(name, pageRequest);
        if (p == null) {/*name not found return 404 error */
            throw new EntityNotFoundException("artist with name:" + name + "does not exist !");
        }
        return p;
    }

    @RequestMapping(value = "",method = RequestMethod.POST, consumes = "application/json")
    public Artist create(@RequestBody Artist artist) {

        if (artistRepository.findByNameIgnoreCase(artist.getName())!=null) {
            /* check if artist already exist return 409 */
            throw new EntityExistsException("artist with name:" + artist.getName() + " already exist !");
        }
        return artistRepository.save(artist);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT, consumes = "application/json")
    public Artist modify(@PathVariable("id") Long id,@RequestBody Artist artist) {
        if(!artistRepository.exists(id))  {
            throw new EntityNotFoundException("artist with id:" + id + "does not exist !");
        }
        return artistRepository.save(artist);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE, consumes = "application/json")
    public void delete(@PathVariable("id") Long id, @RequestBody Artist artist) {
        if(!artistRepository.exists(id))  {
            throw new EntityNotFoundException("artist with id:" + id + "does not exist !");
        }
        if (!artist.getId().equals(id)) {
            throw new IllegalArgumentException("artist id is not reliable !");
        }
        artistRepository.delete(id);
    }




}