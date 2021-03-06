package org.kryptonmlt.damselbuster.controllers;

import java.util.List;
import org.kryptonmlt.damselbuster.pojos.Platform;
import org.kryptonmlt.damselbuster.repositories.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes platforms stored in database
 *
 * @author Kurt
 */
@RestController()
@RequestMapping("/platforms")
public class PlatformController {

    @Autowired
    private PlatformRepository platformRepository;

    /**
     * Retrieves all platforms
     *
     * @return platforms all the platforms
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Platform> getPlatforms() {
        return (List<Platform>) platformRepository.findAll();
    }

    /**
     * Retrieves 1 platform
     *
     * @param platformId platform id
     * @return the corresponding platform
     */
    @RequestMapping(value = "/{platformId}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Platform getPlatform(@PathVariable long platformId) {
        return platformRepository.findOne(platformId);
    }

}
