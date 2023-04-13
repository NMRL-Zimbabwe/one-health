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
import zw.nmrl.onehealth.domain.LaboratoryRequest;
import zw.nmrl.onehealth.repository.LaboratoryRequestRepository;

/**
 * Integration tests for the {@link LaboratoryRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LaboratoryRequestResourceIT {

    private static final String DEFAULT_SAMPLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SAMPLE_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_TYPE_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_COLLECTED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_COLLECTED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RECEIVED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEIVED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SAMPLE_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_CONDITION = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SECTOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_SECTOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_ID = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROCINCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCINCE_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/laboratory-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LaboratoryRequestRepository laboratoryRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLaboratoryRequestMockMvc;

    private LaboratoryRequest laboratoryRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LaboratoryRequest createEntity(EntityManager em) {
        LaboratoryRequest laboratoryRequest = new LaboratoryRequest()
            .sampleId(DEFAULT_SAMPLE_ID)
            .sampleTypeId(DEFAULT_SAMPLE_TYPE_ID)
            .dateCollected(DEFAULT_DATE_COLLECTED)
            .dateReceived(DEFAULT_DATE_RECEIVED)
            .sampleCondition(DEFAULT_SAMPLE_CONDITION)
            .clientId(DEFAULT_CLIENT_ID)
            .priority(DEFAULT_PRIORITY)
            .status(DEFAULT_STATUS)
            .remarks(DEFAULT_REMARKS)
            .locationId(DEFAULT_LOCATION_ID)
            .sectorId(DEFAULT_SECTOR_ID)
            .districtId(DEFAULT_DISTRICT_ID)
            .procinceId(DEFAULT_PROCINCE_ID);
        return laboratoryRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LaboratoryRequest createUpdatedEntity(EntityManager em) {
        LaboratoryRequest laboratoryRequest = new LaboratoryRequest()
            .sampleId(UPDATED_SAMPLE_ID)
            .sampleTypeId(UPDATED_SAMPLE_TYPE_ID)
            .dateCollected(UPDATED_DATE_COLLECTED)
            .dateReceived(UPDATED_DATE_RECEIVED)
            .sampleCondition(UPDATED_SAMPLE_CONDITION)
            .clientId(UPDATED_CLIENT_ID)
            .priority(UPDATED_PRIORITY)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .locationId(UPDATED_LOCATION_ID)
            .sectorId(UPDATED_SECTOR_ID)
            .districtId(UPDATED_DISTRICT_ID)
            .procinceId(UPDATED_PROCINCE_ID);
        return laboratoryRequest;
    }

    @BeforeEach
    public void initTest() {
        laboratoryRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createLaboratoryRequest() throws Exception {
        int databaseSizeBeforeCreate = laboratoryRequestRepository.findAll().size();
        // Create the LaboratoryRequest
        restLaboratoryRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isCreated());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeCreate + 1);
        LaboratoryRequest testLaboratoryRequest = laboratoryRequestList.get(laboratoryRequestList.size() - 1);
        assertThat(testLaboratoryRequest.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testLaboratoryRequest.getSampleTypeId()).isEqualTo(DEFAULT_SAMPLE_TYPE_ID);
        assertThat(testLaboratoryRequest.getDateCollected()).isEqualTo(DEFAULT_DATE_COLLECTED);
        assertThat(testLaboratoryRequest.getDateReceived()).isEqualTo(DEFAULT_DATE_RECEIVED);
        assertThat(testLaboratoryRequest.getSampleCondition()).isEqualTo(DEFAULT_SAMPLE_CONDITION);
        assertThat(testLaboratoryRequest.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testLaboratoryRequest.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLaboratoryRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLaboratoryRequest.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testLaboratoryRequest.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testLaboratoryRequest.getSectorId()).isEqualTo(DEFAULT_SECTOR_ID);
        assertThat(testLaboratoryRequest.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
        assertThat(testLaboratoryRequest.getProcinceId()).isEqualTo(DEFAULT_PROCINCE_ID);
    }

    @Test
    @Transactional
    void createLaboratoryRequestWithExistingId() throws Exception {
        // Create the LaboratoryRequest with an existing ID
        laboratoryRequest.setId(1L);

        int databaseSizeBeforeCreate = laboratoryRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLaboratoryRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSampleIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratoryRequestRepository.findAll().size();
        // set the field null
        laboratoryRequest.setSampleId(null);

        // Create the LaboratoryRequest, which fails.

        restLaboratoryRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isBadRequest());

        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSampleTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratoryRequestRepository.findAll().size();
        // set the field null
        laboratoryRequest.setSampleTypeId(null);

        // Create the LaboratoryRequest, which fails.

        restLaboratoryRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isBadRequest());

        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLaboratoryRequests() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        // Get all the laboratoryRequestList
        restLaboratoryRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(laboratoryRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleId").value(hasItem(DEFAULT_SAMPLE_ID)))
            .andExpect(jsonPath("$.[*].sampleTypeId").value(hasItem(DEFAULT_SAMPLE_TYPE_ID)))
            .andExpect(jsonPath("$.[*].dateCollected").value(hasItem(DEFAULT_DATE_COLLECTED.toString())))
            .andExpect(jsonPath("$.[*].dateReceived").value(hasItem(DEFAULT_DATE_RECEIVED.toString())))
            .andExpect(jsonPath("$.[*].sampleCondition").value(hasItem(DEFAULT_SAMPLE_CONDITION)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID)))
            .andExpect(jsonPath("$.[*].sectorId").value(hasItem(DEFAULT_SECTOR_ID)))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID)))
            .andExpect(jsonPath("$.[*].procinceId").value(hasItem(DEFAULT_PROCINCE_ID)));
    }

    @Test
    @Transactional
    void getLaboratoryRequest() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        // Get the laboratoryRequest
        restLaboratoryRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, laboratoryRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(laboratoryRequest.getId().intValue()))
            .andExpect(jsonPath("$.sampleId").value(DEFAULT_SAMPLE_ID))
            .andExpect(jsonPath("$.sampleTypeId").value(DEFAULT_SAMPLE_TYPE_ID))
            .andExpect(jsonPath("$.dateCollected").value(DEFAULT_DATE_COLLECTED.toString()))
            .andExpect(jsonPath("$.dateReceived").value(DEFAULT_DATE_RECEIVED.toString()))
            .andExpect(jsonPath("$.sampleCondition").value(DEFAULT_SAMPLE_CONDITION))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID))
            .andExpect(jsonPath("$.sectorId").value(DEFAULT_SECTOR_ID))
            .andExpect(jsonPath("$.districtId").value(DEFAULT_DISTRICT_ID))
            .andExpect(jsonPath("$.procinceId").value(DEFAULT_PROCINCE_ID));
    }

    @Test
    @Transactional
    void getNonExistingLaboratoryRequest() throws Exception {
        // Get the laboratoryRequest
        restLaboratoryRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLaboratoryRequest() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();

        // Update the laboratoryRequest
        LaboratoryRequest updatedLaboratoryRequest = laboratoryRequestRepository.findById(laboratoryRequest.getId()).get();
        // Disconnect from session so that the updates on updatedLaboratoryRequest are not directly saved in db
        em.detach(updatedLaboratoryRequest);
        updatedLaboratoryRequest
            .sampleId(UPDATED_SAMPLE_ID)
            .sampleTypeId(UPDATED_SAMPLE_TYPE_ID)
            .dateCollected(UPDATED_DATE_COLLECTED)
            .dateReceived(UPDATED_DATE_RECEIVED)
            .sampleCondition(UPDATED_SAMPLE_CONDITION)
            .clientId(UPDATED_CLIENT_ID)
            .priority(UPDATED_PRIORITY)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .locationId(UPDATED_LOCATION_ID)
            .sectorId(UPDATED_SECTOR_ID)
            .districtId(UPDATED_DISTRICT_ID)
            .procinceId(UPDATED_PROCINCE_ID);

        restLaboratoryRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLaboratoryRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLaboratoryRequest))
            )
            .andExpect(status().isOk());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
        LaboratoryRequest testLaboratoryRequest = laboratoryRequestList.get(laboratoryRequestList.size() - 1);
        assertThat(testLaboratoryRequest.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testLaboratoryRequest.getSampleTypeId()).isEqualTo(UPDATED_SAMPLE_TYPE_ID);
        assertThat(testLaboratoryRequest.getDateCollected()).isEqualTo(UPDATED_DATE_COLLECTED);
        assertThat(testLaboratoryRequest.getDateReceived()).isEqualTo(UPDATED_DATE_RECEIVED);
        assertThat(testLaboratoryRequest.getSampleCondition()).isEqualTo(UPDATED_SAMPLE_CONDITION);
        assertThat(testLaboratoryRequest.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testLaboratoryRequest.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLaboratoryRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLaboratoryRequest.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testLaboratoryRequest.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testLaboratoryRequest.getSectorId()).isEqualTo(UPDATED_SECTOR_ID);
        assertThat(testLaboratoryRequest.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testLaboratoryRequest.getProcinceId()).isEqualTo(UPDATED_PROCINCE_ID);
    }

    @Test
    @Transactional
    void putNonExistingLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, laboratoryRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLaboratoryRequestWithPatch() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();

        // Update the laboratoryRequest using partial update
        LaboratoryRequest partialUpdatedLaboratoryRequest = new LaboratoryRequest();
        partialUpdatedLaboratoryRequest.setId(laboratoryRequest.getId());

        partialUpdatedLaboratoryRequest
            .sampleTypeId(UPDATED_SAMPLE_TYPE_ID)
            .locationId(UPDATED_LOCATION_ID)
            .districtId(UPDATED_DISTRICT_ID);

        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLaboratoryRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLaboratoryRequest))
            )
            .andExpect(status().isOk());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
        LaboratoryRequest testLaboratoryRequest = laboratoryRequestList.get(laboratoryRequestList.size() - 1);
        assertThat(testLaboratoryRequest.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testLaboratoryRequest.getSampleTypeId()).isEqualTo(UPDATED_SAMPLE_TYPE_ID);
        assertThat(testLaboratoryRequest.getDateCollected()).isEqualTo(DEFAULT_DATE_COLLECTED);
        assertThat(testLaboratoryRequest.getDateReceived()).isEqualTo(DEFAULT_DATE_RECEIVED);
        assertThat(testLaboratoryRequest.getSampleCondition()).isEqualTo(DEFAULT_SAMPLE_CONDITION);
        assertThat(testLaboratoryRequest.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testLaboratoryRequest.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testLaboratoryRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLaboratoryRequest.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testLaboratoryRequest.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testLaboratoryRequest.getSectorId()).isEqualTo(DEFAULT_SECTOR_ID);
        assertThat(testLaboratoryRequest.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testLaboratoryRequest.getProcinceId()).isEqualTo(DEFAULT_PROCINCE_ID);
    }

    @Test
    @Transactional
    void fullUpdateLaboratoryRequestWithPatch() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();

        // Update the laboratoryRequest using partial update
        LaboratoryRequest partialUpdatedLaboratoryRequest = new LaboratoryRequest();
        partialUpdatedLaboratoryRequest.setId(laboratoryRequest.getId());

        partialUpdatedLaboratoryRequest
            .sampleId(UPDATED_SAMPLE_ID)
            .sampleTypeId(UPDATED_SAMPLE_TYPE_ID)
            .dateCollected(UPDATED_DATE_COLLECTED)
            .dateReceived(UPDATED_DATE_RECEIVED)
            .sampleCondition(UPDATED_SAMPLE_CONDITION)
            .clientId(UPDATED_CLIENT_ID)
            .priority(UPDATED_PRIORITY)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .locationId(UPDATED_LOCATION_ID)
            .sectorId(UPDATED_SECTOR_ID)
            .districtId(UPDATED_DISTRICT_ID)
            .procinceId(UPDATED_PROCINCE_ID);

        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLaboratoryRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLaboratoryRequest))
            )
            .andExpect(status().isOk());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
        LaboratoryRequest testLaboratoryRequest = laboratoryRequestList.get(laboratoryRequestList.size() - 1);
        assertThat(testLaboratoryRequest.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testLaboratoryRequest.getSampleTypeId()).isEqualTo(UPDATED_SAMPLE_TYPE_ID);
        assertThat(testLaboratoryRequest.getDateCollected()).isEqualTo(UPDATED_DATE_COLLECTED);
        assertThat(testLaboratoryRequest.getDateReceived()).isEqualTo(UPDATED_DATE_RECEIVED);
        assertThat(testLaboratoryRequest.getSampleCondition()).isEqualTo(UPDATED_SAMPLE_CONDITION);
        assertThat(testLaboratoryRequest.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testLaboratoryRequest.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testLaboratoryRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLaboratoryRequest.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testLaboratoryRequest.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testLaboratoryRequest.getSectorId()).isEqualTo(UPDATED_SECTOR_ID);
        assertThat(testLaboratoryRequest.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testLaboratoryRequest.getProcinceId()).isEqualTo(UPDATED_PROCINCE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, laboratoryRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLaboratoryRequest() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRequestRepository.findAll().size();
        laboratoryRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratoryRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LaboratoryRequest in the database
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLaboratoryRequest() throws Exception {
        // Initialize the database
        laboratoryRequestRepository.saveAndFlush(laboratoryRequest);

        int databaseSizeBeforeDelete = laboratoryRequestRepository.findAll().size();

        // Delete the laboratoryRequest
        restLaboratoryRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, laboratoryRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LaboratoryRequest> laboratoryRequestList = laboratoryRequestRepository.findAll();
        assertThat(laboratoryRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
