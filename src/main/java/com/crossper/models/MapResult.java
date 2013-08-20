/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.models;
import com.google.api.client.util.Key;
import java.util.List;

public class MapResult {
    @Key
	public String status;

	@Key
	public List<MapDetails> results;
}
