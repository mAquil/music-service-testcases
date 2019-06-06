package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exceptions.TrackNotFoundException;
import com.stackroute.muzixservice.repository.MusicRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MusicServiceTest {
    private Music music;
    Optional optional;

    //Create a mock for UserRepository
    @Mock
    private MusicRepository musicRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    private MusicServiceImpl musicService;
    List<Music> list= null;


    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        music = new Music();
        music.setId(3);
        music.setTrackName("hey baby");
        music.setTrackComments("awesome song");
        list = new ArrayList<>();
        list.add(music);
        optional = Optional.of(music);
    }

    @Test
    public void saveTrackTestSuccess() throws TrackAlreadyExistsException {
        when(musicRepository.save((Music) any())).thenReturn(music);
        Music savedUser = musicService.saveTrack(music);
        Assert.assertEquals(music,savedUser);
        //verify here verifies that userRepository save method is only called once
        verify(musicRepository,times(1)).save(music);

    }

    @Test(expected = TrackAlreadyExistsException.class)
    public void saveTrackTestFailure() throws TrackAlreadyExistsException {
        when(musicRepository.save((Music) any())).thenReturn(null);
        Music savedUser = musicService.saveTrack(music);
        System.out.println("savedUser" + savedUser);
        Assert.assertEquals(music,savedUser);

    }

    @Test
    public void updateTrackTest() throws TrackNotFoundException {
        when(musicRepository.findById(music.getId())).thenReturn(optional);
        Music music1=new Music(music.getId(),music.getTrackName(),music.getTrackComments());
        musicRepository.save(music1);
        Music savedTrack = musicService.updateTrack(music1,music.getId());
        Assert.assertEquals(music1,savedTrack);
    }

    @Test
    public void deleteTrackTest() throws TrackNotFoundException {
        when(musicRepository.findById(music.getId())).thenReturn(optional);
        Music deletedtrack=musicService.deleteTracks(music.getId());
        Assert.assertEquals(3,deletedtrack.getId());
        verify(musicRepository,times(1)).deleteById(music.getId());
    }

    @Test
    public void getTrackByName() throws TrackNotFoundException {
        when(musicRepository.getTrackByName(music.getTrackName())).thenReturn(music);
        Music savedUser = (Music) musicService.getTrackByName(music.getTrackName());
        Assert.assertEquals(3,savedUser.getId());
    }

    @Test
    public void getAllTrack(){
        musicRepository.save(music);
        //stubbing the mock to return specific data
        when(musicRepository.findAll()).thenReturn(list);
        List<Music> userlist = musicService.displayAllTracks();
        Assert.assertEquals(list,userlist);
    }
}