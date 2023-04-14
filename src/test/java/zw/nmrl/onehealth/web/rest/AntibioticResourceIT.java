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
import zw.nmrl.onehealth.domain.Antibiotic;
import zw.nmrl.onehealth.repository.AntibioticRepository;

/**
 * Integration tests for the {@link AntibioticResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AntibioticResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CLASS_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/antibiotics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AntibioticRepository antibioticRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAntibioticMockMvc;

    private Antibiotic antibiotic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Antibiotic createEntity(EntityManager em) {
        Antibiotic antibiotic = new Antibiotic()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .classId(DEFAULT_CLASS_ID);
        return antibiotic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Antibiotic createUpdatedEntity(EntityManager em) {
        Antibiotic antibiotic = new Antibiotic()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .classId(UPDATED_CLASS_ID);
        return antibiotic;
    }

    @BeforeEach
    public void initTest() {
        antibiotic = createEntity(em);
    }

    @Test
    @Transactional
    void createAntibiotic() throws Exception {
        int databaseSizeBeforeCreate = antibioticRepository.findAll().size();
        // Create the Antibiotic
        restAntibioticMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibiotic)))
            .andExpect(status().isCreated());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeCreate + 1);
        Antibiotic testAntibiotic = antibioticList.get(antibioticList.size() - 1);
        assertThat(testAntibiotic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAntibiotic.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAntibiotic.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAntibiotic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAntibiotic.getClassId()).isEqualTo(DEFAULT_CLASS_ID);
    }

    @Test
    @Transactional
    void createAntibioticWithExistingId() throws Exception {
        // Create the Antibiotic with an existing ID
        antibiotic.setId(1L);

        int databaseSizeBeforeCreate = antibioticRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntibioticMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibiotic)))
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = antibioticRepository.findAll().size();
        // set the field null
        antibiotic.setName(null);

        // Create the Antibiotic, which fails.

        restAntibioticMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibiotic)))
            .andExpect(status().isBadRequest());

        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClassIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = antibioticRepository.findAll().size();
        // set the field null
        antibiotic.setClassId(null);

        // Create the Antibiotic, which fails.

        restAntibioticMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibiotic)))
            .andExpect(status().isBadRequest());

        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAntibiotics() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList
        restAntibioticMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antibiotic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].classId").value(hasItem(DEFAULT_CLASS_ID)));
    }

    @Test
    @Transactional
    void getAntibiotic() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get the antibiotic
        restAntibioticMockMvc
            .perform(get(ENTITY_API_URL_ID, antibiotic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(antibiotic.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.classId").value(DEFAULT_CLASS_ID));
    }

    @Test
    @Transactional
    void getNonExistingAntibiotic() throws Exception {
        // Get the antibiotic
        restAntibioticMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAntibiotic() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();

        // Update the antibiotic
        Antibiotic updatedAntibiotic = antibioticRepository.findById(antibiotic.getId()).get();
        // Disconnect from session so that the updates on updatedAntibiotic are not directly saved in db
        em.detach(updatedAntibiotic);
        updatedAntibiotic
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .classId(UPDATED_CLASS_ID);

        restAntibioticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAntibiotic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAntibiotic))
            )
            .andExpect(status().isOk());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
        Antibiotic testAntibiotic = antibioticList.get(antibioticList.size() - 1);
        assertThat(testAntibiotic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAntibiotic.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAntibiotic.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAntibiotic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAntibiotic.getClassId()).isEqualTo(UPDATED_CLASS_ID);
    }

    @Test
    @Transactional
    void putNonExistingAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, antibiotic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(antibiotic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(antibiotic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibiotic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAntibioticWithPatch() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();

        // Update the antibiotic using partial update
        Antibiotic partialUpdatedAntibiotic = new Antibiotic();
        partialUpdatedAntibiotic.setId(antibiotic.getId());

        partialUpdatedAntibiotic.name(UPDATED_NAME).code(UPDATED_CODE).status(UPDATED_STATUS);

        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAntibiotic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAntibiotic))
            )
            .andExpect(status().isOk());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
        Antibiotic testAntibiotic = antibioticList.get(antibioticList.size() - 1);
        assertThat(testAntibiotic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAntibiotic.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAntibiotic.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAntibiotic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAntibiotic.getClassId()).isEqualTo(DEFAULT_CLASS_ID);
    }

    @Test
    @Transactional
    void fullUpdateAntibioticWithPatch() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();

        // Update the antibiotic using partial update
        Antibiotic partialUpdatedAntibiotic = new Antibiotic();
        partialUpdatedAntibiotic.setId(antibiotic.getId());

        partialUpdatedAntibiotic
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .classId(UPDATED_CLASS_ID);

        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAntibiotic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAntibiotic))
            )
            .andExpect(status().isOk());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
        Antibiotic testAntibiotic = antibioticList.get(antibioticList.size() - 1);
        assertThat(testAntibiotic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAntibiotic.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAntibiotic.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAntibiotic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAntibiotic.getClassId()).isEqualTo(UPDATED_CLASS_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, antibiotic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(antibiotic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(antibiotic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(antibiotic))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAntibiotic() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        int databaseSizeBeforeDelete = antibioticRepository.findAll().size();

        // Delete the antibiotic
        restAntibioticMockMvc
            .perform(delete(ENTITY_API_URL_ID, antibiotic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
