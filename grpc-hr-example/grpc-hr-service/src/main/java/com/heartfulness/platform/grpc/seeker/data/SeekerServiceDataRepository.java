
package com.heartfulness.platform.grpc.seeker.data;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartfulness.platform.grpc.seeker.service.SeekerFilter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SeekerServiceDataRepository {
    Map<Long, SeekerEntity> seekersMap;

    public SeekerServiceDataRepository() {
        seekersMap = readSeekerDataFromFileStore();
    }

    public SeekerEntity createSeeker(SeekerEntity department) {
        seekersMap.put(department.getId(), department);
        return department;
    }


    public SeekerEntity updateSeeker(SeekerEntity department) {
        seekersMap.put(department.getId(), department);
        return department;
    }

    public List<SeekerEntity> findAllSeekers(){
        List<SeekerEntity> seekerEntityList = seekersMap.values().stream().collect(Collectors.toList());
        return seekerEntityList;

    }
    public List<SeekerEntity> findSeekersByFilter(SeekerFilter seekerFilter) {
        List<SeekerEntity> filteredList = seekersMap.values().stream()
                .filter(Objects::nonNull)
                .filter(x -> ((seekerFilter.getId() == 0 || x.getId().equals(seekerFilter.getId()))
                        && (seekerFilter.getLocation().isEmpty()  || x.getLocation().equals(seekerFilter.getLocation()))
                        && (seekerFilter.getSeekerName().isEmpty() || x.getSeekerName().equals(seekerFilter.getSeekerName())))
                )
                .collect(Collectors.toList());
        return filteredList;
    }


    public void deleteSeeker(long seekerId) {
        seekersMap.remove(seekerId);
    }

    public SeekerEntity findSeekerById(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Seeker Id cannot be negative!");
        }
        return seekersMap.get(id);
    }

    private Map<Long, SeekerEntity> readSeekerDataFromFileStore() {
        List<SeekerEntity> seekersList = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream jsonInput = SeekerServiceDataRepository.class.getResourceAsStream("/hr-data.json");
            seekersList = mapper.readValue(jsonInput,
                    new TypeReference<List<SeekerEntity>>() {
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            seekersList = new ArrayList<SeekerEntity>();
        }
        Map<Long, SeekerEntity> seekersMap = seekersList.stream().
                collect(Collectors.toMap(SeekerEntity::getId, seeker -> seeker));
        return seekersMap;
    }
}
