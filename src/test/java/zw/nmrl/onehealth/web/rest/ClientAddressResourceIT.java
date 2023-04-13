package zw.nmrl.onehealth.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.IntegrationTest;
import zw.nmrl.onehealth.domain.ClientAddress;
import zw.nmrl.onehealth.repository.ClientAddressRepository;

/**
 * Integration tests for the {@link ClientAddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientAddressResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientAddressRepository clientAddressRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientAddressMockMvc;

    private ClientAddress clientAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientAddress createEntity(EntityManager em) {
        ClientAddress clientAddress = new ClientAddress().name(DEFAULT_NAME).clientId(DEFAULT_CLIENT_ID);
        return clientAddress;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientAddress createUpdatedEntity(EntityManager em) {
        ClientAddress clientAddress = new ClientAddress().name(UPDATED_NAME).clientId(UPDATED_CLIENT_ID);
        return clientAddress;
    }

    @BeforeEach
    public void initTest() {
        clientAddress = createEntity(em);
    }

    @Test
    @Transactional
    void createClientAddress() throws Exception {
        int databaseSizeBeforeCreate = clientAddressRepository.findAll().size();
        // Create the ClientAddress
        restClientAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientAddress)))
            .andExpect(status().isCreated());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeCreate + 1);
        ClientAddress testClientAddress = clientAddressList.get(clientAddressList.size() - 1);
        assertThat(testClientAddress.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClientAddress.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    void createClientAddressWithExistingId() throws Exception {
        // Create the ClientAddress with an existing ID
        clientAddress.setId(1L);

        int databaseSizeBeforeCreate = clientAddressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientAddress)))
            .andExpect(status().isBadRequest());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientAddresses() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

        // Get all the clientAddressList
        restClientAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)));
    }

    @Test
    @Transactional
    void getClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

        // Get the clientAddress
        restClientAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, clientAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientAddress.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID));
    }

    @Test
    @Transactional
    void getNonExistingClientAddress() throws Exception {
        // Get the clientAddress
        restClientAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();

        // Update the clientAddress
        ClientAddress updatedClientAddress = clientAddressRepository.findById(clientAddress.getId()).get();
        // Disconnect from session so that the updates on updatedClientAddress are not directly saved in db
        em.detach(updatedClientAddress);
        updatedClientAddress.name(UPDATED_NAME).clientId(UPDATED_CLIENT_ID);

        restClientAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientAddress.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientAddress))
            )
            .andExpect(status().isOk());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
        ClientAddress testClientAddress = clientAddressList.get(clientAddressList.size() - 1);
        assertThat(testClientAddress.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClientAddress.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    void putNonExistingClientAddress() throws Exception {
        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();
        clientAddress.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientAddress.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientAddress() throws Exception {
        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();
        clientAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientAddress() throws Exception {
        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();
        clientAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientAddress)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientAddressWithPatch() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();

        // Update the clientAddress using partial update
        ClientAddress partialUpdatedClientAddress = new ClientAddress();
        partialUpdatedClientAddress.setId(clientAddress.getId());

        restClientAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientAddress))
            )
            .andExpect(status().isOk());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
        ClientAddress testClientAddress = clientAddressList.get(clientAddressList.size() - 1);
        assertThat(testClientAddress.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClientAddress.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    void fullUpdateClientAddressWithPatch() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();

        // Update the clientAddress using partial update
        ClientAddress partialUpdatedClientAddress = new ClientAddress();
        partialUpdatedClientAddress.setId(clientAddress.getId());

        partialUpdatedClientAddress.name(UPDATED_NAME).clientId(UPDATED_CLIENT_ID);

        restClientAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientAddress))
            )
            .andExpect(status().isOk());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
        ClientAddress testClientAddress = clientAddressList.get(clientAddressList.size() - 1);
        assertThat(testClientAddress.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClientAddress.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingClientAddress() throws Exception {
        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();
        clientAddress.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientAddress() throws Exception {
        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();
        clientAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientAddress() throws Exception {
        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();
        clientAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientAddressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientAddress))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

        int databaseSizeBeforeDelete = clientAddressRepository.findAll().size();

        // Delete the clientAddress
        restClientAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientAddress.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
