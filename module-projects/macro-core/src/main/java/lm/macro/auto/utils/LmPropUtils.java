package lm.macro.auto.utils;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.PropertiesBuilderParameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.springframework.core.io.Resource;

public class LmPropUtils {
    public static ImmutableConfiguration load(String path) {
        try {
            Resource resource = LmResourceUtils.getResource(path);
            assert resource != null;
            return new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class).configure(basePropertiesBuilderParameters().setFile(resource.getFile())).getConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static PropertiesBuilderParameters basePropertiesBuilderParameters() {
        return new Parameters()
                .properties()
                .setEncoding("utf-8")
                .setThrowExceptionOnMissing(true)
                .setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
                .setIncludesAllowed(false);
    }
}
