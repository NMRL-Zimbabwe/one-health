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
import zw.nmrl.onehealth.domain.Orgnanism;
import zw.nmrl.onehealth.repository.OrgnanismRepository;

/**
 * Integration tests for the {@link OrgnanismResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrgnanismResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/orgnanisms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrgnanismRepository orgnanismRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrgnanismMockMvc;

    private Orgnanism orgnanism;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orgnanism createEntity(EntityManager em) {
        Orgnanism orgnanism = new Orgnanism().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return orgnanism;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orgnanism createUpdatedEntity(EntityManager em) {
        Orgnanism orgnanism = new Orgnanism().name(UPDATED_NAME).code(UPDATED_CODE);
        return orgnanism;
    }

    @BeforeEach
    public void initTest() {
        orgnanism = createEntity(em);
    }

    @Test
    @Transactional
    void createOrgnanism() throws Exception {
        int databaseSizeBeforeCreate = orgnanismRepository.findAll().size();
        // Create the Orgnanism
        restOrgnanismMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgnanism)))
            .andExpect(status().isCreated());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeCreate + 1);
        Orgnanism testOrgnanism = orgnanismList.get(orgnanismList.size() - 1);
        assertThat(testOrgnanism.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrgnanism.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createOrgnanismWithExistingId() throws Exception {
        // Create the Orgnanism with an existing ID
        orgnanism.setId(1L);

        int databaseSizeBeforeCreate = orgnanismRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgnanismMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgnanism)))
            .andExpect(status().isBadRequest());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = orgnanismRepository.findAll().size();
        // set the field null
        orgnanism.setName(null);

        // Create the Orgnanism, which fails.

        restOrgnanismMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgnanism)))
            .andExpect(status().isBadRequest());

        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrgnanisms() throws Exception {
        // Initialize the database
        orgnanismRepository.saveAndFlush(orgnanism);

        // Get all the orgnanismList
        restOrgnanismMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgnanism.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getOrgnanism() throws Exception {
        // Initialize the database
        orgnanismRepository.saveAndFlush(orgnanism);

        // Get the orgnanism
        restOrgnanismMockMvc
            .perform(get(ENTITY_API_URL_ID, orgnanism.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orgnanism.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingOrgnanism() throws Exception {
        // Get the orgnanism
        restOrgnanismMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrgnanism() throws Exception {
        // Initialize the database
        orgnanismRepository.saveAndFlush(orgnanism);

        int databaseSizeBeforeUpdate = orgnanismRepository.findAll().size();

        // Update the orgnanism
        Orgnanism updatedOrgnanism = orgnanismRepository.findById(orgnanism.getId()).get();
        // Disconnect from session so that the updates on updatedOrgnanism are not directly saved in db
        em.detach(updatedOrgnanism);
        updatedOrgnanism.name(UPDATED_NAME).code(UPDATED_CODE);

        restOrgnanismMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrgnanism.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrgnanism))
            )
            .andExpect(status().isOk());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeUpdate);
        Orgnanism testOrgnanism = orgnanismList.get(orgnanismList.size() - 1);
        assertThat(testOrgnanism.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrgnanism.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingOrgnanism() throws Exception {
        int databaseSizeBeforeUpdate = orgnanismRepository.findAll().size();
        orgnanism.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgnanismMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgnanism.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgnanism))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrgnanism() throws Exception {
        int databaseSizeBeforeUpdate = orgnanismRepository.findAll().size();
        orgnanism.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgnanismMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgnanism))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrgnanism() throws Exception {
        int databaseSizeBeforeUpdate = orgnanismRepository.findAll().size();
        orgnanism.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgnanismMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgnanism)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrgnanismWithPatch() throws Exception {
        // Initialize the database
        orgnanismRepository.saveAndFlush(orgnanism);

        int databaseSizeBeforeUpdate = orgnanismRepository.findAll().size();

        // Update the orgnanism using partial update
        Orgnanism partialUpdatedOrgnanism = new Orgnanism();
        partialUpdatedOrgnanism.setId(orgnanism.getId());

        partialUpdatedOrgnanism.code(UPDATED_CODE);

        restOrgnanismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgnanism.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgnanism))
            )
            .andExpect(status().isOk());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeUpdate);
        Orgnanism testOrgnanism = orgnanismList.get(orgnanismList.size() - 1);
        assertThat(testOrgnanism.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrgnanism.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateOrgnanismWithPatch() throws Exception {
        // Initialize the database
        orgnanismRepository.saveAndFlush(orgnanism);

        int databaseSizeBeforeUpdate = orgnanismRepository.findAll().size();

        // Update the orgnanism using partial update
        Orgnanism partialUpdatedOrgnanism = new Orgnanism();
        partialUpdatedOrgnanism.setId(orgnanism.getId());

        partialUpdatedOrgnanism.name(UPDATED_NAME).code(UPDATED_CODE);

        restOrgnanismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgnanism.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgnanism))
            )
            .andExpect(status().isOk());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeUpdate);
        Orgnanism testOrgnanism = orgnanismList.get(orgnanismList.size() - 1);
        assertThat(testOrgnanism.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrgnanism.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingOrgnanism() throws Exception {
        int databaseSizeBeforeUpdate = orgnanismRepository.findAll().size();
        orgnanism.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgnanismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orgnanism.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgnanism))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrgnanism() throws Exception {
        int databaseSizeBeforeUpdate = orgnanismRepository.findAll().size();
        orgnanism.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgnanismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgnanism))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrgnanism() throws Exception {
        int databaseSizeBeforeUpdate = orgnanismRepository.findAll().size();
        orgnanism.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgnanismMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orgnanism))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orgnanism in the database
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrgnanism() throws Exception {
        // Initialize the database
        orgnanismRepository.saveAndFlush(orgnanism);

        int databaseSizeBeforeDelete = orgnanismRepository.findAll().size();

        // Delete the orgnanism
        restOrgnanismMockMvc
            .perform(delete(ENTITY_API_URL_ID, orgnanism.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Orgnanism> orgnanismList = orgnanismRepository.findAll();
        assertThat(orgnanismList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
