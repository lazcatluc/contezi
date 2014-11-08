package ro.contezi.novote.config;

import java.util.Properties;
import javax.enterprise.inject.spi.InjectionPoint;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Catalin
 */
public class ConfigurationFactoryLocalPropertiesTest extends ConfigurationFactoryTest {

    private final InjectionPoint p = mockInjectionPoint("undefinedField", null);
    
    /**
     * Test of getProperties method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetProperties() throws Exception {
        Properties result = new ConfigurationFactoryLocalProperties().getProperties();

        assertNotNull(result);
    }

    @Test
    public void testGetConfiguration() throws Exception {
        new ConfigurationFactoryLocalProperties().getConfiguration(p);
    }

    @Test(expected = NullPointerException.class)
    @Override
    public void testGetConfigurationDouble() throws Exception {
        new ConfigurationFactoryLocalProperties().getConfigurationDouble(p);
    }

    @Test(expected = NullPointerException.class)
    @Override
    public void testGetConfigurationDateFormat() throws Exception {
        new ConfigurationFactoryLocalProperties().getConfigurationDouble(p);
    }

    @Test(expected = NumberFormatException.class)
    @Override
    public void testGetConfigurationInteger() throws Exception {
        new ConfigurationFactoryLocalProperties().getConfigurationInteger(p);
    }

    @Test(expected = NumberFormatException.class)
    @Override
    public void testGetConfigurationLong() throws Exception {
        new ConfigurationFactoryLocalProperties().getConfigurationLong(p);
    }

    @Test(expected = NullPointerException.class)
    @Override
    public void testGetConfigurationStringArray() throws Exception {
        new ConfigurationFactoryLocalProperties().getConfigurationStringArray(p);
    }

    @Test(expected = NullPointerException.class)
    @Override
    public void testGetConfigurationMessageFormat() throws Exception {
        new ConfigurationFactoryLocalProperties().getConfigurationMessageFormat(p);
    }
}

