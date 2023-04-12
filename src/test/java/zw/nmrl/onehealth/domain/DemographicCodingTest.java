package zw.nmrl.onehealth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zw.nmrl.onehealth.web.rest.TestUtil;

class DemographicCodingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemographicCoding.class);
        DemographicCoding demographicCoding1 = new DemographicCoding();
        demographicCoding1.setId(1L);
        DemographicCoding demographicCoding2 = new DemographicCoding();
        demographicCoding2.setId(demographicCoding1.getId());
        assertThat(demographicCoding1).isEqualTo(demographicCoding2);
        demographicCoding2.setId(2L);
        assertThat(demographicCoding1).isNotEqualTo(demographicCoding2);
        demographicCoding1.setId(null);
        assertThat(demographicCoding1).isNotEqualTo(demographicCoding2);
    }
}
