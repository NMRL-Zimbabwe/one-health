package zw.nmrl.onehealth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zw.nmrl.onehealth.web.rest.TestUtil;

class ClientPhoneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientPhone.class);
        ClientPhone clientPhone1 = new ClientPhone();
        clientPhone1.setId(1L);
        ClientPhone clientPhone2 = new ClientPhone();
        clientPhone2.setId(clientPhone1.getId());
        assertThat(clientPhone1).isEqualTo(clientPhone2);
        clientPhone2.setId(2L);
        assertThat(clientPhone1).isNotEqualTo(clientPhone2);
        clientPhone1.setId(null);
        assertThat(clientPhone1).isNotEqualTo(clientPhone2);
    }
}
