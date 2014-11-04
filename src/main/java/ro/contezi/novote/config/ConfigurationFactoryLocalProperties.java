package ro.contezi.novote.config;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import ro.contezi.novote.model.Candidate;

/**
 *
 * @author Catalin
 */
@Stateless
@Local
public class ConfigurationFactoryLocalProperties extends
		ConfigurationFactoryProperties implements ConfigurationFactory {

	/**
	 * the properties default path
	 */
	public static final String PROPERTIES_FILE_PATH = "application.properties";
	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger(ConfigurationFactoryLocalProperties.class.getName());

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Override
	public Properties getProperties() {
		return InstanceHolder.CONFIG_PROPERTIES;
	}

	private static final class InstanceHolder {
		private static final Properties CONFIG_PROPERTIES;

		static {
			Properties config = new Properties();
			try {
				InputStream is = ConfigurationFactoryLocalProperties.class
						.getClassLoader().getResourceAsStream(
								PROPERTIES_FILE_PATH);

				LOGGER.log(Level.INFO, "Input Stream is: {0}", is);

				config.load(is);
			} catch (IOException ex) {
				LOGGER.log(Level.SEVERE, null, ex);
				throw new ExceptionInInitializerError(ex);
			}
			CONFIG_PROPERTIES = config;
		}

		private InstanceHolder() {

		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Produces
	@Config
	@Override
	public String getConfiguration(InjectionPoint p) {
		return super.getConfiguration(p);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Override
	@Produces
	@Config
	public DateFormat getConfigurationDateFormat(InjectionPoint p) {
		return super.getConfigurationDateFormat(p);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Produces
	@Config
	@Override
	public Double getConfigurationDouble(InjectionPoint p) {
		return super.getConfigurationDouble(p);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Produces
	@Config
	@Override
	public Integer getConfigurationInteger(InjectionPoint p) {
		return super.getConfigurationInteger(p);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Produces
	@Config
	@Override
	public String[] getConfigurationStringArray(InjectionPoint p) {
		return super.getConfigurationStringArray(p);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Produces
	@Config
	@Override
	public Candidate[] getConfigurationCandidateArray(InjectionPoint p) {
		return super.getConfigurationCandidateArray(p);
	}	

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Produces
	@Config
	@Override
	public Long getConfigurationLong(InjectionPoint p) {
		return super.getConfigurationLong(p);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Produces
	@Config
	@Override
	public MessageFormat getConfigurationMessageFormat(InjectionPoint p) {
		return super.getConfigurationMessageFormat(p);
	}

}
