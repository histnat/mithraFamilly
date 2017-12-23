package net.mithra.familly.model;

public enum ObjectType {

		XML("xml"), 
		SGML("sgm"), 
		CSV("csv"),
		DMRL("dmrl");
		
		private ObjectType(String typeValue) {
			this.typeValue = typeValue;
		}
		
		
		private String typeValue;


		public String getTypeValue() {
			return typeValue;
		}


		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}

		


}
