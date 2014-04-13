Mp3Organizer
============

Piccolo programma in java che ordina gli mp3 gerarchicamente in cartelle a seconda degli id3tag.

Richiede openjdk-7, bash.

Cambiare il path all'interno del file bash "organize" con il path della cartella contenente la musica.
A questo punto lanciare il file organize. Tutti gli mp3 verranno organizzati in cartelle: 

	Autore->Album->Artista (Album) titolo_canzone

Gli mp3 che per qualche motivo fallisce a leggere vengono spostati dentro la cartella "spostati" all'interno
della directory scelta nel file bash.

Il codice è scritto di getto e andrebbe aggiunta qualche funzione. Lo utiliizzo ormai da anni e fa quello che
deve abbastanza bene.

