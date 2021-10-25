Feature: Test creazione ordine
  
  Scenario Outline: acquisto ordine
    Given broswer aperto
   		And pagina di home caricata
   		##And release la release è 
    When seleziono offerta
    Then pagina di offerta caricata
    When seleziono configuro offerta 
    Then carico pagina copertura
    When edito coperturta comune:<comune> strada:<strada> civico:<civico>
    Then carico pagina configurazione 
     And verifico prezzo base
    When configura addon Intrattenimento:<Intrattenimento> Studio:<Studio> Security:<Security>
    Then configura device
    When configurazione telefonica
    When configurazione installazione
    When configurazione ripetitore
    
    Examples: 
    | comune | strada | civico | Intrattenimento | Studio | Security |
    | BUSTO ARSIZIO | VIA GRAN SAN BERNARDO | 33 | Si | Si | Si |
    | DESIO | VIA GIUSEPPE GARIBALDI | 15 | No | No | No |