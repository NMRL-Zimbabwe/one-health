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
import zw.nmrl.onehealth.domain.Sample;
import zw.nmrl.onehealth.repository.SampleRepository;

/**
 * Integration tests for the {@link SampleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SampleResourceIT {

    private static final String DEFAULT_SAMPLE_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYSIS_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS_SERVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/samples";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSampleMockMvc;

    private Sample sample;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sample createEntity(EntityManager em) {
        Sample sample = new Sample()
            .sampleTypeId(DEFAULT_SAMPLE_TYPE_ID)
            .analysisServiceId(DEFAULT_ANALYSIS_SERVICE_ID)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE);
        return sample;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sample createUpdatedEntity(EntityManager em) {
        Sample sample = new Sample()
            .sampleTypeId(UPDATED_SAMPLE_TYPE_ID)
            .analysisServiceId(UPDATED_ANALYSIS_SERVICE_ID)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);
        return sample;
    }

    @BeforeEach
    public void initTest() {
        sample = createEntity(em);
    }

    @Test
    @Transactional
    void createSample() throws Exception {
        int databaseSizeBeforeCreate = sampleRepository.findAll().size();
        // Create the Sample
        restSampleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isCreated());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeCreate + 1);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getSampleTypeId()).isEqualTo(DEFAULT_SAMPLE_TYPE_ID);
        assertThat(testSample.getAnalysisServiceId()).isEqualTo(DEFAULT_ANALYSIS_SERVICE_ID);
        assertThat(testSample.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testSample.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    void createSampleWithExistingId() throws Exception {
        // Create the Sample with an existing ID
        sample.setId(1L);

        int databaseSizeBeforeCreate = sampleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSampleTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = sampleRepository.findAll().size();
        // set the field null
        sample.setSampleTypeId(null);

        // Create the Sample, which fails.

        restSampleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnalysisServiceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = sampleRepository.findAll().size();
        // set the field null
        sample.setAnalysisServiceId(null);

        // Create the Sample, which fails.

        restSampleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSamples() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        // Get all the sampleList
        restSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sample.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleTypeId").value(hasItem(DEFAULT_SAMPLE_TYPE_ID)))
            .andExpect(jsonPath("$.[*].analysisServiceId").value(hasItem(DEFAULT_ANALYSIS_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)));
    }

    @Test
    @Transactional
    void getSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        // Get the sample
        restSampleMockMvc
            .perform(get(ENTITY_API_URL_ID, sample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sample.getId().intValue()))
            .andExpect(jsonPath("$.sampleTypeId").value(DEFAULT_SAMPLE_TYPE_ID))
            .andExpect(jsonPath("$.analysisServiceId").value(DEFAULT_ANALYSIS_SERVICE_ID))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE));
    }

    @Test
    @Transactional
    void getNonExistingSample() throws Exception {
        // Get the sample
        restSampleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Update the sample
        Sample updatedSample = sampleRepository.findById(sample.getId()).get();
        // Disconnect from session so that the updates on updatedSample are not directly saved in db
        em.detach(updatedSample);
        updatedSample
            .sampleTypeId(UPDATED_SAMPLE_TYPE_ID)
            .analysisServiceId(UPDATED_ANALYSIS_SERVICE_ID)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);

        restSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSample.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSample))
            )
            .andExpect(status().isOk());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getSampleTypeId()).isEqualTo(UPDATED_SAMPLE_TYPE_ID);
        assertThat(testSample.getAnalysisServiceId()).isEqualTo(UPDATED_ANALYSIS_SERVICE_ID);
        assertThat(testSample.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSample.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void putNonExistingSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sample.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sample))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sample))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSampleWithPatch() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Update the sample using partial update
        Sample partialUpdatedSample = new Sample();
        partialUpdatedSample.setId(sample.getId());

        partialUpdatedSample.longitude(UPDATED_LONGITUDE).latitude(UPDATED_LATITUDE);

        restSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSample))
            )
            .andExpect(status().isOk());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getSampleTypeId()).isEqualTo(DEFAULT_SAMPLE_TYPE_ID);
        assertThat(testSample.getAnalysisServiceId()).isEqualTo(DEFAULT_ANALYSIS_SERVICE_ID);
        assertThat(testSample.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSample.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void fullUpdateSampleWithPatch() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Update the sample using partial update
        Sample partialUpdatedSample = new Sample();
        partialUpdatedSample.setId(sample.getId());

        partialUpdatedSample
            .sampleTypeId(UPDATED_SAMPLE_TYPE_ID)
            .analysisServiceId(UPDATED_ANALYSIS_SERVICE_ID)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);

        restSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSample))
            )
            .andExpect(status().isOk());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getSampleTypeId()).isEqualTo(UPDATED_SAMPLE_TYPE_ID);
        assertThat(testSample.getAnalysisServiceId()).isEqualTo(UPDATED_ANALYSIS_SERVICE_ID);
        assertThat(testSample.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSample.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void patchNonExistingSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sample))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sample))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeDelete = sampleRepository.findAll().size();

        // Delete the sample
        restSampleMockMvc
            .perform(delete(ENTITY_API_URL_ID, sample.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
