package com.xsimo.crawler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Ensemble de fonctions pour faciliter la lecture de nombres et de caracteres
 * au clavier. Pour faire usage de cette classe, vous devez soit:
 * <ul>
 * <li>ajoutez le repertoire <tt>/u/dift1015/RessourcesJava</tt> dans votre
 * <tt>CLASSPATH</tt>;</li>
 * <li>ou copiez le fichier <tt>Keyboard.java</tt>
 * dans votre repertoire de travail.</li>
 * </ul>
 *
 * <p>Chaque fonction definie dans cette classe lit une entree par ligne,
 * <i>i.e.</i> si la fonction {@link #readInt() Keyboard.readInt()} est appelee,
 * un entier va etre lu dans la derniere ligne tapee au clavier, et seulement
 * cet entier doit se trouver sur cette ligne ou il y aura une erreur. Il en va
 * default meme pour la lecture d'un nombre reel et d'un caractere. Seul la
 * lecture d'une chaine de caracteres ({@link #readString()
 * Keyboard.readString()}) permet la lecture d'une ligne quelconque. Vous devrez
 * traiter cette ligne manuellement pour gerer des entrees plus
 * sophistiquees.</p>
 *
 * <p><b>Note:</b> Ceci n'est pas un programme. Vous ne pourrez pas l'executer
 * apres l'avoir compile.</p>
 *
 * @author felipe IFT1010, modifie par duranlef IFT1010
 */
public final class Keyboard
{
  /** Derniere ligne lue. */
  private static String derniereEntree;

  /** Le lecteur de System.in... */
  private static BufferedReader keyboard =
    new BufferedReader( new InputStreamReader( System.in ) );

  /** Indique s'il y a eu erreur de la derniere operation. */
  private static boolean erreur = false;

  /** Indique la fin de l'entree (plus rien a lire). */
  private static boolean finEntree = false;

  /** Constructeur "camoufle". */
  Keyboard() {}

  /**
   * Lit la prochaine ligne.
   * @return true si la lecture s'est bien deroulee.
   */
  private static boolean readLine()
  {
    try
    {
      erreur = false; // jusqu'ici tout est beau
      derniereEntree = keyboard.readLine();

      if( derniereEntree == null ) // fin de la lecture?
      {
        finEntree = true;
        derniereEntree = "";
      }
      return true;
    }
    catch( IOException e )
    {
      System.err.println( "Erreur de lecture clavier" );
      erreur = true; // il y a eu un probleme
    }
    return false;
  }

  // ----------------------------------------------------------
  // interface de la classe Keyboard
  // ----------------------------------------------------------

  /**
   * Retourne vrai s'il y a eu une erreur de lecture lors de la derniere
   * operation.
   */
  public static boolean error()
  {
    return erreur;
  }

  /**
   * Retourne vrai s'il n'y a plus rien a lire.
   */
  public static boolean isEof()
  {
    return finEntree;
  }

  /**
   * Lecture d'un entier.
   * @return L'entier lu s'il y a bel et bien un entier a lire. Sinon, un zero
   * est retourne et le prochain appel a {@link #error() error()} retournera
   * <tt>false</tt>.
   */
  public static int readInt()
  {
    if( readLine() ) // lecture de la prochaine ligne
    {
      try
      {
        return Integer.parseInt( derniereEntree );
      }
      catch( NumberFormatException e )
      {
        // Affiche un message d'erreur pour avertir de l'erreur
        //System.err.print( "La chaine saisie n'est pas un entier. " );
        //System.err.println( "Je retourne 0 a la place." );
        erreur = true; // mauvais format
      }
    }
    return 0;
  }

  /**
   * Lecture d'un nombre reel.
   * @return Le nombre lu s'il y a bel et bien un nombre a lire. Sinon, un zero
   * est retourne et le prochain appel a {@link #error() error()} retournera
   * <tt>false</tt>.
   */
  public static double readDouble()
  {
    if( readLine() ) // lecture de la prochaine ligne
    {
      try
      {
        return Double.parseDouble( derniereEntree );
      }
      catch( NumberFormatException e )
      {
        // Affiche un message d'erreur pour avertir de l'erreur
        //System.err.println( "La chaine saisie n'est pas un nombre decimal" );
        //System.err.println( "Je retourne 0. a la place" );
        erreur = true; // mauvais format
      }
    }
    return 0.0;
  }

  /**
   * Lit un caractere.
   * @return Le caractere lu s'il y a bel et bien un caractere a lire et s'il
   * est seul. Sinon, le caractere zero est retourne et le prochain appel a
   * {@link #error() error()} retournera <tt>false</tt>.
   */
  public static char readChar()
  {
    if( readLine() ) // lecture de la prochaine ligne
    {
      if( derniereEntree.length() == 1 )
      {
        // retourne l'unique caractere de la chaine
        return derniereEntree.charAt( 0 );
      }
      else
      {


        erreur = true; // mauvais format
      }
    }
    return (char)0;
  }

  /**
   * Lecture d'une chaine de caracteres.
   * @return La chaine lu s'il n'y a pas eu d'erreur de lecture. Sinon, la
   * chaine vide est retournee et le prochain appel a {@link #error() error()}
   * retournera <tt>false</tt>.
   */
  public static String readString()
  {
    if( readLine() ) // lecture de la prochaine ligne
      return derniereEntree;
    return "";
  }

}
