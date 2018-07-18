package org.engim.tss2018;
 //si fa copia incolla di ste cose, tutto il file, spdataprovider
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.engim.tss2018.db.ChiavePrimaria;

//implementa l interfaccia sortabledataprovider, inventaa da sviluppatori, 
public class SPDataProvider<T extends ChiavePrimaria> //extends vuol dire, come riga 71, così pongo una condizione e aggiungo, un javabeans chiave primaria,  
        extends SortableDataProvider<T, String>
{
  private Class<T> classe;
  
  public SPDataProvider(Class<T> classe)
  {
    this.classe = classe;
  }
  //dobbiamo implementare noi il metodo iterator, size e model
  //cursore in una tabella, numerino che dice a che punto siamo arivati a leggere nei risultati, variabile nel database ch dice, l utente ha letto il primo risultato, poi passo al secondo, il cursore si incrementa
  //iterator, è una classe java standard che fa da cursore, wicket ci chiede un cursore tramite itarator, minuscolo, ci passa start e num, es dal 71 a 80, start 71 num10, 
  @Override //fornitore di dati che funzina quando ho le chiavi primaria di tipo intero
  public Iterator<? extends T> //iterator di un oggetto che nn conosco <? che come minimo extend T
        iterator(long start, long num) //utilizza long nn so quanto sono grandi le tabelle, 
  {
    EntityManager db = PM.db(); //chiedo connessione al database
    try
    {
      Query q = //classe può essere, fermata, corso persona....
       db.createNamedQuery(classe.getSimpleName() + 
                           ".findAll"); //cerca la query findall
      q.setFirstResult((int)start); //conversione in intero(long in int), start e num, sono un limit, es 71, 10
      q.setMaxResults((int)num);
      List<T> result = q.getResultList(); //interfaccia(classe) List, è una classe base di arraylist, ci ritorna una list  
      return result.iterator(); //ritorno il cursore sulla lista di risultati
    }
    finally
    {
      db.close();
    }
  }
//per generare la tabella devo sapere quanti record ci sono da mosrare, il size fa questo,
        
  @Override  //qui faccio il SELECT COUNT * FROM scritto in java, sono tutte classi standard di java JPA, in realtà(ibernate)
  public long size()
  {
    EntityManager db = PM.db();
    try
    { //costruisce dinamicamente le query(criteriabuilder), restituirà un numero di tipo long
      CriteriaBuilder cb = db.getCriteriaBuilder();
      CriteriaQuery<Long> cq = 
        cb.createQuery(Long.class); //devo passargli la classe
      Root<T> root = cq.from(classe);//radice dell query, tabella da cui seleziono, dimmi quale è la tabella da cui fai la select
      cq.select(cb.count(root)); //root è la nostra tabella
      return db.createQuery(cq).getSingleResult(); //ritorna un numero, ritorna nella query il singolo risultato della query
    }
    finally
    {//restituisce la connessione al database
      db.close();
    }
  }
   //dato il cursore del database, per ogni risultato devo chiamare la model
  @Override //è la scatola, per ogni risultato, ottenuto con l iterator(cursore), creo la scatolina che contiene questo oggetto
  public IModel<T> model(T object) //object è della classe T, nella modelc viene passato un oggetto di classe, es corso object, linne, fermata, il t è una generica classe che extends objects
  { //alla scatolina spldmodel, 
    return new SPLDModel<T>(object.getId(), classe); //getid nn cè in in extends object, per aggiungerlo,
  }// modificato in Corso riga 47, e persona, chiave primaria(è un interfaccia che dice che esiste il metodo get id, restituisce un intero)
//object.getid, lo posso chiamare perchè ho fatto tutuo sto casino,, extends... ecc
//quando wicket deve mostrare i campi chama la spldmodel
}

//quando scrivo...extends o implements nei file
//extends per le classi
//implements per le interfacce
//sono la stessa cosa alla fine..