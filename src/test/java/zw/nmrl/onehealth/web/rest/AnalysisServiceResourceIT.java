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
import zw.nmrl.onehealth.domain.AnalysisService;
import zw.nmrl.onehealth.repository.AnalysisServiceRepository;

/**
 * Integration tests for the {@link AnalysisServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalysisServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/analysis-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalysisServiceRepository analysisServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalysisServiceMockMvc;

    private AnalysisService analysisService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalysisService createEntity(EntityManager em) {
        AnalysisService analysisService = new AnalysisService().name(DEFAULT_NAME);
        return analysisService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalysisService createUpdatedEntity(EntityManager em) {
        AnalysisService analysisService = new AnalysisService().name(UPDATED_NAME);
        return analysisService;
    }

    @BeforeEach
    public void initTest() {
        analysisService = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalysisService() throws Exception {
        int databaseSizeBeforeCreate = analysisServiceRepository.findAll().size();
        // Create the AnalysisService
        restAnalysisServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysisService))
            )
            .andExpect(status().isCreated());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeCreate + 1);
        AnalysisService testAnalysisService = analysisServiceList.get(analysisServiceList.size() - 1);
        assertThat(testAnalysisService.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createAnalysisServiceWithExistingId() throws Exception {
        // Create the AnalysisService with an existing ID
        analysisService.setId(1L);

        int databaseSizeBeforeCreate = analysisServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalysisServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysisService))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisServiceRepository.findAll().size();
        // set the field null
        analysisService.setName(null);

        // Create the AnalysisService, which fails.

        restAnalysisServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysisService))
            )
            .andExpect(status().isBadRequest());

        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnalysisServices() throws Exception {
        // Initialize the database
        analysisServiceRepository.saveAndFlush(analysisService);

        // Get all the analysisServiceList
        restAnalysisServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analysisService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getAnalysisService() throws Exception {
        // Initialize the database
        analysisServiceRepository.saveAndFlush(analysisService);

        // Get the analysisService
        restAnalysisServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, analysisService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analysisService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingAnalysisService() throws Exception {
        // Get the analysisService
        restAnalysisServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnalysisService() throws Exception {
        // Initialize the database
        analysisServiceRepository.saveAndFlush(analysisService);

        int databaseSizeBeforeUpdate = analysisServiceRepository.findAll().size();

        // Update the analysisService
        AnalysisService updatedAnalysisService = analysisServiceRepository.findById(analysisService.getId()).get();
        // Disconnect from session so that the updates on updatedAnalysisService are not directly saved in db
        em.detach(updatedAnalysisService);
        updatedAnalysisService.name(UPDATED_NAME);

        restAnalysisServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnalysisService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnalysisService))
            )
            .andExpect(status().isOk());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeUpdate);
        AnalysisService testAnalysisService = analysisServiceList.get(analysisServiceList.size() - 1);
        assertThat(testAnalysisService.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingAnalysisService() throws Exception {
        int databaseSizeBeforeUpdate = analysisServiceRepository.findAll().size();
        analysisService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalysisServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analysisService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analysisService))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalysisService() throws Exception {
        int databaseSizeBeforeUpdate = analysisServiceRepository.findAll().size();
        analysisService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analysisService))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalysisService() throws Exception {
        int databaseSizeBeforeUpdate = analysisServiceRepository.findAll().size();
        analysisService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisServiceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analysisService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalysisServiceWithPatch() throws Exception {
        // Initialize the database
        analysisServiceRepository.saveAndFlush(analysisService);

        int databaseSizeBeforeUpdate = analysisServiceRepository.findAll().size();

        // Update the analysisService using partial update
        AnalysisService partialUpdatedAnalysisService = new AnalysisService();
        partialUpdatedAnalysisService.setId(analysisService.getId());

        partialUpdatedAnalysisService.name(UPDATED_NAME);

        restAnalysisServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalysisService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalysisService))
            )
            .andExpect(status().isOk());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeUpdate);
        AnalysisService testAnalysisService = analysisServiceList.get(analysisServiceList.size() - 1);
        assertThat(testAnalysisService.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateAnalysisServiceWithPatch() throws Exception {
        // Initialize the database
        analysisServiceRepository.saveAndFlush(analysisService);

        int databaseSizeBeforeUpdate = analysisServiceRepository.findAll().size();

        // Update the analysisService using partial update
        AnalysisService partialUpdatedAnalysisService = new AnalysisService();
        partialUpdatedAnalysisService.setId(analysisService.getId());

        partialUpdatedAnalysisService.name(UPDATED_NAME);

        restAnalysisServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalysisService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalysisService))
            )
            .andExpect(status().isOk());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeUpdate);
        AnalysisService testAnalysisService = analysisServiceList.get(analysisServiceList.size() - 1);
        assertThat(testAnalysisService.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingAnalysisService() throws Exception {
        int databaseSizeBeforeUpdate = analysisServiceRepository.findAll().size();
        analysisService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalysisServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analysisService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analysisService))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalysisService() throws Exception {
        int databaseSizeBeforeUpdate = analysisServiceRepository.findAll().size();
        analysisService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analysisService))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalysisService() throws Exception {
        int databaseSizeBeforeUpdate = analysisServiceRepository.findAll().size();
        analysisService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalysisServiceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analysisService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalysisService in the database
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalysisService() throws Exception {
        // Initialize the database
        analysisServiceRepository.saveAndFlush(analysisService);

        int databaseSizeBeforeDelete = analysisServiceRepository.findAll().size();

        // Delete the analysisService
        restAnalysisServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, analysisService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalysisService> analysisServiceList = analysisServiceRepository.findAll();
        assertThat(analysisServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
