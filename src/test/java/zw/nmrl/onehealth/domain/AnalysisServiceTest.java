package zw.nmrl.onehealth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zw.nmrl.onehealth.web.rest.TestUtil;

class AnalysisServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalysisService.class);
        AnalysisService analysisService1 = new AnalysisService();
        analysisService1.setId(1L);
        AnalysisService analysisService2 = new AnalysisService();
        analysisService2.setId(analysisService1.getId());
        assertThat(analysisService1).isEqualTo(analysisService2);
        analysisService2.setId(2L);
        assertThat(analysisService1).isNotEqualTo(analysisService2);
        analysisService1.setId(null);
        assertThat(analysisService1).isNotEqualTo(analysisService2);
    }
}
