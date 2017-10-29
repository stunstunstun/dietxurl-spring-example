package com.holaxapps.dietxurl.v1.service;

import com.holaxapps.dietxurl.domain.ShortenLink;
import com.holaxapps.dietxurl.domain.ShortenLinkRepository;
import com.holaxapps.dietxurl.exception.ResourceNotFoundException;
import com.holaxapps.dietxurl.support.Base62;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author minhyeok
 */
@Component("shortenLinkService")
public class ShortenLinkService {

    private final ShortenLinkRepository shortenLinkRepository;

    public ShortenLinkService(ShortenLinkRepository shortenLinkRepository) {
        this.shortenLinkRepository = shortenLinkRepository;
    }

    public ShortenLink findOneByLinkId(String linkId) {
        ShortenLink entity = shortenLinkRepository.findOneByLinkId(linkId);
        if (ObjectUtils.isEmpty(entity)) {
            throw new ResourceNotFoundException();
        }
        return entity;
    }

    public synchronized ShortenLink create(ShortenLink link) {
        ShortenLink entity = shortenLinkRepository.findOneByOriginLink(link.getOriginLink());
        if (!ObjectUtils.isEmpty(entity)) {
            return entity;
        }
        link.setLinkId(Base62.encodeToString(Long.sum(shortenLinkRepository.count(), System.currentTimeMillis())));
        return shortenLinkRepository.save(link);
    }
}
