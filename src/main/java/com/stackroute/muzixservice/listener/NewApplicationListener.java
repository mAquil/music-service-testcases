package com.stackroute.muzixservice.listener;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:config.properties")
public class NewApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    Music music=new Music();
    @Autowired
    MusicRepository musicRepository;
    @Autowired
    private Environment environment;
    @Override

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        music.setId(Integer.parseInt(environment.getProperty("music1.id")));
        music.setTrackName(environment.getProperty("music1.name"));
        music.setTrackComments(environment.getProperty("music1.comment"));
        musicRepository.save(music);

    }

}
