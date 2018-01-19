package com.debby.mybatis.mapping;

import java.io.Serializable;

import com.debby.mybatis.annotation.MappingCompositeId;

public class EntityWithMappingCompositeIdAndNoMappingIdIn {

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

		private int id;

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

	}

}
