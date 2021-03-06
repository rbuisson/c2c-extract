package net.mekomsolutions.c2c.migration.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.mekomsolutions.c2c.migration.entity.sync.SyncEntity;

/**
 * Wraps a {@link net.mekomsolutions.c2c.migration.entity.sync.SyncEntity SyncEntity} 
 * to add metadata to it, such as {@link EntityWrapper#modelClass "modelClass"} for instance, that is
 * required to facilitate later marshaling of the entity it holds.
 * 
 * Also provides the JSON field mapping to be used by the {@link com.fasterxml.jackson Jackson} library
 * via the {@link com.fasterxml.jackson.annotation.JsonIgnore @JsonIgnore} annotation for the same purpose.
 */
public class EntityWrapper<T extends SyncEntity> implements Serializable {

	private static final long serialVersionUID = -6161174494661919383L;

	@JsonProperty("tableToSyncModelClass")
	private String modelClass;

	@JsonIgnore
	private String uuid;

	@JsonProperty("model")
	private T entity;

	public EntityWrapper(String modelClass, String uuid, T entity) {
		super();
		this.modelClass = modelClass;
		this.uuid = uuid;
		this.entity = entity;
	}
	
	/**
	 * Convenient constructor used when the wrapper fields can be guessed
	 * from the entity wrapped itself.
	 * 
	 * @param entity
	 */
	public EntityWrapper(T entity) {
		this.entity = entity;
		this.modelClass = entity.getModelClassName();
		this.uuid = entity.getUuid();
	}

	public String getModelClass() {
		return modelClass;
	}

	public String getUuid() {
		return uuid;
	}

	public T getEntity() {
		return entity;
	}

}
