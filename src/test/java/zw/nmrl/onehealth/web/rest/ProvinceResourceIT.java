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
import zw.nmrl.onehealth.domain.Province;
import zw.nmrl.onehealth.repository.ProvinceRepository;

/**
 * Integration tests for the {@link ProvinceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProvinceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_ID = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/provinces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProvinceMockMvc;

    private Province province;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Province createEntity(EntityManager em) {
        Province province = new Province()
            .name(DEFAULT_NAME)
            .countryId(DEFAULT_COUNTRY_ID)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE);
        return province;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Province createUpdatedEntity(EntityManager em) {
        Province province = new Province()
            .name(UPDATED_NAME)
            .countryId(UPDATED_COUNTRY_ID)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);
        return province;
    }

    @BeforeEach
    public void initTest() {
        province = createEntity(em);
    }

    @Test
    @Transactional
    void createProvince() throws Exception {
        int databaseSizeBeforeCreate = provinceRepository.findAll().size();
        // Create the Province
        restProvinceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(province)))
            .andExpect(status().isCreated());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeCreate + 1);
        Province testProvince = provinceList.get(provinceList.size() - 1);
        assertThat(testProvince.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProvince.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
        assertThat(testProvince.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testProvince.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    void createProvinceWithExistingId() throws Exception {
        // Create the Province with an existing ID
        province.setId(1L);

        int databaseSizeBeforeCreate = provinceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvinceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(province)))
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinceRepository.findAll().size();
        // set the field null
        province.setName(null);

        // Create the Province, which fails.

        restProvinceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(province)))
            .andExpect(status().isBadRequest());

        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinceRepository.findAll().size();
        // set the field null
        province.setCountryId(null);

        // Create the Province, which fails.

        restProvinceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(province)))
            .andExpect(status().isBadRequest());

        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProvinces() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        // Get all the provinceList
        restProvinceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(province.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(DEFAULT_COUNTRY_ID)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)));
    }

    @Test
    @Transactional
    void getProvince() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        // Get the province
        restProvinceMockMvc
            .perform(get(ENTITY_API_URL_ID, province.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(province.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.countryId").value(DEFAULT_COUNTRY_ID))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE));
    }

    @Test
    @Transactional
    void getNonExistingProvince() throws Exception {
        // Get the province
        restProvinceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProvince() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();

        // Update the province
        Province updatedProvince = provinceRepository.findById(province.getId()).get();
        // Disconnect from session so that the updates on updatedProvince are not directly saved in db
        em.detach(updatedProvince);
        updatedProvince.name(UPDATED_NAME).countryId(UPDATED_COUNTRY_ID).longitude(UPDATED_LONGITUDE).latitude(UPDATED_LATITUDE);

        restProvinceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProvince.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProvince))
            )
            .andExpect(status().isOk());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeUpdate);
        Province testProvince = provinceList.get(provinceList.size() - 1);
        assertThat(testProvince.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProvince.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testProvince.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testProvince.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void putNonExistingProvince() throws Exception {
        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();
        province.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, province.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(province))
            )
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProvince() throws Exception {
        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();
        province.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(province))
            )
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProvince() throws Exception {
        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();
        province.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(province)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProvinceWithPatch() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();

        // Update the province using partial update
        Province partialUpdatedProvince = new Province();
        partialUpdatedProvince.setId(province.getId());

        partialUpdatedProvince.name(UPDATED_NAME).longitude(UPDATED_LONGITUDE);

        restProvinceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvince.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProvince))
            )
            .andExpect(status().isOk());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeUpdate);
        Province testProvince = provinceList.get(provinceList.size() - 1);
        assertThat(testProvince.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProvince.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
        assertThat(testProvince.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testProvince.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    void fullUpdateProvinceWithPatch() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();

        // Update the province using partial update
        Province partialUpdatedProvince = new Province();
        partialUpdatedProvince.setId(province.getId());

        partialUpdatedProvince.name(UPDATED_NAME).countryId(UPDATED_COUNTRY_ID).longitude(UPDATED_LONGITUDE).latitude(UPDATED_LATITUDE);

        restProvinceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvince.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProvince))
            )
            .andExpect(status().isOk());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeUpdate);
        Province testProvince = provinceList.get(provinceList.size() - 1);
        assertThat(testProvince.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProvince.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testProvince.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testProvince.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void patchNonExistingProvince() throws Exception {
        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();
        province.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, province.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(province))
            )
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProvince() throws Exception {
        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();
        province.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(province))
            )
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProvince() throws Exception {
        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();
        province.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(province)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Province in the database
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProvince() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        int databaseSizeBeforeDelete = provinceRepository.findAll().size();

        // Delete the province
        restProvinceMockMvc
            .perform(delete(ENTITY_API_URL_ID, province.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Province> provinceList = provinceRepository.findAll();
        assertThat(provinceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
