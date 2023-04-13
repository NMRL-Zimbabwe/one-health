package zw.nmrl.onehealth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zw.nmrl.onehealth.web.rest.TestUtil;

class ClientAddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientAddress.class);
        ClientAddress clientAddress1 = new ClientAddress();
        clientAddress1.setId(1L);
        ClientAddress clientAddress2 = new ClientAddress();
        clientAddress2.setId(clientAddress1.getId());
        assertThat(clientAddress1).isEqualTo(clientAddress2);
        clientAddress2.setId(2L);
        assertThat(clientAddress1).isNotEqualTo(clientAddress2);
        clientAddress1.setId(null);
        assertThat(clientAddress1).isNotEqualTo(clientAddress2);
    }
}
