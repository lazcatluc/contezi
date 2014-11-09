package ro.contezi.novote.validator;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.RegexValidator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import ro.contezi.novote.config.Config;
import ro.contezi.novote.repository.RegistrationRepository;

@ManagedBean(name = "cnpValidator")
@SessionScoped
public class CnpValidator implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final long COUNTING_BASE = 10;
	private static final long CNP_CRC_KEY = 279_146_358_279L;
	private static final String PATTERN = "[0-9]{13}";
	
	@Inject
	@Config
	private RegistrationRepository registrationRepository;	

	@ManagedProperty("#{msg.duplicateCnp}")
	private String duplicateCnpMessage;
	@ManagedProperty("#{msg.incorrectCnpFormat}")
	private String incorrectCnpFormat;
	@ManagedProperty("#{msg.incorrectCnp}")
	private String incorrectCnp;
	
	private String cnpClass = "";
	
	public static long getChecksum(long cnpWithoutLastDigit) {
		long sum = 0;
		long crcKey = CNP_CRC_KEY;
		while(cnpWithoutLastDigit > 0) {
			long cnpDigit = cnpWithoutLastDigit % COUNTING_BASE;
			long crcDigit = crcKey % COUNTING_BASE;
			cnpWithoutLastDigit /= COUNTING_BASE;
			crcKey /= COUNTING_BASE;
			sum += cnpDigit * crcDigit;
		}
		long modulo = sum % (COUNTING_BASE + 1);
		if (modulo == COUNTING_BASE) {
			modulo = 1;
		}
		return modulo;
	}
	
	public static boolean validateCnp(String cnp) {
		long cnpLong = Long.parseLong(cnp);
		return cnpLong % COUNTING_BASE == getChecksum (cnpLong / COUNTING_BASE);
	}

	public void cnpValidator(FacesContext context, UIComponent component, Object params) {
		cnpClass = "";
		RegexValidator emailValidator = new RegexValidator();
		emailValidator.setPattern(PATTERN);
		try {
			emailValidator.validate(context, component, params);
		}
		catch(ValidatorException ve) {
			cnpClass = "error";
			throw new ValidatorException(new FacesMessage(getIncorrectCnpFormat()));
		}
		
		if (!validateCnp(params.toString())) {
			cnpClass = "error";
			throw new ValidatorException(new FacesMessage(getIncorrectCnp()));
		}
		
		if (getRegistrationRepository().hasCnpRegistration(params.toString())) {
			cnpClass = "error";
			throw new ValidatorException(new FacesMessage(getDuplicateCnpMessage()));
		}
	}

	public String getDuplicateCnpMessage() {
		return duplicateCnpMessage;
	}

	public void setDuplicateCnpMessage(String duplicateCnpMessage) {
		this.duplicateCnpMessage = duplicateCnpMessage;
	}

	public String getIncorrectCnpFormat() {
		return incorrectCnpFormat;
	}

	public void setIncorrectCnpFormat(String incorrectCnpFormat) {
		this.incorrectCnpFormat = incorrectCnpFormat;
	}

	public RegistrationRepository getRegistrationRepository() {
		return registrationRepository;
	}

	public void setRegistrationRepository(RegistrationRepository registrationRepository) {
		this.registrationRepository = registrationRepository;
	}

	public String getIncorrectCnp() {
		return incorrectCnp;
	}

	public void setIncorrectCnp(String incorrectCnp) {
		this.incorrectCnp = incorrectCnp;
	}

	public String getCnpClass() {
		return cnpClass;
	}

	public void setCnpClass(String cnpClass) {
		this.cnpClass = cnpClass;
	}

}
