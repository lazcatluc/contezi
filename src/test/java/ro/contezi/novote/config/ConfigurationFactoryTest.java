package ro.contezi.novote.config;


import java.lang.reflect.Member;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Catalin
 */
public class ConfigurationFactoryTest {

    private ConfigurationFactory instance;
    protected ConfigurationFactory getConfigurationFactoryInstance() {
        return new ConfigurationFactoryProperties() {

            private Properties properties;

            @Override
            public Properties getProperties() {
                if (properties == null) {
                    properties = new Properties();
                    properties.setProperty("key1","value1");
                    properties.setProperty("configParam","hello\\!\\!\\!\\!");
                    properties.setProperty("double1","0.01");
                    properties.setProperty("integer1","1");
                    properties.setProperty("key2","value2");
                    properties.setProperty(ConfigurationFactoryTest.class.getSimpleName()+".key3","value3");
                    properties.setProperty(ConfigurationFactoryTest.class.getCanonicalName()+".key4","value4");
                    properties.setProperty("stringArray", "a,b,c");
                    properties.setProperty("test", "ejb test");
                    properties.setProperty("dateFormat","dd/MM/yyyy");
                    properties.setProperty("messageFormat", "Hello {0}!");
                }
                return properties;
            }
        };
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected InjectionPoint mockInjectionPoint(String memberName, String annotationConfigName) {

        Config mockConfig = mock(Config.class);
        when(mockConfig.name()).thenReturn(annotationConfigName);

        Member mockMember = mock(Member.class);
        when(mockMember.getDeclaringClass()).thenReturn((Class)ConfigurationFactoryTest.class);
        when(mockMember.getName()).thenReturn(memberName);

        Annotated mockAnnotated = mock(Annotated.class);
        when(mockAnnotated.getAnnotation(Config.class)).thenReturn(mockConfig);

        InjectionPoint ret = mock(InjectionPoint.class);
        when(ret.getMember()).thenReturn(mockMember);
        when(ret.getAnnotated()).thenReturn(mockAnnotated);
        return ret;

    }

    @Before
    public void setUp() {
        instance = getConfigurationFactoryInstance();
    }

    /**
     * Test of getConfiguration method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationNonExistentProperty() throws Exception {
        InjectionPoint p = mockInjectionPoint("undefinedField", null);
        String result = instance.getConfiguration(p);
        String expectedResult = null;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of getConfiguration method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationFromAnnotationName() throws Exception {
        InjectionPoint p = mockInjectionPoint(null, "key1");
        String result = instance.getConfiguration(p);
        String expectedResult = "value1";
        assertEquals(expectedResult, result);
    }

    /**
     * Test of getConfiguration method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationFromAnnotationNameEvenIfFieldIsDefined() throws Exception {
        InjectionPoint p = mockInjectionPoint("key2", "key1");
        String result = instance.getConfiguration(p);
        String expectedResult = "value1";
        assertEquals(expectedResult, result);
    }

    /**
     * Test of getConfiguration method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationFromFieldName() throws Exception {
        InjectionPoint p = mockInjectionPoint("key2", null);
        String result = instance.getConfiguration(p);
        String expectedResult = "value2";
        assertEquals(expectedResult, result);
    }

    /**
     * Test of getConfiguration method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationFromClassFieldName() throws Exception {
        InjectionPoint p = mockInjectionPoint("key3", null);
        String result = instance.getConfiguration(p);
        String expectedResult = "value3";
        assertEquals(expectedResult, result);
    }

    /**
     * Test of getConfiguration method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationFromFullyQualifiedClassFieldName() throws Exception {
        InjectionPoint p = mockInjectionPoint("key4", null);
        String expectedResult = "value4";

        String result = instance.getConfiguration(p);

        assertEquals(expectedResult, result);
    }

    /**
     * Test of getConfigurationDouble method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationDouble() throws Exception {
        InjectionPoint p = mockInjectionPoint(null, "double1");
        Double expResult = 0.01d;
        Double result = instance.getConfigurationDouble(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of getConfigurationInteger method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationInteger() throws Exception {
        InjectionPoint p = mockInjectionPoint(null, "integer1");
        Integer expResult = 1;
        Integer result = instance.getConfigurationInteger(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of getConfigurationLong method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationLong() throws Exception {
        InjectionPoint p = mockInjectionPoint(null, "integer1");
        Long expResult = 1l;
        Long result = instance.getConfigurationLong(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of getConfigurationLong method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationStringArray() throws Exception {
        InjectionPoint p = mockInjectionPoint(null, "stringArray");
        String[] expResult = new String[]{"a","b","c"};
        String[] result = instance.getConfigurationStringArray(p);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getConfigurationLong method, of class ConfigurationFactoryLocalProperties.
     */
    @Test
    public void testGetConfigurationDateFormat() throws Exception {
        InjectionPoint p = mockInjectionPoint(null, "dateFormat");
        String expectedResult = "01/01/1970";
        Date firstDate = new Date(0);

        DateFormat df = instance.getConfigurationDateFormat(p);
        String result = df.format(firstDate);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetConfigurationMessageFormat() throws Exception {
        InjectionPoint p = mockInjectionPoint(null, "messageFormat");
        String expectedResult = "Hello World!";

        MessageFormat mf = instance.getConfigurationMessageFormat(p);
        String result = mf.format(new String[]{"World"});

        assertEquals(expectedResult, result);
    }
}