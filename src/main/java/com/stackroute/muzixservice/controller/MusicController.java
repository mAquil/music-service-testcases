package com.stackroute.muzixservice.controller;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exceptions.TrackNotFoundException;
import com.stackroute.muzixservice.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value ="/api/v1")
public class MusicController {

    private MusicService musicService;
    @Autowired
    public MusicController(MusicService musicService) {

        this.musicService = musicService;
    }
    @PostMapping("/music")
    public ResponseEntity<?> saveTrack(@RequestBody Music music) throws TrackAlreadyExistsException {

        ResponseEntity responseEntity;
        try{
            musicService.saveTrack(music);
            responseEntity=new ResponseEntity<String>("Successfully saved", HttpStatus.CREATED);
        }catch(Exception exception)
        {
            responseEntity=new ResponseEntity<String>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;

    }



    @GetMapping("/music")
    public ResponseEntity<?> displayAllTracks()
    {
        List<Music> musicList=musicService.displayAllTracks();
        return new ResponseEntity<List<Music>>(musicList,HttpStatus.OK);
    }
    @GetMapping("/music/{id}")  //ideal way is to use path variable
    public ResponseEntity<?> displayTracksById(@PathVariable int id)
    {
       Music  musicList=musicService.displayTrackById(id);
        return new ResponseEntity<Music>(musicList,HttpStatus.OK);
    }

    @DeleteMapping("/music/{id}")
    public ResponseEntity<?> deleteTracks(@PathVariable int id) throws TrackNotFoundException
    {
        musicService.deleteTracks(id);
        return new ResponseEntity<String>("Deleted Successfully",HttpStatus.CREATED);
    }

    @PutMapping("/music/{id}")  // use patch mapping here if u r just updating some part of the track n not the whole track
    public ResponseEntity<?> updateTrack(@RequestBody Music music,@PathVariable int id) throws TrackNotFoundException {
        //  try{
        Music music1= (Music) musicService.updateTrack(music,id);
        return new ResponseEntity<Music>(music1,HttpStatus.CREATED);}

}

