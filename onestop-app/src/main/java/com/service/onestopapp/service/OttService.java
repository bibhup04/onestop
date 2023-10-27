package com.service.onestopapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopapp.entity.Ott;
import com.service.onestopapp.repository.OttRepository;

@Service
public class OttService {

    private final OttRepository ottRepository;

    @Autowired
    public OttService(OttRepository ottRepository) {
        this.ottRepository = ottRepository;
    }

    
    /** 
     * @return List<Ott>
     */
    public List<Ott> getAllOtts() {
        return ottRepository.findAll();
    }

    public Optional<Ott> getOttById(Long id) {
        return ottRepository.findById(id);
    }

    public Ott saveOtt(Ott ott) {
        return ottRepository.save(ott);
    }

    public void deleteOttById(Long id) {
        ottRepository.deleteById(id);
    }
}
