package com.holaxapps.dietxurl.v1.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holaxapps.dietxurl.EndPoints;
import com.holaxapps.dietxurl.domain.ShortenLink;
import com.holaxapps.dietxurl.v1.service.ShortenLinkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author minhyeok
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ShortenLinkController.class)
public class ShortenLinkControllerTests {

    @MockBean
    private ShortenLinkService shortenLinkService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<ShortenLink> resource;

    private ShortenLink entity;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        entity = resource.readObject(new ClassPathResource("shorten_link_resource.json"));
    }

    @Test
    public void findOneByLinkId() throws Exception {
        given(shortenLinkService.findOneByLinkId(entity.getLinkId())).willReturn(entity);

        mvc.perform(get(EndPoints.SHORTEN_URL_API + "/{linkId}", entity.getLinkId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sequence", is(entity.getSequence().intValue())))
                .andExpect(jsonPath("$.originLink", is(entity.getOriginLink())))
                .andExpect(jsonPath("$.linkId", is(entity.getLinkId())));
    }

    @Test
    public void save() throws Exception {
        given(shortenLinkService.create(entity)).willReturn(entity);

        mvc.perform(post(EndPoints.SHORTEN_URL_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resource.write(entity).getJson()))
                .andExpect(status().isCreated());
    }

    @Test
    public void saveAndUnsupportedMediaType() throws Exception {
        given(shortenLinkService.create(entity)).willReturn(entity);

        mvc.perform(post(EndPoints.SHORTEN_URL_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_XML)
                .content("<urls></urls>"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void saveAndRequestBodyNotReadable() throws Exception {
        given(shortenLinkService.create(entity)).willReturn(entity);

        mvc.perform(post(EndPoints.SHORTEN_URL_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
