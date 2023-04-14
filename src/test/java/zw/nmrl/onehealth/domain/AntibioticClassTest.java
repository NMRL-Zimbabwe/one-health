package zw.nmrl.onehealth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zw.nmrl.onehealth.web.rest.TestUtil;

class AntibioticClassTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntibioticClass.class);
        AntibioticClass antibioticClass1 = new AntibioticClass();
        antibioticClass1.setId(1L);
        AntibioticClass antibioticClass2 = new AntibioticClass();
        antibioticClass2.setId(antibioticClass1.getId());
        assertThat(antibioticClass1).isEqualTo(antibioticClass2);
        antibioticClass2.setId(2L);
        assertThat(antibioticClass1).isNotEqualTo(antibioticClass2);
        antibioticClass1.setId(null);
        assertThat(antibioticClass1).isNotEqualTo(antibioticClass2);
    }
}
