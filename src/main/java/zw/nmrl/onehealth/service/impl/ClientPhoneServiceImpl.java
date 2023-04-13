package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.ClientPhone;
import zw.nmrl.onehealth.repository.ClientPhoneRepository;
import zw.nmrl.onehealth.service.ClientPhoneService;

/**
 * Service Implementation for managing {@link ClientPhone}.
 */
@Service
@Transactional
public class ClientPhoneServiceImpl implements ClientPhoneService {

    private final Logger log = LoggerFactory.getLogger(ClientPhoneServiceImpl.class);

    private final ClientPhoneRepository clientPhoneRepository;

    public ClientPhoneServiceImpl(ClientPhoneRepository clientPhoneRepository) {
        this.clientPhoneRepository = clientPhoneRepository;
    }

    @Override
    public ClientPhone save(ClientPhone clientPhone) {
        log.debug("Request to save ClientPhone : {}", clientPhone);
        return clientPhoneRepository.save(clientPhone);
    }

    @Override
    public ClientPhone update(ClientPhone clientPhone) {
        log.debug("Request to update ClientPhone : {}", clientPhone);
        return clientPhoneRepository.save(clientPhone);
    }

    @Override
    public Optional<ClientPhone> partialUpdate(ClientPhone clientPhone) {
        log.debug("Request to partially update ClientPhone : {}", clientPhone);

        return clientPhoneRepository
            .findById(clientPhone.getId())
            .map(existingClientPhone -> {
                if (clientPhone.getNumber() != null) {
                    existingClientPhone.setNumber(clientPhone.getNumber());
                }
                if (clientPhone.getClientId() != null) {
                    existingClientPhone.setClientId(clientPhone.getClientId());
                }

                return existingClientPhone;
            })
            .map(clientPhoneRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientPhone> findAll(Pageable pageable) {
        log.debug("Request to get all ClientPhones");
        return clientPhoneRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientPhone> findOne(Long id) {
        log.debug("Request to get ClientPhone : {}", id);
        return clientPhoneRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientPhone : {}", id);
        clientPhoneRepository.deleteById(id);
    }
}
