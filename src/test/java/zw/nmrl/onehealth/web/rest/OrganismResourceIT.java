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
import zw.nmrl.onehealth.domain.Organism;
import zw.nmrl.onehealth.repository.OrganismRepository;

/**
 * Integration tests for the {@link OrganismResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganismResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organisms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganismRepository organismRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganismMockMvc;

    private Organism organism;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organism createEntity(EntityManager em) {
        Organism organism = new Organism().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return organism;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organism createUpdatedEntity(EntityManager em) {
        Organism organism = new Organism().name(UPDATED_NAME).code(UPDATED_CODE);
        return organism;
    }

    @BeforeEach
    public void initTest() {
        organism = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganism() throws Exception {
        int databaseSizeBeforeCreate = organismRepository.findAll().size();
        // Create the Organism
        restOrganismMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organism)))
            .andExpect(status().isCreated());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeCreate + 1);
        Organism testOrganism = organismList.get(organismList.size() - 1);
        assertThat(testOrganism.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganism.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createOrganismWithExistingId() throws Exception {
        // Create the Organism with an existing ID
        organism.setId(1L);

        int databaseSizeBeforeCreate = organismRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganismMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organism)))
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organismRepository.findAll().size();
        // set the field null
        organism.setName(null);

        // Create the Organism, which fails.

        restOrganismMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organism)))
            .andExpect(status().isBadRequest());

        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganisms() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList
        restOrganismMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organism.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getOrganism() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get the organism
        restOrganismMockMvc
            .perform(get(ENTITY_API_URL_ID, organism.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organism.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingOrganism() throws Exception {
        // Get the organism
        restOrganismMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganism() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        int databaseSizeBeforeUpdate = organismRepository.findAll().size();

        // Update the organism
        Organism updatedOrganism = organismRepository.findById(organism.getId()).get();
        // Disconnect from session so that the updates on updatedOrganism are not directly saved in db
        em.detach(updatedOrganism);
        updatedOrganism.name(UPDATED_NAME).code(UPDATED_CODE);

        restOrganismMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrganism.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrganism))
            )
            .andExpect(status().isOk());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
        Organism testOrganism = organismList.get(organismList.size() - 1);
        assertThat(testOrganism.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganism.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organism.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organism))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organism))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organism)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganismWithPatch() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        int databaseSizeBeforeUpdate = organismRepository.findAll().size();

        // Update the organism using partial update
        Organism partialUpdatedOrganism = new Organism();
        partialUpdatedOrganism.setId(organism.getId());

        partialUpdatedOrganism.code(UPDATED_CODE);

        restOrganismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganism.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganism))
            )
            .andExpect(status().isOk());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
        Organism testOrganism = organismList.get(organismList.size() - 1);
        assertThat(testOrganism.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganism.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateOrganismWithPatch() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        int databaseSizeBeforeUpdate = organismRepository.findAll().size();

        // Update the organism using partial update
        Organism partialUpdatedOrganism = new Organism();
        partialUpdatedOrganism.setId(organism.getId());

        partialUpdatedOrganism.name(UPDATED_NAME).code(UPDATED_CODE);

        restOrganismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganism.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganism))
            )
            .andExpect(status().isOk());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
        Organism testOrganism = organismList.get(organismList.size() - 1);
        assertThat(testOrganism.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganism.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organism.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organism))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organism))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(organism)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganism() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        int databaseSizeBeforeDelete = organismRepository.findAll().size();

        // Delete the organism
        restOrganismMockMvc
            .perform(delete(ENTITY_API_URL_ID, organism.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
