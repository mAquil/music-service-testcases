package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exceptions.TrackNotFoundException;
import com.stackroute.muzixservice.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@CacheConfig(cacheNames = "music")
public class MusicServiceImpl implements MusicService {
    MusicRepository musicRepository;
    Music music=null;

    public void simulateDelay(){
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public MusicServiceImpl(MusicRepository musicRepository){
        this.musicRepository=musicRepository;
    }

    @Override
    public Music saveTrack(Music music) throws TrackAlreadyExistsException{
        if (musicRepository.existsById(music.getId()))
        {
            throw new TrackAlreadyExistsException("Track Already Exists");
        }
        Music saveTracks=musicRepository.save(music);
        if(saveTracks==null)
        {
            throw new TrackAlreadyExistsException("Track Already Exists");
        }
        return saveTracks;
    }

    @Cacheable
    @Override
    public List<Music> displayAllTracks() {
        simulateDelay();
        List<Music> musicList=(List<Music>)musicRepository.findAll();
        return musicList;
    }

    @Override
    public Music displayTrackById(int id) {
        return null;
    }

    @CachePut
    @Override
    public Music deleteTracks(int id){
        Music music=null;
        Optional optional=musicRepository.findById(id);
        if(optional.isPresent())
        {
            music = musicRepository.findById(id).get();
            musicRepository.deleteById(id);
        }
        return music;
    }

    @CachePut
    @Override
    public Music updateTrack(Music music, int id) {
        Music music1=null;
        Optional optional=musicRepository.findById(id);
        if(optional.isPresent())
        {
            music1=musicRepository.findById(id).get();
        }
        else
        {
            try {
                throw new TrackNotFoundException("Track does not exist");
            } catch (TrackNotFoundException e) {
                e.printStackTrace();
            }
        }
        return music1;


    }

    @Override
    public Music getTrackByName(String trackName)throws TrackNotFoundException{
        return musicRepository.getTrackByName(trackName);
    }

    @PostConstruct
    public  void  loadData(){
        musicRepository.save(Music.builder().id(6).trackName("mere sang to chal zara").trackComments("new song").build());
        musicRepository.save(Music.builder().id(7).trackName("yaariyan").trackComments("another song").build());
        musicRepository.save(Music.builder().id(8).trackName("aami je tomaar").trackComments("monjo's song").build());
    }
}
