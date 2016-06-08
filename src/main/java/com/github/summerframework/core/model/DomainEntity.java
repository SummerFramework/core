package com.github.summerframework.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Defines the domain entities base class, defining his behaviors and properties.
 * 
 * @author Renan Barbioni
 */
@SuppressWarnings ( "serial" )
@MappedSuperclass
public abstract class DomainEntity implements Serializable {

	public static final String UUID = "uuid";
	
	public static final String ACTIVE = "active";
	
	/**
	 * ID do objeto no banco de dados.
	 */
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	@JsonIgnore
	public Long id;

    @Column(name = "uuid", nullable = false, updatable = false)
    private String uuid = java.util.UUID.randomUUID().toString();
	
	/**
	 * Data e hora da criação deste conteúdo no sistema.
	 */
	@Column ( name = "creation_date", nullable = false )
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm")
	public Date creationDate = new Date();
	
	@Column ( name = "active", nullable=false )
	public boolean active = true;

	/**
	 * Return the database ID of this domain entity.
	 * 
	 * @return Database ID of this domain entity.
	 */
	public Long getId () {
		return this.id;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public String getUUID() {
		return uuid;
	}

	public void generateUUID (){
		this.uuid = java.util.UUID.randomUUID().toString();
	}

	protected void setUUID ( String uuid ){
		this.uuid = uuid;
	}

	@JsonIgnore
	public abstract Class getVOType();

	public <VO> VO toVO() throws IllegalAccessException, InstantiationException {
		VO vo = (VO) getVOType().newInstance();
		BeanUtils.copyProperties(this, vo);
		return vo;
	}

	public void validate (){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		final Set<ConstraintViolation<DomainEntity>> constraintViolations = validator.validate(this);
		if ( constraintViolations.size() > 0){
			for (ConstraintViolation<DomainEntity> violation : constraintViolations) {
				System.out.println("violation = " + violation);
			}
		}

	}

	@Override
	public boolean equals(Object obj) {

		if ( obj == null ){
			return false;
		}

		if ( obj instanceof DomainEntity == false ){
			return false;
		}

		DomainEntity object = (DomainEntity) obj;

		return this.uuid.equals( object.uuid );
	}
}