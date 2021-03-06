package net.mekomsolutions.c2c.migration.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.mekomsolutions.c2c.migration.entity.sync.SyncEntity;

/**
 * Describes a Diagnosis, which is merely a {@link java.util.List list}
 * of {@link net.mekomsolutions.c2c.migration.entity.EntityWrapper wrapped entities}
 * of type {@link net.mekomsolutions.c2c.migration.entity.sync.SyncEntity SyncEntity}.
 */
public class Diagnosis {

	@JsonProperty
	private List<EntityWrapper<SyncEntity>> entities;

	public Diagnosis(List<SyncEntity> allEntities) {

		List<EntityWrapper<SyncEntity>> asWrappedEntities = new ArrayList<>() ;

		for (SyncEntity entity: allEntities) {
			asWrappedEntities.add(new EntityWrapper<>(entity));
		}

		this.entities = asWrappedEntities;
	}

	public List<EntityWrapper<SyncEntity>> getEntities() {
		return entities;
	}

}
