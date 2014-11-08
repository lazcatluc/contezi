package ro.contezi.novote.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Voter.class)
public abstract class Voter_ {

	public static volatile SingularAttribute<Voter, String> country;
	public static volatile SingularAttribute<Voter, String> cnp;
	public static volatile SingularAttribute<Voter, String> city;
	public static volatile SingularAttribute<Voter, String> name;
	public static volatile SingularAttribute<Voter, Voter> pairedVoter;
	public static volatile SingularAttribute<Voter, String> email;

}

