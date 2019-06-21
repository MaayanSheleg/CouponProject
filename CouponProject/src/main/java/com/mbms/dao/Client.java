package com.mbms.dao;

import com.mbms.enums.ClientType;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
 */
public interface Client {

	ClientType clientType = null;

	public Client login(String user,String password, ClientType clientType);

}