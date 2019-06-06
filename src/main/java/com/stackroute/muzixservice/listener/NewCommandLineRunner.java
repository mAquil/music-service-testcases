package com.stackroute.muzixservice.listener;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NewCommandLineRunner implements CommandLineRunner {
    @Value("${music1.id}")
    private String id;
    @Value("${music1.name}")
    private String name;
    @Value("${music1.comment}")
    private String comment;

    Music music=new Music();
    @Autowired
    MusicRepository musicRepository;
    @Override
    public void run(String... args) throws Exception {
        music.setId(Integer.parseInt(id));
        music.setTrackName(name);
        music.setTrackComments(comment);
        musicRepository.save(music);
    }
}