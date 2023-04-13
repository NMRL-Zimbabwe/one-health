package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.Organism;
import zw.nmrl.onehealth.repository.OrganismRepository;
import zw.nmrl.onehealth.service.OrganismService;

/**
 * Service Implementation for managing {@link Organism}.
 */
@Service
@Transactional
public class OrganismServiceImpl implements OrganismService {

    private final Logger log = LoggerFactory.getLogger(OrganismServiceImpl.class);

    private final OrganismRepository organismRepository;

    public OrganismServiceImpl(OrganismRepository organismRepository) {
        this.organismRepository = organismRepository;
    }

    @Override
    public Organism save(Organism organism) {
        log.debug("Request to save Organism : {}", organism);
        return organismRepository.save(organism);
    }

    @Override
    public Organism update(Organism organism) {
        log.debug("Request to update Organism : {}", organism);
        return organismRepository.save(organism);
    }

    @Override
    public Optional<Organism> partialUpdate(Organism organism) {
        log.debug("Request to partially update Organism : {}", organism);

        return organismRepository
            .findById(organism.getId())
            .map(existingOrganism -> {
                if (organism.getName() != null) {
                    existingOrganism.setName(organism.getName());
                }
                if (organism.getCode() != null) {
                    existingOrganism.setCode(organism.getCode());
                }

                return existingOrganism;
            })
            .map(organismRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Organism> findAll(Pageable pageable) {
        log.debug("Request to get all Organisms");
        return organismRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Organism> findOne(Long id) {
        log.debug("Request to get Organism : {}", id);
        return organismRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organism : {}", id);
        organismRepository.deleteById(id);
    }
}
