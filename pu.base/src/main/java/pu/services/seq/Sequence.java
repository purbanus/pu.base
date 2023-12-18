/*
 * Created on 21-mrt-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.services.seq;

/**
 * Generates series of unique numbers
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface Sequence
{
// HIGH Waarom heb je in vredesnaam een hasNext?
//       - Een antwoord is dat je in Iris2 vaak een blok nummers wilt 'pakken' en je wilt dat dat precies
//         groot genoeg is. Je gooit dan een fout als het blok op is en je toch nog een volgende probeert
//         te halen. Maar dat is een debug methode en daar zijn andere oplossingen voor, veel betere.
//         Bijvoorbeeld een CheckedRangeSequence
public abstract boolean hasNext();
public abstract int next();
}
