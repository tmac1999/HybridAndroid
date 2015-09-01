package com.example.chinamobile.bean;

public class StandardUser {
	public String id;
	public String label;
	public boolean checked;
	/*
	 * 
	 * 
	 * {
               item:{
                    id: 'id31',
                    label: 'label31',
                    checked: true
                } 
            }
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "item : {id:'" + id + "', label:'" + label + "', checked:"
				+ checked + "}";
		
	}
	
}
