package net.mithra.familly.model;

import java.io.Serializable;

public class TreatedObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8498415506143648584L;

	private String idObject;
	
	private Object objectConvert;
	
	private String LastResultAction;
	
	private String outputName;
	
	private ObjectType objectType;
	
	public TreatedObject() {}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((LastResultAction == null) ? 0 : LastResultAction.hashCode());
		result = prime * result + ((idObject == null) ? 0 : idObject.hashCode());
		result = prime * result + ((objectConvert == null) ? 0 : objectConvert.hashCode());
		result = prime * result + ((outputName == null) ? 0 : outputName.hashCode());
		result = prime * result + ((objectType == null) ? 0 : objectType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreatedObject other = (TreatedObject) obj;
		if (LastResultAction == null) {
			if (other.LastResultAction != null)
				return false;
		} else if (!LastResultAction.equals(other.LastResultAction))
			return false;
		if (idObject == null) {
			if (other.idObject != null)
				return false;
		} else if (!idObject.equals(other.idObject))
			return false;
		if (objectConvert == null) {
			if (other.objectConvert != null)
				return false;
		} else if (!objectConvert.equals(other.objectConvert))
			return false;
		if (outputName == null) {
			if (other.outputName != null)
				return false;
		} else if (!outputName.equals(other.outputName))
			return false;
		if (objectType == null) {
			if (other.objectType != null)
				return false;
		} else if (!objectType.equals(other.objectType))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TreatedObject [idObject=" + idObject + ", objectConvert=" + objectConvert + ", LastResultAction="
				+ LastResultAction + ", outputName=" + outputName + "]";
	}

	/**
	 * @return the idObject
	 */
	public String getIdObject() {
		return idObject;
	}

	/**
	 * @param idObject the idObject to set
	 */
	public void setIdObject(String idObject) {
		this.idObject = idObject;
	}

	/**
	 * @return the objectConvert
	 */
	public Object getObjectConvert() {
		return objectConvert;
	}

	/**
	 * @param objectConvert the objectConvert to set
	 */
	public void setObjectConvert(Object objectConvert) {
		this.objectConvert = objectConvert;
	}

	/**
	 * @return the lastResultAction
	 */
	public String getLastResultAction() {
		return LastResultAction;
	}

	/**
	 * @param lastResultAction the lastResultAction to set
	 */
	public void setLastResultAction(String lastResultAction) {
		LastResultAction = lastResultAction;
	}

	/**
	 * @return the outputName
	 */
	public String getOutputName() {
		return outputName;
	}

	/**
	 * @param outputName the outputName to set
	 */
	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public ObjectType getObjectType() {
		return objectType;
	}

	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

	public void setObjectType(String objectType) {
		switch(objectType){
		case "xml":this.objectType = ObjectType.XML;break;
		case "XML":this.objectType = ObjectType.XML;break;
		case "csv":this.objectType = ObjectType.CSV;break;
		case "CSV":this.objectType = ObjectType.CSV;break;
		case "SGM":this.objectType = ObjectType.SGML;break;
		case "sgm":this.objectType = ObjectType.SGML;break;
		case "dmrl":this.objectType = ObjectType.DMRL;break;
		case "DMRL":this.objectType = ObjectType.DMRL;break;
		case "dml":this.objectType = ObjectType.DMRL;break;
		case "DML":this.objectType = ObjectType.DMRL;break;
		default:this.objectType = ObjectType.XML;break;
		}
	}
	
}
