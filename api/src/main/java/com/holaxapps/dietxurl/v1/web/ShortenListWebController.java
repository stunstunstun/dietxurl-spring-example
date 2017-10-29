package com.holaxapps.dietxurl.v1.web;

import com.holaxapps.dietxurl.EndPoints;
import com.holaxapps.dietxurl.v1.service.ShortenLinkService;
import com.holaxapps.dietxurl.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author minhyeok
 */
@Controller
@RequestMapping(value = EndPoints.SHORTEN_URL_PAGE)
public class ShortenListWebController {

    private final Logger logger = LoggerFactory.getLogger(ShortenListWebController.class);

    private final ShortenLinkService shortenLinkService;

    public ShortenListWebController(ShortenLinkService shortenLinkService) {
        this.shortenLinkService = shortenLinkService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("{linkId}")
    public String redirectToOriginalPage(@PathVariable("linkId") String linkId) {
        return "redirect:" + shortenLinkService.findOneByLinkId(linkId).getOriginLink();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "index";
    }
}