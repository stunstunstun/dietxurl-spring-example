package com.holaxapps.dietxurl.domain;

import com.holaxapps.dietxurl.AbstractDataJpaTest;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author minhyeok
 */
public class ShortenLinkRepositoryTests extends AbstractDataJpaTest {

    @Autowired
    private ShortenLinkRepository shortenLinkRepository;

    @Test
    public void findAll() {
        Iterable<ShortenLink> links = shortenLinkRepository.findAll();
        assertThat(Lists.newArrayList(links).size()).isEqualTo(3);
    }

    @Test
    public void findOne() {
        ShortenLink entity = shortenLinkRepository.findOne(1L);
        assertThat(entity).isNotNull();
    }

    @Test
    public void findOneByOriginLink() {
        ShortenLink entity = shortenLinkRepository.findOneByOriginLink("https://www.holaxprogramming.com/2017/08/16/java-history/");
        assertThat(entity).isNotNull();
    }

    @Test
    public void findOneByLinkId() {
        ShortenLink entity = shortenLinkRepository.findOneByLinkId("aaaaaa");
        assertThat(entity).isNotNull();
    }

    @Test
    public void empty() {
        ShortenLink entity = shortenLinkRepository.findOneByLinkId("eeeeee");
        assertThat(entity).isNull();
    }

    @Test
    public void save() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("ffffff");
        entity.setOriginLink("http://www.kakao.com/kakaopay/");

        entity = shortenLinkRepository.save(entity);
        assertThat(shortenLinkRepository.exists(entity.getSequence())).isTrue();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithLinkIdIsNull() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId(null);
        entity.setOriginLink("http://www.kakao.com/kakaopay/");

        shortenLinkRepository.save(entity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithOriginLinkIsNull() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("ffffff");
        entity.setOriginLink(null);

        shortenLinkRepository.save(entity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithLinkIdIsTooLong() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("fffffffff");
        entity.setOriginLink(null);

        assertThat(entity.getLinkId().length() > 8).isTrue();
        shortenLinkRepository.save(entity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithDuplicatedOriginLink() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("ffffff");
        entity.setOriginLink("https://www.holaxprogramming.com/2017/08/16/java-history/");

        shortenLinkRepository.save(entity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithDuplicatedLinkId() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("aaaaaa");
        entity.setOriginLink("http://www.kakao.com/kakaopay/");

        shortenLinkRepository.save(entity);
    }
}
