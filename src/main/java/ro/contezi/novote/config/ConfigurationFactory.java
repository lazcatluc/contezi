package ro.contezi.novote.config;


import java.text.DateFormat;
import java.text.MessageFormat;

import javax.enterprise.inject.spi.InjectionPoint;

import ro.contezi.novote.model.Candidate;

/**
 *
 * @author Catalin
 */
public interface ConfigurationFactory {

    /**
     * The main configuration provider. Looks up a value
     * and injects it as a string.
     *
     * @param p the injection point for this current value
     * @return the value to be injected
     */
    String getConfiguration(InjectionPoint p);

    /**
     * Looks up a value and injects it as a double.
     *
     * @param p the injection point for this current value
     * @return the value to be injected
     */
    Double getConfigurationDouble(InjectionPoint p);

    /**
     * Looks up a value and injects it as an integer.
     *
     * @param p the injection point for this current value
     * @return the value to be injected
     */
    Integer getConfigurationInteger(InjectionPoint p);

    /**
     * Looks up a value and injects it as a long.
     *
     * @param p the injection point for this current value
     * @return the value to be injected
     */
    Long getConfigurationLong(InjectionPoint p);

    /**
     * Looks up a value and injects it as a string array.
     *
     * The value should be comma-separated.
     *
     * @param p the injection point for this current value
     * @return the value to be injected
     */
    String[] getConfigurationStringArray(InjectionPoint p);
    
    /**
     * Looks up a value and injects it as a string array.
     *
     * The value should be comma-separated.
     *
     * @param p the injection point for this current value
     * @return the value to be injected
     */
    Candidate[] getConfigurationCandidateArray(InjectionPoint p);

    /**
     * Looks up a value and injects it as a date format.
     *
     * @param p the injection point for this current value
     * @return the value to be injected
     */
    DateFormat getConfigurationDateFormat(InjectionPoint p);

    /**
     * Looks up a value and injects it as a message format.
     *
     * @param p the injection point for this current value
     * @return the value to be injected
     */
    MessageFormat getConfigurationMessageFormat(InjectionPoint p);
}
