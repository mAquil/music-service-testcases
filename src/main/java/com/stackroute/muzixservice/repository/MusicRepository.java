package com.stackroute.muzixservice.repository;

import com.stackroute.muzixservice.domain.Music;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends CrudRepository<Music,Integer> {
    @Query(value = "select * from track where trackName = ?", nativeQuery = true)
    public Music getTrackByName(String trackName);

}
