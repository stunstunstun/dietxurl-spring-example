package com.holaxapps.dietxurl.v1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holaxapps.dietxurl.domain.ShortenLink;
import com.holaxapps.dietxurl.domain.ShortenLinkRepository;
import com.holaxapps.dietxurl.exception.ResourceNotFoundException;
import com.holaxapps.dietxurl.support.Base62;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author minhyeok
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortenServiceTests {

    @MockBean
    private ShortenLinkRepository shortenLinkRepository;

    @Autowired
    private ShortenLinkService shortenLinkService;

    private JacksonTester<ShortenLink> resource;

    private ShortenLink entity;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        entity = resource.readObject(new ClassPathResource("shorten_link_resource.json"));
    }

    @Test
    public void create() {
        given(shortenLinkRepository.findOneByOriginLink(entity.getOriginLink())).willReturn(null);
        given(shortenLinkRepository.count()).willReturn(0L);

        entity.setLinkId(Base62.encodeToString(entity.getSequence()));
        given(shortenLinkRepository.save(entity)).willReturn(entity);

        assertThat(shortenLinkService.create(entity)).isEqualTo(entity);
    }

    @Test
    public void createAndNotSave() {
        given(shortenLinkRepository.findOneByOriginLink(entity.getOriginLink())).willReturn(entity);
        assertThat(shortenLinkService.create(entity)).isEqualTo(entity);
    }

    @Test
    public void findOneByLinkId() {
        given(shortenLinkRepository.findOneByLinkId(entity.getLinkId())).willReturn(entity);
        assertThat(shortenLinkService.findOneByLinkId(entity.getLinkId())).isEqualTo(entity);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findOneByLinkIdResourceNotFound() {
        given(shortenLinkRepository.findOneByLinkId(entity.getLinkId())).willReturn(null);
        assertThat(shortenLinkService.findOneByLinkId(entity.getLinkId())).isEqualTo(entity);
    }
}
