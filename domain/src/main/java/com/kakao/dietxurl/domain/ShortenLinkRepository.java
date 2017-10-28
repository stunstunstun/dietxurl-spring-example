package com.kakao.dietxurl.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * @author minhyeok
 */
public interface ShortenLinkRepository extends CrudRepository<ShortenLink, Long> {

    ShortenLink findOneByOriginLink(String originLink);

    ShortenLink findOneByLinkId(String linkId);
}
