package com.example.gadgetsore.service;

import com.example.gadgetsore.entity.Smartphone;
import com.example.gadgetsore.repository.SmartphoneRepository;
import com.example.gadgetsore.specifications.SmartphoneSpecifications;
import com.example.gadgetsore.util.SmartphoneFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SmartphoneService {
    public final SmartphoneRepository smartphoneRepository;

    public SmartphoneService(SmartphoneRepository smartphoneRepository) {
        this.smartphoneRepository = smartphoneRepository;
    }
    public Collection<Smartphone> getMatchingSmartphone(Optional<SmartphoneFilter> smartphoneFilter) {
        if(smartphoneFilter.isEmpty()){
            /*Specification<Smartphone> spec = SmartphoneSpecifications
                    .hasBrand(
                            smartphoneFilter.getListBrand(),
                            smartphoneFilter.getListMemory());
            return smartphoneRepository.findAll(spec);*/
            return smartphoneRepository.findAll();

        }
        else{

            Specification<Smartphone> spec = SmartphoneSpecifications
                    .hasBrand(
                            smartphoneFilter.get().getListBrand(),
                            smartphoneFilter.get().getListModel(),
                            smartphoneFilter.get().getListMemory(),
                            smartphoneFilter.get().getListStorage(),
                            smartphoneFilter.get().getListColor()
                    );
            return smartphoneRepository.findAll(spec);
            //return smartphoneRepository.findAll();
        }
    }


}
