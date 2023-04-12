package zw.nmrl.onehealth.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
import zw.nmrl.onehealth.domain.Analysis;
import zw.nmrl.onehealth.repository.AnalysisRepository;

/**
 * Integration tests for the {@link AnalysisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalysisResourceIT {

    private static final String DEFAULT_SAMPLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYSIS_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS_SERVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RESULTED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RESULTED = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/analyses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalysisMockMvc;

    private Analysis analysis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Analysis createEntity(EntityManager em) {
        Analysis analysis = new Analysis()
            .sampleId(DEFAULT_SAMPLE_ID)
            .analysisServiceId(DEFAULT_ANALYSIS_SERVICE_ID)
            .result(DEFAULT_RESULT)
            .dateResulted(DEFAULT_DATE_RESULTED);
        return analysis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Analysis createUpdatedEntity(EntityManager em) {
        Analysis analysis = new Analysis()
            .sampleId(UPDATED_SAMPLE_ID)
            .analysisServiceId(UPDATED_ANALYSIS_SERVICE_ID)
            .result(UPDATED_RESULT)
            .dateResulted(UPDATED_DATE_RESULTED);
        return analysis;
    }

    @BeforeEach
    public void initTest() {
        analysis = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalysis() throws Exception {
        int databaseSizeBeforeCreate = analysisRepository.findAll().size();
        // Create the Analysis
        restAnalysisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isCreated());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate + 1);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testAnalysis.getAnalysisServiceId()).isEqualTo(DEFAULT_ANALYSIS_SERVICE_ID);
        assertThat(testAnalysis.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testAnalysis.getDateResulted()).isEqualTo(DEFAULT_DATE_RESULTED);
    }

    @Test
    @Transactional
    void createAnalysisWithExistingId() throws Exception {
        // Create the Analysis with an existing ID
        analysis.setId(1L);

        int databaseSizeBeforeCreate = analysisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalysisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSampleIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisRepository.findAll().size();
        // set the field null
        analysis.setSampleId(null);

        // Create the Analysis, which fails.

        restAnalysisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isBadRequest());

        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnalysisServiceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisRepository.findAll().size();
        // set the field null
        analysis.setAnalysisServiceId(null);

        // Create the Analysis, which fails.

        restAnalysisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isBadRequest());

        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnalyses() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        // Get all the analysisList
        restAnalysisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleId").value(hasItem(DEFAULT_SAMPLE_ID)))
            .andExpect(jsonPath("$.[*].analysisServiceId").value(hasItem(DEFAULT_ANALYSIS_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].dateResulted").value(hasItem(DEFAULT_DATE_RESULTED.toString())));
    }

    @Test
    @Transactional
    void getAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        // Get the analysis
        restAnalysisMockMvc
            .perform(get(ENTITY_API_URL_ID, analysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analysis.getId().intValue()))
            .andExpect(jsonPath("$.sampleId").value(DEFAULT_SAMPLE_ID))
            .andExpect(jsonPath("$.analysisServiceId").value(DEFAULT_ANALYSIS_SERVICE_ID))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.dateResulted").value(DEFAULT_DATE_RESULTED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnalysis() throws Exception {
        // Get the analysis
        restAnalysisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Update the analysis
        Analysis updatedAnalysis = analysisRepository.findById(analysis.getId()).get();
        // Disconnect from session so that the updates on updatedAnalysis are not directly saved in db
        em.detach(updatedAnalysis);
        updatedAnalysis
            .sampleId(UPDATED_SAMPLE_ID)
            .analysisServiceId(UPDATED_ANALYSIS_SERVICE_ID)
            .result(UPDATED_RESULT)
            .dateResulted(UPDATED_DATE_RESULTED);

        restAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnalysis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnalysis))
            )
            .andExpect(status().isOk());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testAnalysis.getAnalysisServiceId()).isEqualTo(UPDATED_ANALYSIS_SERVICE_ID);
        assertThat(testAnalysis.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testAnalysis.getDateResulted()).isEqualTo(UPDATED_DATE_RESULTED);
    }

    @Test
    @Transactional
    void putNonExistingAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();
        analysis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analysis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analysis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();
        analysis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analysis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();
        analysis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalysisWithPatch() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Update the analysis using partial update
        Analysis partialUpdatedAnalysis = new Analysis();
        partialUpdatedAnalysis.setId(analysis.getId());

        partialUpdatedAnalysis.analysisServiceId(UPDATED_ANALYSIS_SERVICE_ID).result(UPDATED_RESULT).dateResulted(UPDATED_DATE_RESULTED);

        restAnalysisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalysis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalysis))
            )
            .andExpect(status().isOk());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testAnalysis.getAnalysisServiceId()).isEqualTo(UPDATED_ANALYSIS_SERVICE_ID);
        assertThat(testAnalysis.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testAnalysis.getDateResulted()).isEqualTo(UPDATED_DATE_RESULTED);
    }

    @Test
    @Transactional
    void fullUpdateAnalysisWithPatch() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Update the analysis using partial update
        Analysis partialUpdatedAnalysis = new Analysis();
        partialUpdatedAnalysis.setId(analysis.getId());

        partialUpdatedAnalysis
            .sampleId(UPDATED_SAMPLE_ID)
            .analysisServiceId(UPDATED_ANALYSIS_SERVICE_ID)
            .result(UPDATED_RESULT)
            .dateResulted(UPDATED_DATE_RESULTED);

        restAnalysisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalysis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalysis))
            )
            .andExpect(status().isOk());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testAnalysis.getAnalysisServiceId()).isEqualTo(UPDATED_ANALYSIS_SERVICE_ID);
        assertThat(testAnalysis.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testAnalysis.getDateResulted()).isEqualTo(UPDATED_DATE_RESULTED);
    }

    @Test
    @Transactional
    void patchNonExistingAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();
        analysis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalysisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analysis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analysis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();
        analysis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analysis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();
        analysis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        int databaseSizeBeforeDelete = analysisRepository.findAll().size();

        // Delete the analysis
        restAnalysisMockMvc
            .perform(delete(ENTITY_API_URL_ID, analysis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
