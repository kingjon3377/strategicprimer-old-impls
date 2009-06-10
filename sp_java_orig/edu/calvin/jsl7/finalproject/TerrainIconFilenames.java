/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.calvin.jsl7.finalproject;

import java.net.URL;

/**
 *
 * @author kingjon
 */
public class TerrainIconFilenames {
	/**
	 * An array of filenames.
	 */
	private final URL[] filenames;
	/**
	 * Create an array of filenames
	 * @param size The size we want the array to be
	 * @param prefix A string to put before the number
	 * @param suffix A string to put after the number
	 */
    public TerrainIconFilenames(final int size, final String prefix, final String suffix) {
        filenames = new URL[size];
        for (int index = 0; index < size;index++) {
            filenames[index] = getClass().getResource(prefix + (index < 9 ? '0' : "") + Integer.toString(index + 1) + suffix);
        }
    }
    /**
     * Get one of the filenames
     * @param index Which filename to get
     * @return The filename
     */
    public URL get(final int index) {
        return filenames[index];
    }
}
