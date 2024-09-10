package com.yao.app.spi.annotation.gem;

import com.yao.app.spi.annotation.Custom;
import org.mapstruct.tools.gem.GemDefinition;
import org.mapstruct.tools.gem.GemDefinitions;

@GemDefinitions({@GemDefinition(Custom.class)})
public interface CustomGemGenerator {

}
