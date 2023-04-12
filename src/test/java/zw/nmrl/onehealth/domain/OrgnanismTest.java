package zw.nmrl.onehealth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zw.nmrl.onehealth.web.rest.TestUtil;

class OrgnanismTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orgnanism.class);
        Orgnanism orgnanism1 = new Orgnanism();
        orgnanism1.setId(1L);
        Orgnanism orgnanism2 = new Orgnanism();
        orgnanism2.setId(orgnanism1.getId());
        assertThat(orgnanism1).isEqualTo(orgnanism2);
        orgnanism2.setId(2L);
        assertThat(orgnanism1).isNotEqualTo(orgnanism2);
        orgnanism1.setId(null);
        assertThat(orgnanism1).isNotEqualTo(orgnanism2);
    }
}
