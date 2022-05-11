# Peer-Review 2: Protocollo di Comunicazione

Michela Gregorini, Jonathan Fiore, Renato Gallicola

Gruppo 33

Valutazione della documentazione del protocollo di comunicazione del gruppo 32.


## Lati positivi

Il protocollo risulta dettagliato e completo: i messaggi vengono spiegati in modo chiaro, rendendo leggibili i diagrammi.
Le soluzioni adottate dal gruppo risultano perlopiù efficienti e facili da implementare.

Un aspetto positivo da evidenziare è presente nel diagramma "2.5 Move Phase, Move1", in particolare nella gestione del movimento degli studenti. Infatti, quest'ultima permette di rimuovere dall'ingresso della plancia uno studente alla volta, semplificando così i controlli di validità di tale mossa. Inoltre, indipendentemente dalla destinazione dello spostamento, questa scelta viene gestita tramite un unico messaggio, in modo da evitare che si utilizzino due messaggi differenti.

Riteniamo inoltre interessante l'impiego di un "Communication Message", un generico messaggio che viene inviato dal server al client per comunicare informazioni nel corso di diverse situazioni di gioco.                                                             


## Lati negativi

Un primo lato negativo riscontrato durante la review è relativo al diagramma "2.2 New game".
Infatti, in esso è presente un box-case con etichetta "All players joined" che illustra una situazione che non si può verificare secondo la struttura della fase di creazione di una nuova partita proposta dal gruppo.
La situazione "New game" è relativa solo al primo giocatore che si unisce e si conclude una volta che quest'ultimo ha terminato le scelte riguardanti la modalità della partita e il numero di giocatori di cui è composta.
Il messaggio "MatchStarted" viene inviato esclusivamente nel caso in cui la partita risulti piena, ma ciò si può verificare solo quando almeno un giocatore (oltre al primo) si è unito e dunque la fase "New game" è già conclusa.

Secondo il diagramma "2.5 Move Phase, Move1", nel momento in cui il giocatore di turno deve scegliere quali studenti spostare dal suo ingresso, il server consente di effettuare un numero di scelte pari a tre, mentre il numero di studenti da spostare può variare in base al numero di giocatori.
Infatti, nel caso in cui una partita sia giocata da tre giocatori, ad ogni turno devono essere rimossi dall'ingresso esattamente quattro studenti; nel caso invece di due o quattro giocatori, è corretto consentire lo spostamento di tre studenti.

Un ultimo errore riscontrato durante l'analisi del protocollo del gruppo 32 è relativo al diagramma "Move Phase, Move2". A seguito dello spostamento di Madre Natura, il server comunica ai client lo stato aggiornato del tavolo di gioco, il quale presenta come unica modifica la nuova posizione di Madre Natura. Tale aggiornamento non prende in considerazione ciò che accade a seguito del calcolo dell'influenza sull'isola sulla quale si è arrestata Madre Natura, poiché tale calcolo viene effettuato solo dopo l'invio del messaggio di aggiornamento in questione. Terminato ciò, il server invia un ulteriore aggiornamento del tavolo di gioco, completo di tutte le eventuali modifiche subite dalle isole a seguito del calcolo dell'influenza. Riteniamo dunque opportuno che, durante questa fase, i giocatori siano notificati un'unica volta circa lo stato delle isole, esclusivamente al termine di tutti i controlli relativi all'influenza. 


## Confronto

Nonostante i due protocolli risultino simili sia nella formulazione dei messaggi, sia nell'identificazione e realizzazione degli scenari, un confronto da effettuare riguarda il criterio secondo cui vengono scelti i colori delle torri ed i maghi.
Il gruppo 32 ha deciso di effettuare tali scelte solo dopo che tutti i giocatori si sono uniti alla partita, permettendo a ciascuno di loro di scegliere la propria preferenza a turno secondo l'ordine di adesione, garantendo che prima vengano selezionate le torri da tutti giocatori e poi i maghi. Il sistema da noi adottato consente di effettuare tali scelte durante la fase di unione ad una partita, la quale si conclude dopo che il giocatore che ha fatto richiesta di partecipare ha selezionato sia il colore della torre che il mago.