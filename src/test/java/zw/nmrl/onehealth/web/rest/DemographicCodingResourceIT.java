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
import org.springframework.util.Base64Utils;
import zw.nmrl.onehealth.IntegrationTest;
import zw.nmrl.onehealth.domain.DemographicCoding;
import zw.nmrl.onehealth.repository.DemographicCodingRepository;

/**
 * Integration tests for the {@link DemographicCodingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemographicCodingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/demographic-codings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemographicCodingRepository demographicCodingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemographicCodingMockMvc;

    private DemographicCoding demographicCoding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemographicCoding createEntity(EntityManager em) {
        DemographicCoding demographicCoding = new DemographicCoding()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return demographicCoding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemographicCoding createUpdatedEntity(EntityManager em) {
        DemographicCoding demographicCoding = new DemographicCoding()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return demographicCoding;
    }

    @BeforeEach
    public void initTest() {
        demographicCoding = createEntity(em);
    }

    @Test
    @Transactional
    void createDemographicCoding() throws Exception {
        int databaseSizeBeforeCreate = demographicCodingRepository.findAll().size();
        // Create the DemographicCoding
        restDemographicCodingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isCreated());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeCreate + 1);
        DemographicCoding testDemographicCoding = demographicCodingList.get(demographicCodingList.size() - 1);
        assertThat(testDemographicCoding.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDemographicCoding.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDemographicCoding.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createDemographicCodingWithExistingId() throws Exception {
        // Create the DemographicCoding with an existing ID
        demographicCoding.setId(1L);

        int databaseSizeBeforeCreate = demographicCodingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemographicCodingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = demographicCodingRepository.findAll().size();
        // set the field null
        demographicCoding.setName(null);

        // Create the DemographicCoding, which fails.

        restDemographicCodingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isBadRequest());

        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = demographicCodingRepository.findAll().size();
        // set the field null
        demographicCoding.setCode(null);

        // Create the DemographicCoding, which fails.

        restDemographicCodingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isBadRequest());

        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemographicCodings() throws Exception {
        // Initialize the database
        demographicCodingRepository.saveAndFlush(demographicCoding);

        // Get all the demographicCodingList
        restDemographicCodingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demographicCoding.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getDemographicCoding() throws Exception {
        // Initialize the database
        demographicCodingRepository.saveAndFlush(demographicCoding);

        // Get the demographicCoding
        restDemographicCodingMockMvc
            .perform(get(ENTITY_API_URL_ID, demographicCoding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demographicCoding.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemographicCoding() throws Exception {
        // Get the demographicCoding
        restDemographicCodingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemographicCoding() throws Exception {
        // Initialize the database
        demographicCodingRepository.saveAndFlush(demographicCoding);

        int databaseSizeBeforeUpdate = demographicCodingRepository.findAll().size();

        // Update the demographicCoding
        DemographicCoding updatedDemographicCoding = demographicCodingRepository.findById(demographicCoding.getId()).get();
        // Disconnect from session so that the updates on updatedDemographicCoding are not directly saved in db
        em.detach(updatedDemographicCoding);
        updatedDemographicCoding.name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restDemographicCodingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemographicCoding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemographicCoding))
            )
            .andExpect(status().isOk());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeUpdate);
        DemographicCoding testDemographicCoding = demographicCodingList.get(demographicCodingList.size() - 1);
        assertThat(testDemographicCoding.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDemographicCoding.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDemographicCoding.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingDemographicCoding() throws Exception {
        int databaseSizeBeforeUpdate = demographicCodingRepository.findAll().size();
        demographicCoding.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemographicCodingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demographicCoding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemographicCoding() throws Exception {
        int databaseSizeBeforeUpdate = demographicCodingRepository.findAll().size();
        demographicCoding.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemographicCodingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemographicCoding() throws Exception {
        int databaseSizeBeforeUpdate = demographicCodingRepository.findAll().size();
        demographicCoding.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemographicCodingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemographicCodingWithPatch() throws Exception {
        // Initialize the database
        demographicCodingRepository.saveAndFlush(demographicCoding);

        int databaseSizeBeforeUpdate = demographicCodingRepository.findAll().size();

        // Update the demographicCoding using partial update
        DemographicCoding partialUpdatedDemographicCoding = new DemographicCoding();
        partialUpdatedDemographicCoding.setId(demographicCoding.getId());

        partialUpdatedDemographicCoding.name(UPDATED_NAME).code(UPDATED_CODE);

        restDemographicCodingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemographicCoding.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemographicCoding))
            )
            .andExpect(status().isOk());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeUpdate);
        DemographicCoding testDemographicCoding = demographicCodingList.get(demographicCodingList.size() - 1);
        assertThat(testDemographicCoding.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDemographicCoding.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDemographicCoding.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateDemographicCodingWithPatch() throws Exception {
        // Initialize the database
        demographicCodingRepository.saveAndFlush(demographicCoding);

        int databaseSizeBeforeUpdate = demographicCodingRepository.findAll().size();

        // Update the demographicCoding using partial update
        DemographicCoding partialUpdatedDemographicCoding = new DemographicCoding();
        partialUpdatedDemographicCoding.setId(demographicCoding.getId());

        partialUpdatedDemographicCoding.name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restDemographicCodingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemographicCoding.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemographicCoding))
            )
            .andExpect(status().isOk());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeUpdate);
        DemographicCoding testDemographicCoding = demographicCodingList.get(demographicCodingList.size() - 1);
        assertThat(testDemographicCoding.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDemographicCoding.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDemographicCoding.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingDemographicCoding() throws Exception {
        int databaseSizeBeforeUpdate = demographicCodingRepository.findAll().size();
        demographicCoding.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemographicCodingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demographicCoding.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemographicCoding() throws Exception {
        int databaseSizeBeforeUpdate = demographicCodingRepository.findAll().size();
        demographicCoding.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemographicCodingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemographicCoding() throws Exception {
        int databaseSizeBeforeUpdate = demographicCodingRepository.findAll().size();
        demographicCoding.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemographicCodingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demographicCoding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemographicCoding in the database
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemographicCoding() throws Exception {
        // Initialize the database
        demographicCodingRepository.saveAndFlush(demographicCoding);

        int databaseSizeBeforeDelete = demographicCodingRepository.findAll().size();

        // Delete the demographicCoding
        restDemographicCodingMockMvc
            .perform(delete(ENTITY_API_URL_ID, demographicCoding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemographicCoding> demographicCodingList = demographicCodingRepository.findAll();
        assertThat(demographicCodingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
