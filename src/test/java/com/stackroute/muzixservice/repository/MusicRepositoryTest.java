package com.stackroute.muzixservice.repository;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.exceptions.TrackNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MusicRepositoryTest {
        @Autowired
        private MusicRepository musicRepository;
        private Music music;

        @Before
        public void setUp()
        {
            music = new Music();
            music.setId(1);
            music.setTrackName("aawarapan");
            music.setTrackComments("new song");

        }

        @After
        public void tearDown(){

            musicRepository.deleteAll();
        }


        @Test
        public void testSaveTrack(){
            musicRepository.save(music);
            Music fetchUser = musicRepository.findById(music.getId()).get();
            Assert.assertEquals(1,fetchUser.getId());

        }

        @Test
        public void testSaveUserFailure(){
            Music testUser = new Music(2,"dil mei ho tum","favourite song");
            musicRepository.save(music);
            Music fetchUser = musicRepository.findById(music.getId()).get();
            Assert.assertNotSame(testUser,music);
        }


        @Test
        public void testDisplayAllTracks(){
            Music u = new Music(3,"bulawe tujhe yar aj meri galiyan","gonjo's favourite");
            Music u1 = new Music(4,"tere bin","good song");
            musicRepository.save(u);
            musicRepository.save(u1);

            List<Music> list = (List<Music>) musicRepository.findAll();
            Assert.assertEquals("tere bin",list.get(1).getTrackName());

        }

    @Test
    public void testDisplayAllTracksFailure(){
        Music u = new Music(3,"bulawe tujhe yar aj meri galiyan","gonjo's favourite");
        Music u1 = new Music(4,"tere bin","good song");
        musicRepository.save(u);
        musicRepository.save(u1);
        List<Music> list = (List<Music>) musicRepository.findAll();

        Assert.assertNotSame(list,music);
    }


    @Test
    public void testDeleteTrack()
    {    Music u=new Music(4,"just like animals","hit song");
        musicRepository.save(u);
        musicRepository.deleteById(u.getId());
        Assert.assertEquals(0, musicRepository.count());
    }

    @Test
    public void testDeleteTrackfailure()
    {    Music u=new Music(1,"you can count on me","awesome");
        musicRepository.save(u);
        musicRepository.deleteById(music.getId());
        Assert.assertNotEquals(1, musicRepository.count());
    }


   }


