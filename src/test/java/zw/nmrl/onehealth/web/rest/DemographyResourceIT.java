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
import zw.nmrl.onehealth.domain.Demography;
import zw.nmrl.onehealth.repository.DemographyRepository;

/**
 * Integration tests for the {@link DemographyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemographyResourceIT {

    private static final String DEFAULT_RECORD_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECORD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEMOGRAPHIC_CODING_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEMOGRAPHIC_CODING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/demographies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemographyRepository demographyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemographyMockMvc;

    private Demography demography;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demography createEntity(EntityManager em) {
        Demography demography = new Demography()
            .recordId(DEFAULT_RECORD_ID)
            .demographicCodingId(DEFAULT_DEMOGRAPHIC_CODING_ID)
            .value(DEFAULT_VALUE);
        return demography;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demography createUpdatedEntity(EntityManager em) {
        Demography demography = new Demography()
            .recordId(UPDATED_RECORD_ID)
            .demographicCodingId(UPDATED_DEMOGRAPHIC_CODING_ID)
            .value(UPDATED_VALUE);
        return demography;
    }

    @BeforeEach
    public void initTest() {
        demography = createEntity(em);
    }

    @Test
    @Transactional
    void createDemography() throws Exception {
        int databaseSizeBeforeCreate = demographyRepository.findAll().size();
        // Create the Demography
        restDemographyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demography)))
            .andExpect(status().isCreated());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeCreate + 1);
        Demography testDemography = demographyList.get(demographyList.size() - 1);
        assertThat(testDemography.getRecordId()).isEqualTo(DEFAULT_RECORD_ID);
        assertThat(testDemography.getDemographicCodingId()).isEqualTo(DEFAULT_DEMOGRAPHIC_CODING_ID);
        assertThat(testDemography.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createDemographyWithExistingId() throws Exception {
        // Create the Demography with an existing ID
        demography.setId(1L);

        int databaseSizeBeforeCreate = demographyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemographyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demography)))
            .andExpect(status().isBadRequest());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = demographyRepository.findAll().size();
        // set the field null
        demography.setRecordId(null);

        // Create the Demography, which fails.

        restDemographyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demography)))
            .andExpect(status().isBadRequest());

        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDemographicCodingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = demographyRepository.findAll().size();
        // set the field null
        demography.setDemographicCodingId(null);

        // Create the Demography, which fails.

        restDemographyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demography)))
            .andExpect(status().isBadRequest());

        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = demographyRepository.findAll().size();
        // set the field null
        demography.setValue(null);

        // Create the Demography, which fails.

        restDemographyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demography)))
            .andExpect(status().isBadRequest());

        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemographies() throws Exception {
        // Initialize the database
        demographyRepository.saveAndFlush(demography);

        // Get all the demographyList
        restDemographyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demography.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordId").value(hasItem(DEFAULT_RECORD_ID)))
            .andExpect(jsonPath("$.[*].demographicCodingId").value(hasItem(DEFAULT_DEMOGRAPHIC_CODING_ID)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getDemography() throws Exception {
        // Initialize the database
        demographyRepository.saveAndFlush(demography);

        // Get the demography
        restDemographyMockMvc
            .perform(get(ENTITY_API_URL_ID, demography.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demography.getId().intValue()))
            .andExpect(jsonPath("$.recordId").value(DEFAULT_RECORD_ID))
            .andExpect(jsonPath("$.demographicCodingId").value(DEFAULT_DEMOGRAPHIC_CODING_ID))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingDemography() throws Exception {
        // Get the demography
        restDemographyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemography() throws Exception {
        // Initialize the database
        demographyRepository.saveAndFlush(demography);

        int databaseSizeBeforeUpdate = demographyRepository.findAll().size();

        // Update the demography
        Demography updatedDemography = demographyRepository.findById(demography.getId()).get();
        // Disconnect from session so that the updates on updatedDemography are not directly saved in db
        em.detach(updatedDemography);
        updatedDemography.recordId(UPDATED_RECORD_ID).demographicCodingId(UPDATED_DEMOGRAPHIC_CODING_ID).value(UPDATED_VALUE);

        restDemographyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemography.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemography))
            )
            .andExpect(status().isOk());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeUpdate);
        Demography testDemography = demographyList.get(demographyList.size() - 1);
        assertThat(testDemography.getRecordId()).isEqualTo(UPDATED_RECORD_ID);
        assertThat(testDemography.getDemographicCodingId()).isEqualTo(UPDATED_DEMOGRAPHIC_CODING_ID);
        assertThat(testDemography.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingDemography() throws Exception {
        int databaseSizeBeforeUpdate = demographyRepository.findAll().size();
        demography.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemographyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demography.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demography))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemography() throws Exception {
        int databaseSizeBeforeUpdate = demographyRepository.findAll().size();
        demography.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemographyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demography))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemography() throws Exception {
        int databaseSizeBeforeUpdate = demographyRepository.findAll().size();
        demography.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemographyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demography)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemographyWithPatch() throws Exception {
        // Initialize the database
        demographyRepository.saveAndFlush(demography);

        int databaseSizeBeforeUpdate = demographyRepository.findAll().size();

        // Update the demography using partial update
        Demography partialUpdatedDemography = new Demography();
        partialUpdatedDemography.setId(demography.getId());

        partialUpdatedDemography.recordId(UPDATED_RECORD_ID).value(UPDATED_VALUE);

        restDemographyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemography.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemography))
            )
            .andExpect(status().isOk());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeUpdate);
        Demography testDemography = demographyList.get(demographyList.size() - 1);
        assertThat(testDemography.getRecordId()).isEqualTo(UPDATED_RECORD_ID);
        assertThat(testDemography.getDemographicCodingId()).isEqualTo(DEFAULT_DEMOGRAPHIC_CODING_ID);
        assertThat(testDemography.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateDemographyWithPatch() throws Exception {
        // Initialize the database
        demographyRepository.saveAndFlush(demography);

        int databaseSizeBeforeUpdate = demographyRepository.findAll().size();

        // Update the demography using partial update
        Demography partialUpdatedDemography = new Demography();
        partialUpdatedDemography.setId(demography.getId());

        partialUpdatedDemography.recordId(UPDATED_RECORD_ID).demographicCodingId(UPDATED_DEMOGRAPHIC_CODING_ID).value(UPDATED_VALUE);

        restDemographyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemography.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemography))
            )
            .andExpect(status().isOk());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeUpdate);
        Demography testDemography = demographyList.get(demographyList.size() - 1);
        assertThat(testDemography.getRecordId()).isEqualTo(UPDATED_RECORD_ID);
        assertThat(testDemography.getDemographicCodingId()).isEqualTo(UPDATED_DEMOGRAPHIC_CODING_ID);
        assertThat(testDemography.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingDemography() throws Exception {
        int databaseSizeBeforeUpdate = demographyRepository.findAll().size();
        demography.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemographyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demography.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demography))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemography() throws Exception {
        int databaseSizeBeforeUpdate = demographyRepository.findAll().size();
        demography.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemographyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demography))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemography() throws Exception {
        int databaseSizeBeforeUpdate = demographyRepository.findAll().size();
        demography.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemographyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(demography))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demography in the database
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemography() throws Exception {
        // Initialize the database
        demographyRepository.saveAndFlush(demography);

        int databaseSizeBeforeDelete = demographyRepository.findAll().size();

        // Delete the demography
        restDemographyMockMvc
            .perform(delete(ENTITY_API_URL_ID, demography.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Demography> demographyList = demographyRepository.findAll();
        assertThat(demographyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
