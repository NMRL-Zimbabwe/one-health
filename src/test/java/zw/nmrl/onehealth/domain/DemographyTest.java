package zw.nmrl.onehealth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zw.nmrl.onehealth.web.rest.TestUtil;

class DemographyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Demography.class);
        Demography demography1 = new Demography();
        demography1.setId(1L);
        Demography demography2 = new Demography();
        demography2.setId(demography1.getId());
        assertThat(demography1).isEqualTo(demography2);
        demography2.setId(2L);
        assertThat(demography1).isNotEqualTo(demography2);
        demography1.setId(null);
        assertThat(demography1).isNotEqualTo(demography2);
    }
}
