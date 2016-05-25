package org.ozwillo.dcexporter.service;

import org.ozwillo.dcexporter.dao.DcModelMappingRepository;
import org.ozwillo.dcexporter.model.DcModelMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Temporary initializer to bootstrap some DC to CKAN mappings.
 *
 * In the future, they will be created and managed from the application's interface
 */
@Component
public class DCModelMappingInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DCModelMappingInitializer.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DcModelMappingRepository dcModelMappingRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (dcModelMappingRepository.count() > 0) {
            LOGGER.info("Already some data in DC model mapping, returning");
            return;
        }

        LOGGER.info("Initializing sample DC model mappings");

        DcModelMapping orgMapping = new DcModelMapping("http://data.ozwillo.com/dc/type/dcmo:model_0/org:Organization_0",
            "org_1", "org:Organization_0", "Organisations");
        orgMapping.setCkanPackageId("organisations");
        orgMapping.setCkanResourceId("f7d1d5dc-45c3-48fc-bca4-d9d98ba50d3a");
        dcModelMappingRepository.save(orgMapping);

        DcModelMapping poiMapping = new DcModelMapping("http://data.ozwillo.com/dc/type/dcmo:model_0/poi:Geoloc_0",
            "poi_0", "poi:Geoloc_0", "Points d'intérêt");
        poiMapping.setCkanPackageId("points-interet-poi");
        poiMapping.setCkanResourceId("b4fca7f7-773a-4bca-87f0-f54437082817");
        dcModelMappingRepository.save(poiMapping);
    }
}