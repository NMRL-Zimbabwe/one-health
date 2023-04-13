package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.ClientAddress;
import zw.nmrl.onehealth.repository.ClientAddressRepository;
import zw.nmrl.onehealth.service.ClientAddressService;

/**
 * Service Implementation for managing {@link ClientAddress}.
 */
@Service
@Transactional
public class ClientAddressServiceImpl implements ClientAddressService {

    private final Logger log = LoggerFactory.getLogger(ClientAddressServiceImpl.class);

    private final ClientAddressRepository clientAddressRepository;

    public ClientAddressServiceImpl(ClientAddressRepository clientAddressRepository) {
        this.clientAddressRepository = clientAddressRepository;
    }

    @Override
    public ClientAddress save(ClientAddress clientAddress) {
        log.debug("Request to save ClientAddress : {}", clientAddress);
        return clientAddressRepository.save(clientAddress);
    }

    @Override
    public ClientAddress update(ClientAddress clientAddress) {
        log.debug("Request to update ClientAddress : {}", clientAddress);
        return clientAddressRepository.save(clientAddress);
    }

    @Override
    public Optional<ClientAddress> partialUpdate(ClientAddress clientAddress) {
        log.debug("Request to partially update ClientAddress : {}", clientAddress);

        return clientAddressRepository
            .findById(clientAddress.getId())
            .map(existingClientAddress -> {
                if (clientAddress.getName() != null) {
                    existingClientAddress.setName(clientAddress.getName());
                }
                if (clientAddress.getClientId() != null) {
                    existingClientAddress.setClientId(clientAddress.getClientId());
                }

                return existingClientAddress;
            })
            .map(clientAddressRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientAddress> findAll(Pageable pageable) {
        log.debug("Request to get all ClientAddresses");
        return clientAddressRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientAddress> findOne(Long id) {
        log.debug("Request to get ClientAddress : {}", id);
        return clientAddressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientAddress : {}", id);
        clientAddressRepository.deleteById(id);
    }
}
