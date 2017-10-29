package com.holaxapps.dietxurl.v1.api;

import com.holaxapps.dietxurl.EndPoints;
import com.holaxapps.dietxurl.v1.service.ShortenLinkService;
import com.holaxapps.dietxurl.domain.ShortenLink;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author minhyeok
 */
@RestController
@RequestMapping(value = EndPoints.SHORTEN_URL_API)
public class ShortenLinkController {

    private final ShortenLinkService shortenLinkService;

    public ShortenLinkController(ShortenLinkService shortenLinkService) {
        this.shortenLinkService = shortenLinkService;
    }

    @RequestMapping(value = "/{linkId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ShortenLink> findOneByLinkId(@PathVariable String linkId) {
        return new ResponseEntity<ShortenLink>(shortenLinkService.findOneByLinkId(linkId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<ShortenLink> save(@RequestBody ShortenLink link) {
        return new ResponseEntity<ShortenLink>(shortenLinkService.create(link), HttpStatus.CREATED);
    }
}
