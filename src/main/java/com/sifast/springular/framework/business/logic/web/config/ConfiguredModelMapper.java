package com.sifast.springular.framework.business.logic.web.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ConfiguredModelMapper extends ModelMapper {

    public ConfiguredModelMapper() {
        super();
        this.getConfiguration().setAmbiguityIgnored(true);
        this.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }

}