package com.debby.mybatis.mapping;

import java.io.Serializable;

import com.debby.mybatis.annotation.MappingCompositeId;
import com.debby.mybatis.annotation.MappingId;

public class EntityWithMappingCompositeIdAndMultipleGeneratedMappingId {

	@MappingCompositeId
	private CompositeId compositeId;

	/**
	 * @return the compositeId
	 */
	public CompositeId getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId
	 *            the compositeId to set
	 */
	public void setCompositeId(CompositeId compositeId) {
		this.compositeId = compositeId;
	}

	class CompositeId implements Serializable {

		@MappingId
		private int id;
		@MappingId
		private String name;

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		
	}

}
