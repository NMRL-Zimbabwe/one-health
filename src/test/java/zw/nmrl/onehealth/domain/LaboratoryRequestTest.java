package zw.nmrl.onehealth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zw.nmrl.onehealth.web.rest.TestUtil;

class LaboratoryRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LaboratoryRequest.class);
        LaboratoryRequest laboratoryRequest1 = new LaboratoryRequest();
        laboratoryRequest1.setId(1L);
        LaboratoryRequest laboratoryRequest2 = new LaboratoryRequest();
        laboratoryRequest2.setId(laboratoryRequest1.getId());
        assertThat(laboratoryRequest1).isEqualTo(laboratoryRequest2);
        laboratoryRequest2.setId(2L);
        assertThat(laboratoryRequest1).isNotEqualTo(laboratoryRequest2);
        laboratoryRequest1.setId(null);
        assertThat(laboratoryRequest1).isNotEqualTo(laboratoryRequest2);
    }
}
