package pu.services;

import java.beans.PropertyChangeListener;

/**
 * Kenmerkt de bron van propertyChange-events, namelijk dat add/remove PropertyChangeListener kunt doen.
 */
public interface PropertyChangeSource
{
/**
 * Voeg een PropertyChange-luisteraar toe
 * @param e java.beans.PropertyChangeListener
 */
public void addPropertyChangeListener( PropertyChangeListener p );
/**
 * Verwijder een PropertyChange-luisteraar
 * @param e java.beans.PropertyChangeListener
 */
public void removePropertyChangeListener( PropertyChangeListener p );
}
