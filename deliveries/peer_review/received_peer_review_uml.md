# Peer-Review 1: UML

[Michela Gregorini], [Jonathan Fiore], [Renato Gallicola]

Gruppo [33]

Valutazione del diagramma UML delle classi del gruppo [32].

## Lati positivi

Analizzando il diagramma realizzato dal gruppo 32, abbiamo riscontrato una buona organizzazione generale del progetto; effettuando una simulazione di partita, appare evidente, infatti, che tutte le classi necessarie all'esecuzione del gioco siano state identificate correttamente. Ciascuna di esse, inoltre, salvo rare eccezioni, presenta gli attributi e i metodi fondamentali allo svolgimento dei vari turni.

Un ulteriore elemento qualificante è rappresentato dal modo in cui il gruppo ha deciso di gestire lo strumento "Professor": realizzando una classe apposita, dotata dell'attributo "owner", è infatti possibile risalire al giocatore che in un determinato momento della partita è in possesso di uno specifico professore. In questo modo si raggiungono due obiettivi: si rende più comodo l'accesso a questa variabile e si semplifica notevolmente il calcolo dell'influenza su un'isola, o gruppo di isole.

Da ultimo, sembra apprezzabile la scelta di adottare il pattern strategy per la gestione dei vari "Character" e del loro relativo effetto, nonostante il suddetto design pattern presenti, nel suo impiego, alcune problematiche che verranno approfondite nel paragrafo seguente.


## Lati negativi

Sebbene l'utilizzo del pattern strategy nella sua versione "standard", allo scopo di differenziare gli effetti unici dei personaggi, rappresenti una buona intuizione, esso comporta delle difficoltà. Non si riesce, infatti, poiché non consente di passare in ingresso ai metodi "effect" dei diversi "Character" un set di parametri personalizzato in funzione del tipo di personaggio che si vuole utilizzare. 
Inoltre, non è chiaro come sia possibile giocare uno specifico personaggio e tenere traccia della carta attiva durante un turno; sarebbero utili, rispettivamente, un metodo e un attributo che svolgessero tali ruoli.

Un elemento di dubbio è rappresentato dalla gestione delle isole: osservando il diagramma, non è chiaro come il gruppo abbia deciso di gestire l’unione delle isole, con il conseguente calcolo dell’influenza e del comportamento di Madre Natura. Sebbene sia presente il metodo "addIsland", non vi è alcun attributo che registri quali isole siano state aggiunte ad un'altra e dunque non si comprende come si riesca a stabilire quali isole risultino essere aggregate a formare un "arcipelago". Pertanto suggeriamo di aggiungere nella classe "Island" un attributo (ad esempio di tipo "ArrayList<Island>") che conservi le isole passate come parametro al metodo "addIsland".

Un piccolo neo, comunque da segnalare, riguarda la variante "Partita in 4 giocatori": pur essendo stato considerato un numero massimo di giocatori pari a 4, non si ritrova alcun modo per risalire a quale squadra appartenga uno specifico utente. A tal proposito, sarebbe utile aggiungere un attributo (ad esempio all'interno della classe "Game") oppure creare una classe apposita. 

A questo punto, ci sembra opportuno suggerire alcune modifiche ad elementi che, pur non rappresentando dei veri e propri errori, potevano essere gestiti in maniera diversa.
È il caso della classe "Student", la quale, presentando "color" come unico attributo, non risulta necessaria e dunque potrebbe essere interamente sostituita con la classe "Color".
Un altro caso è rappresentato dalla gestione degli studenti presenti nella Sala; nello specifico, suggeriamo di inserire, al posto delle cinque variabili che identificano i colori degli studenti, una Map che permetta di accedere al numero di un determinato tipo di studenti in funzione del loro colore.


## Confronto tra le architetture

Confrontando il diagramma realizzato dal nostro gruppo con quello del gruppo 32, la prima differenza sostanziale che emerge è la scelta di distribuire gli attributi e i metodi della nostra classe "GameModel" in due classi, da loro indicate con "Game" e "DashBoard".
Riteniamo che la soluzione da loro adottata renda più evidente la distinzione degli elementi propri della partita (come l'elenco dei giocatori e i criteri di turnazione) da quelli relativi agli elementi presenti "fisicamente" sul tavolo di gioco (le isole, le nuvole e le carte personaggio).

Come già affermato nel paragrafo relativo ai lati positivi, riteniamo vincente la soluzione adottata per i professori, più efficace della nostra che prevede, all’interno della classe "Board" (equivalente alla "SchoolBoard" del gruppo 32), una mappa di booleani per segnalare la presenza o meno di uno specifico professore sulla plancia del giocatore.