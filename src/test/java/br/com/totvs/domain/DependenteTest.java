package br.com.totvs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.totvs.web.rest.TestUtil;

public class DependenteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dependente.class);
        Dependente dependente1 = new Dependente();
        dependente1.setId(1L);
        Dependente dependente2 = new Dependente();
        dependente2.setId(dependente1.getId());
        assertThat(dependente1).isEqualTo(dependente2);
        dependente2.setId(2L);
        assertThat(dependente1).isNotEqualTo(dependente2);
        dependente1.setId(null);
        assertThat(dependente1).isNotEqualTo(dependente2);
    }
}
