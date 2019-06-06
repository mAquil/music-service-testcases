package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exceptions.TrackNotFoundException;

import java.util.List;

public interface MusicService {
    public Music saveTrack(Music music) throws TrackAlreadyExistsException;
    public List<Music> displayAllTracks();
    public Music displayTrackById(int id);
    public Music updateTrack(Music music, int id);
    public Music deleteTracks(int id)throws TrackNotFoundException;
    public Music getTrackByName(String trackName) throws TrackNotFoundException;


}



