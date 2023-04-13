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
import zw.nmrl.onehealth.domain.ClientPhone;
import zw.nmrl.onehealth.repository.ClientPhoneRepository;

/**
 * Integration tests for the {@link ClientPhoneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientPhoneResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-phones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientPhoneRepository clientPhoneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientPhoneMockMvc;

    private ClientPhone clientPhone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientPhone createEntity(EntityManager em) {
        ClientPhone clientPhone = new ClientPhone().number(DEFAULT_NUMBER).clientId(DEFAULT_CLIENT_ID);
        return clientPhone;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientPhone createUpdatedEntity(EntityManager em) {
        ClientPhone clientPhone = new ClientPhone().number(UPDATED_NUMBER).clientId(UPDATED_CLIENT_ID);
        return clientPhone;
    }

    @BeforeEach
    public void initTest() {
        clientPhone = createEntity(em);
    }

    @Test
    @Transactional
    void createClientPhone() throws Exception {
        int databaseSizeBeforeCreate = clientPhoneRepository.findAll().size();
        // Create the ClientPhone
        restClientPhoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientPhone)))
            .andExpect(status().isCreated());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        ClientPhone testClientPhone = clientPhoneList.get(clientPhoneList.size() - 1);
        assertThat(testClientPhone.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testClientPhone.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    void createClientPhoneWithExistingId() throws Exception {
        // Create the ClientPhone with an existing ID
        clientPhone.setId(1L);

        int databaseSizeBeforeCreate = clientPhoneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientPhoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientPhone)))
            .andExpect(status().isBadRequest());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientPhones() throws Exception {
        // Initialize the database
        clientPhoneRepository.saveAndFlush(clientPhone);

        // Get all the clientPhoneList
        restClientPhoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)));
    }

    @Test
    @Transactional
    void getClientPhone() throws Exception {
        // Initialize the database
        clientPhoneRepository.saveAndFlush(clientPhone);

        // Get the clientPhone
        restClientPhoneMockMvc
            .perform(get(ENTITY_API_URL_ID, clientPhone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientPhone.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID));
    }

    @Test
    @Transactional
    void getNonExistingClientPhone() throws Exception {
        // Get the clientPhone
        restClientPhoneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientPhone() throws Exception {
        // Initialize the database
        clientPhoneRepository.saveAndFlush(clientPhone);

        int databaseSizeBeforeUpdate = clientPhoneRepository.findAll().size();

        // Update the clientPhone
        ClientPhone updatedClientPhone = clientPhoneRepository.findById(clientPhone.getId()).get();
        // Disconnect from session so that the updates on updatedClientPhone are not directly saved in db
        em.detach(updatedClientPhone);
        updatedClientPhone.number(UPDATED_NUMBER).clientId(UPDATED_CLIENT_ID);

        restClientPhoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientPhone.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientPhone))
            )
            .andExpect(status().isOk());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeUpdate);
        ClientPhone testClientPhone = clientPhoneList.get(clientPhoneList.size() - 1);
        assertThat(testClientPhone.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testClientPhone.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    void putNonExistingClientPhone() throws Exception {
        int databaseSizeBeforeUpdate = clientPhoneRepository.findAll().size();
        clientPhone.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientPhoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientPhone.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientPhone))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientPhone() throws Exception {
        int databaseSizeBeforeUpdate = clientPhoneRepository.findAll().size();
        clientPhone.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientPhoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientPhone))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientPhone() throws Exception {
        int databaseSizeBeforeUpdate = clientPhoneRepository.findAll().size();
        clientPhone.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientPhoneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientPhone)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientPhoneWithPatch() throws Exception {
        // Initialize the database
        clientPhoneRepository.saveAndFlush(clientPhone);

        int databaseSizeBeforeUpdate = clientPhoneRepository.findAll().size();

        // Update the clientPhone using partial update
        ClientPhone partialUpdatedClientPhone = new ClientPhone();
        partialUpdatedClientPhone.setId(clientPhone.getId());

        restClientPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientPhone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientPhone))
            )
            .andExpect(status().isOk());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeUpdate);
        ClientPhone testClientPhone = clientPhoneList.get(clientPhoneList.size() - 1);
        assertThat(testClientPhone.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testClientPhone.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    void fullUpdateClientPhoneWithPatch() throws Exception {
        // Initialize the database
        clientPhoneRepository.saveAndFlush(clientPhone);

        int databaseSizeBeforeUpdate = clientPhoneRepository.findAll().size();

        // Update the clientPhone using partial update
        ClientPhone partialUpdatedClientPhone = new ClientPhone();
        partialUpdatedClientPhone.setId(clientPhone.getId());

        partialUpdatedClientPhone.number(UPDATED_NUMBER).clientId(UPDATED_CLIENT_ID);

        restClientPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientPhone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientPhone))
            )
            .andExpect(status().isOk());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeUpdate);
        ClientPhone testClientPhone = clientPhoneList.get(clientPhoneList.size() - 1);
        assertThat(testClientPhone.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testClientPhone.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingClientPhone() throws Exception {
        int databaseSizeBeforeUpdate = clientPhoneRepository.findAll().size();
        clientPhone.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientPhone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientPhone))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientPhone() throws Exception {
        int databaseSizeBeforeUpdate = clientPhoneRepository.findAll().size();
        clientPhone.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientPhone))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientPhone() throws Exception {
        int databaseSizeBeforeUpdate = clientPhoneRepository.findAll().size();
        clientPhone.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientPhone))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientPhone in the database
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientPhone() throws Exception {
        // Initialize the database
        clientPhoneRepository.saveAndFlush(clientPhone);

        int databaseSizeBeforeDelete = clientPhoneRepository.findAll().size();

        // Delete the clientPhone
        restClientPhoneMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientPhone.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientPhone> clientPhoneList = clientPhoneRepository.findAll();
        assertThat(clientPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
