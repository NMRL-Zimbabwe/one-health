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
import zw.nmrl.onehealth.domain.AntibioticClass;
import zw.nmrl.onehealth.repository.AntibioticClassRepository;

/**
 * Integration tests for the {@link AntibioticClassResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AntibioticClassResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/antibiotic-classes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AntibioticClassRepository antibioticClassRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAntibioticClassMockMvc;

    private AntibioticClass antibioticClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AntibioticClass createEntity(EntityManager em) {
        AntibioticClass antibioticClass = new AntibioticClass().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return antibioticClass;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AntibioticClass createUpdatedEntity(EntityManager em) {
        AntibioticClass antibioticClass = new AntibioticClass().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return antibioticClass;
    }

    @BeforeEach
    public void initTest() {
        antibioticClass = createEntity(em);
    }

    @Test
    @Transactional
    void createAntibioticClass() throws Exception {
        int databaseSizeBeforeCreate = antibioticClassRepository.findAll().size();
        // Create the AntibioticClass
        restAntibioticClassMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibioticClass))
            )
            .andExpect(status().isCreated());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeCreate + 1);
        AntibioticClass testAntibioticClass = antibioticClassList.get(antibioticClassList.size() - 1);
        assertThat(testAntibioticClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAntibioticClass.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createAntibioticClassWithExistingId() throws Exception {
        // Create the AntibioticClass with an existing ID
        antibioticClass.setId(1L);

        int databaseSizeBeforeCreate = antibioticClassRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntibioticClassMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibioticClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAntibioticClasses() throws Exception {
        // Initialize the database
        antibioticClassRepository.saveAndFlush(antibioticClass);

        // Get all the antibioticClassList
        restAntibioticClassMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antibioticClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getAntibioticClass() throws Exception {
        // Initialize the database
        antibioticClassRepository.saveAndFlush(antibioticClass);

        // Get the antibioticClass
        restAntibioticClassMockMvc
            .perform(get(ENTITY_API_URL_ID, antibioticClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(antibioticClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingAntibioticClass() throws Exception {
        // Get the antibioticClass
        restAntibioticClassMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAntibioticClass() throws Exception {
        // Initialize the database
        antibioticClassRepository.saveAndFlush(antibioticClass);

        int databaseSizeBeforeUpdate = antibioticClassRepository.findAll().size();

        // Update the antibioticClass
        AntibioticClass updatedAntibioticClass = antibioticClassRepository.findById(antibioticClass.getId()).get();
        // Disconnect from session so that the updates on updatedAntibioticClass are not directly saved in db
        em.detach(updatedAntibioticClass);
        updatedAntibioticClass.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restAntibioticClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAntibioticClass.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAntibioticClass))
            )
            .andExpect(status().isOk());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeUpdate);
        AntibioticClass testAntibioticClass = antibioticClassList.get(antibioticClassList.size() - 1);
        assertThat(testAntibioticClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAntibioticClass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingAntibioticClass() throws Exception {
        int databaseSizeBeforeUpdate = antibioticClassRepository.findAll().size();
        antibioticClass.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntibioticClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, antibioticClass.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(antibioticClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAntibioticClass() throws Exception {
        int databaseSizeBeforeUpdate = antibioticClassRepository.findAll().size();
        antibioticClass.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(antibioticClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAntibioticClass() throws Exception {
        int databaseSizeBeforeUpdate = antibioticClassRepository.findAll().size();
        antibioticClass.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticClassMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibioticClass))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAntibioticClassWithPatch() throws Exception {
        // Initialize the database
        antibioticClassRepository.saveAndFlush(antibioticClass);

        int databaseSizeBeforeUpdate = antibioticClassRepository.findAll().size();

        // Update the antibioticClass using partial update
        AntibioticClass partialUpdatedAntibioticClass = new AntibioticClass();
        partialUpdatedAntibioticClass.setId(antibioticClass.getId());

        partialUpdatedAntibioticClass.description(UPDATED_DESCRIPTION);

        restAntibioticClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAntibioticClass.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAntibioticClass))
            )
            .andExpect(status().isOk());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeUpdate);
        AntibioticClass testAntibioticClass = antibioticClassList.get(antibioticClassList.size() - 1);
        assertThat(testAntibioticClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAntibioticClass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateAntibioticClassWithPatch() throws Exception {
        // Initialize the database
        antibioticClassRepository.saveAndFlush(antibioticClass);

        int databaseSizeBeforeUpdate = antibioticClassRepository.findAll().size();

        // Update the antibioticClass using partial update
        AntibioticClass partialUpdatedAntibioticClass = new AntibioticClass();
        partialUpdatedAntibioticClass.setId(antibioticClass.getId());

        partialUpdatedAntibioticClass.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restAntibioticClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAntibioticClass.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAntibioticClass))
            )
            .andExpect(status().isOk());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeUpdate);
        AntibioticClass testAntibioticClass = antibioticClassList.get(antibioticClassList.size() - 1);
        assertThat(testAntibioticClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAntibioticClass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingAntibioticClass() throws Exception {
        int databaseSizeBeforeUpdate = antibioticClassRepository.findAll().size();
        antibioticClass.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntibioticClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, antibioticClass.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(antibioticClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAntibioticClass() throws Exception {
        int databaseSizeBeforeUpdate = antibioticClassRepository.findAll().size();
        antibioticClass.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(antibioticClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAntibioticClass() throws Exception {
        int databaseSizeBeforeUpdate = antibioticClassRepository.findAll().size();
        antibioticClass.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticClassMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(antibioticClass))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AntibioticClass in the database
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAntibioticClass() throws Exception {
        // Initialize the database
        antibioticClassRepository.saveAndFlush(antibioticClass);

        int databaseSizeBeforeDelete = antibioticClassRepository.findAll().size();

        // Delete the antibioticClass
        restAntibioticClassMockMvc
            .perform(delete(ENTITY_API_URL_ID, antibioticClass.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AntibioticClass> antibioticClassList = antibioticClassRepository.findAll();
        assertThat(antibioticClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
