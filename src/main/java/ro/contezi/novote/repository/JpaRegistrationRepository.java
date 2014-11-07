package ro.contezi.novote.repository;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import ro.contezi.novote.config.Config;
import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.Voter;

@Stateless
@Config
public class JpaRegistrationRepository extends AbstractRegistrationRepository implements RegistrationRepository, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	protected void persistRegistration(Registration registration) {
		try {
			getEntityManager().persist(registration.getUser());
			getEntityManager().persist(registration);
		}
		catch (PersistenceException pe) {
			throw new MultipleRegistrationException(pe);
		}
	}

	@Override
	public boolean hasEmailRegistration(String email) {
		return getEntityManager().find(Voter.class, email) != null;
	}

	@Override
	public boolean hasCnpRegistration(String cnp) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Voter> voterQuery = builder.createQuery(Voter.class);
		Root<Voter> voterRoot = voterQuery.from(Voter.class);
		voterQuery.select(voterRoot);
		voterQuery.where(builder.equal(voterRoot.get("cnp"),cnp));
		return !getEntityManager().createQuery(voterQuery).getResultList().isEmpty();
	}

	@Override
	public Registration findPairableRegistration(Registration registration) {
		Voter voter = registration.getUser();
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Registration> query = builder.createQuery(Registration.class);
		Root<Registration> registr = query.from(Registration.class);
		Join<Registration, Voter> joinedVoter = registr.join("user");
		query.select(registr);
		query.where(
				builder.isNotNull(registr.get("candidate")),
				builder.notEqual(registr.get("candidate"), registration.getCandidate()),
				builder.isNull(joinedVoter.get("pairedVoter")),
				builder.equal(joinedVoter.get("city"), voter.getCity()),
				builder.equal(joinedVoter.get("country"), voter.getCountry()));
		List<Registration> registrations = getEntityManager().createQuery(query).setMaxResults(1).getResultList();
		if (registrations.isEmpty()) {
			return Registration.SOMETHING;
		}
		return registrations.get(0);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
