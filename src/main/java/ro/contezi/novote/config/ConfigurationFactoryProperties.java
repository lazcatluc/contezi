package ro.contezi.novote.config;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

import javax.enterprise.inject.spi.InjectionPoint;

import ro.contezi.novote.model.Candidate;

/**
 *
 * @author Catalin
 */
public abstract class ConfigurationFactoryProperties implements ConfigurationFactory {


    /**
     * {@inheritDoc}
     *
     * @param p
     * @return
     */
    @Override
    public String getConfiguration(InjectionPoint p) {
        Properties config = getProperties();
        /**
         * If the user specified the name of the property to lookup, use that name.
         */
        String configKey = p.getAnnotated().getAnnotation(Config.class).name();
        if (configKey != null && !configKey.trim().isEmpty()) {
            return config.getProperty(configKey);
        }
        /**
         * Go for canonical name first: ro.contezi.mavenproject1.openapi.ExampleClass.exampleField
         */
        configKey = p.getMember().getDeclaringClass().getCanonicalName() + "." + p.getMember().getName();
        if (config.getProperty(configKey) != null) {
            return config.getProperty(configKey);
        }
        /**
         * Go for simple class name second: ExampleClass.exampleField
         */
        configKey = p.getMember().getDeclaringClass().getSimpleName() + "." + p.getMember().getName();
        if (config.getProperty(configKey) == null) {
            /**
             * Otherwise, simply try the field name exampleField.
             */
            configKey = p.getMember().getName();
        }
        return config.getProperty(configKey);
    }

    /**
     * {@inheritDoc}
     * @param p
     * @return
     */
    @Override
    public DateFormat getConfigurationDateFormat(InjectionPoint p) {
        String val = getConfiguration(p);
        return new SimpleDateFormat(val, new Locale("ro"));
    }


    /**
     * {@inheritDoc}
     * @param p
     * @return
     */
    @Override
    public Double getConfigurationDouble(InjectionPoint p) {
        String val = getConfiguration(p);
        return Double.parseDouble(val);
    }

    /**
     * {@inheritDoc}
     * @param p
     * @return
     */
    @Override
    public Integer getConfigurationInteger(InjectionPoint p) {
        String val = getConfiguration(p);
        return Integer.parseInt(val);
    }

    /**
     * {@inheritDoc}
     * @param p
     * @return
     */
    @Override
    public Long getConfigurationLong(InjectionPoint p) {
        String val = getConfiguration(p);
        return Long.parseLong(val);
    }


    @Override
    public String[] getConfigurationStringArray(InjectionPoint p) {
        String val = getConfiguration(p);
        return val.split(",");
    }
    
    @Override
    public Candidate[] getConfigurationCandidateArray(InjectionPoint p) {
        String[] candidateNames = getConfigurationStringArray(p);
        Candidate[] candidates = new Candidate[candidateNames.length];
        int i = 0;
        for (String candidateName : candidateNames) {
        	Candidate candidate = new Candidate();
        	candidate.setName(candidateName);
        	candidates[i++] = candidate;
        }
        return candidates;
    }

    /**
     * {@inheritDoc}
     * @param p
     * @return
     */
    @Override
    public MessageFormat getConfigurationMessageFormat(InjectionPoint p) {
        String val = getConfiguration(p);
        return new MessageFormat(val);
    }

    /**
     *
     * @return the current properties for this configuration
     */
    public abstract Properties getProperties();

}
