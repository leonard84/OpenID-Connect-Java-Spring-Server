/*******************************************************************************
 * Copyright 2013 The MITRE Corporation 
 *   and the MIT Kerberos and Internet Trust Consortium
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/**
 * 
 */
package org.mitre.jose.keystore;

import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;

/**
 * @author jricher
 *
 */
public class JWKSetKeyStore implements InitializingBean {

	private JWKSet jwkSet;

	private Resource location;

	public JWKSetKeyStore() {

	}

	public JWKSetKeyStore(JWKSet jwkSet) {
		this.jwkSet = jwkSet;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

		if (jwkSet == null) {
			if (location != null) {

				if (location.exists() && location.isReadable()) {

					// read in the file from disk
					String s = CharStreams.toString(new InputStreamReader(location.getInputStream(), Charsets.UTF_8));

					// parse it into a jwkSet object
					jwkSet = JWKSet.parse(s);

				} else {
					throw new IllegalArgumentException("Key Set resource could not be read: " + location);
				}

			} else {
				throw new IllegalArgumentException("Key store must be initialized with at least one of a jwkSet or a location.");
			}
		}
	}

	/**
	 * @return the jwkSet
	 */
	public JWKSet getJwkSet() {
		return jwkSet;
	}

	/**
	 * @param jwkSet the jwkSet to set
	 */
	public void setJwkSet(JWKSet jwkSet) {
		this.jwkSet = jwkSet;
	}

	/**
	 * @return the location
	 */
	public Resource getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Resource location) {
		this.location = location;
	}

	/**
	 * Get the list of keys in this keystore. This is a passthrough to the underlying JWK Set
	 */
	public List<JWK> getKeys() {
		return jwkSet.getKeys();
	}



}
