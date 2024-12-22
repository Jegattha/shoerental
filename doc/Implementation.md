# Beschreibung des Frontends mit Screenshots der fertigen Applikation
1. BIld: Wenn man die Website https://shoerental.azurewebsites.net/ besucht, gelangt man zur Start-seite. Hier sind zunächst keine Schuhe sichtbar, sondern lediglich eine Beschreibung.
Im dargestellten Ausschnitt ist die Startseite zu sehen. Es gibt drei markierte Punkte, auf die ich genauer eingehen werde:

    1.	Eine About Page:
    Die About Page wurde erstellt, um Informationen über shoerental zu präsentieren. Zusätzlich können Benutzer eine Anfrage per Mail stellen, in dem sie dieses Formular ausfüllen. Unten liks gibt es noch Kontaktinfos, wie Email und Telefonnummer.

    2.	Verlinkung durch Button:
    Bei einem Klick auf dem «Anmelden» Button wird der Benutzer zur Signup-Seite weitergeleitet. Das sieht dann wie folgt aus. (2. BIld)

2. BIld: Hier gibt der Benutzer seine Daten ein und hat die Möglichkeit, die Benutzerrolle bei der Anmeldung auszuwählen. Die gewählte Rolle wird automatisch in Auth0 hinterlegt. 

3. BIld: Für dieses Beispiel habe ich die Rolle «Mieter» gewählt. Im Auth0-Dashboard ist ersichtlich, dass die Rolle automatisch zugewiesen wurde.

4. BIld: Falls man bereits über ein Login verfügt, kann man auf das Login-Icon klicken und sich dann 
anmelden.

5. BIld: Neben der Welcome-Seite existiert auch eine Schuheseite, auf der nicht authentifzierte Be-nutzer alle Schuhe mit Marke, Beschreibung und Bild sehen können. Zusätzlich ist ein «Mie-ten» Button vorhanden. Wenn der Benutzer auf den «Mieten» Button klickt, wird zur SignUp Seite weitergeleitet.  

# Ansicht als Mieter

6. BIld: Soblad sich ein Benutzer erfolgreich angemeldet oder registriert hat, wird er zur Startseite 
weitergeleitet und sieht zusätzliche Navigationsmöglichkeiten in der Navigationsleiste. Im Fol-genden gehen wir schrittweise auf jede dieser Optionen ein.

7. BIld: Auf der Damenschuheseite hat der Mieter Zugriff auf alle verfügbaren Damenschuhe. In die-sem Screenshot ist ersichtlich, dass dem Mieter die Option geboten wird, die Damenschuhe nach Verfügbarkeit, Grösse, Preis und Marken zu filtern. Es ist wichtig zu beachten, dass der Mieter keine bereits vermieteten Schuhe ausleihen kann. Dort wird deutlich, dass der Status auf Vermietet angezeigt wird, so wie ein roter Kreissymbol sowie auch eine Span-Information angezeigt wird, wie lange es vermietet ist. Zusätzlich gibt es auch die Datumsfelder nicht. Da-her lohnt sich der Filter nach Verfügbarkeit anzuwenden, um nicht die vermieteten Damen-schuhe angezeigt zu bekommen.

8. BIld: Wenn der Mieter seine Mietdauer angibt (Achtung: Das Datum darf nicht in der Vergangenheit 
liegen, andernfalls wird eine Fehlermeldung angezeigt, wie im nächsten Screenshot gezeigt wird). In diesem Fall werden auch die Anzahl der Tage sowie die Gesamtkosten für die Miet-dauer berechnet. Wenn diese Informationen dem Mieter zusagen, klickt er auf «Mieten».

9. BIld: Hier ein Beispiel, falls das Bis Datum in der Vergangenheit liegt: Hier wurde beim Bis Datum der 06.12.2024 gewählt aber wird nicht angezeigt.

10. BIld: Man kann es derzeit noch umgehen und trotzdem ein vergangenes Datum eingeben. In die-sem Fall würde jedoch der «Mieten» Button nicht funktionieren und es würde eine entspre-chende Fehlermeldung angezeigt werden.

11. BIld: Nachdem die Daten korrekt eingegeben wurden, ändert sich der Status von VERFÜGBAR zu 
VERMIETET. Zusätzlich werden E-Mails versendet.

12. BIld: Auf der Seite für Herrenschuhe sind das Vorgehen und die Logik genau gleich wie auf der Damenschuhe Seite.

13. BIld: Wenn der Mieter auf den Tab «Account» klickt, sieht er seine Informationen wie Name, E-Mail und die Rolle. Neben diesen Informationen hat der Mieter auch stets den Überblick darüber, welche Schuhe er aktuell gemietet hat und für welchen Zeitraum.

# Ansicht als Vermieter
14. BIld: Im Tab «Schuhe» kann der Vermieter Schuhe hinzufügen. Dafür muss er die Felder Marke, Typ, Tagessatz in Franken, Grösse, Schuhfarbe und Schuhbeschreibung ausfüllen. Im Feld «Marke» kann er einen beliebigen Text wie «Prada» eingeben. Bei «Typ» kann zwischen Da-men- oder Hennerschuh ausgewählt werden. Der Tagessatz kann auch Dezimalzahlen enthal-ten. Bei der Grösse gibt es die Auswahl zwischen 16 – 50. Die Schuhfarben sind vorgegeben und beinhalten aktuell die Farben Blau, Grün, Schwarz, Weiss, Grau, Braun und Rot. In der Schuhbeschreibung kann er ebenfalls einen beliebigen Text eingeben. Nachdem er alle Felder ausgefüllt hat, kann er auf «Abenden» klicken. Alle Schuhe werden mit einem Bild erstellt. Die Bilder werden durch die Logik von Key-Value Paaren generiert, dass heisst, wenn der Typ «Damenschuh oder Herrenschuh» und die Farbe «Blau oder eine andere Farbe gewählt wird» ist, wird immer ein Bild des Typs mit dem jeweiligen Typ angezeigt. Der Vermie-ter kann auch Schuhe löschen, die er nicht mehr 
vermieten möchte. Ausserdem hat er die Möglichkeit, Schuhe zu aktualisieren. Er betätigt den «Update»-Knopf und sieht dann die Werte im oberen Formularfeld, wo er die Daten erfassen kann. Dort kann er seine Aktualisierungen vornehmen. Sobald er die Aktualisierungen vorge-nommen hat, muss er auf den grünen Knopf «Update Speichern» klicken. Es gibt auch einen eingebauten Filter, mit dem nach Damen- oder Herrenschuhe gefiltert werden kann. Auf der nächsten Seite sieht man einen Ausschnitt dieser Page. Durch die Integration mit ChatGPT hat der Vermieter die Möglichkeit, stichwortartige Begriffe in die Schuhbeschreibung einzuge-ben. Die Ausgabe zeigt daraufhin eine detaillierte Beschreibung der Schuhe basierend auf den eingegebenen Stichworten. Wenn ein Schuh vermietet wurde, hat der Vermieter keine Möglichkeit, die Schuhe zu updaten oder zu löschen. Anstelle der üblichen Buttons werden dem Vermieter keine Aktualisierungs- oder Löschoptionen angezeigt. Stattdessen wird ein Text im Span-Element angezeigt, der darauf hinweist, dass die Schuhe bereits vermietet sind.

15. BIld: Wenn der Vermieter auf den Tab «Account» klickt, sieht er auch seine Informationen wie Name, E-Mail und die Rolle.

# Ansicht als Admin
16. BIld: Der Admin kann wie der Vermieter Schuhe erstellen, löschen und updaten.

17. BIld & 18. Bild:  Zusätzlich kann er auch Mieter und Vermieter erstellen und löschen. 

19. Bild: Aktuell geht man danach vor, dass der Mieter die Schuhe pünktlich zurücksendet oder ein E-Mail sendet, dass er die Schuhe vorzeitig zurückgibt. Sobald der Vermieter die Ware erhält, überprüft er, ob alles in Ordnung ist. Derzeit wird davon ausgegangen, dass die Schuhe nie beschädigt zurückgegeben werden. Nach Erhalt der Schuhe kann der Vermieter es wieder freigeben, indem er dem Admin also mir ein Mail schreibt, dass er die Schuhe mit dieser SchuhID und MieterID zurückerhalten hat. Im Tab «Vermietete Kleidung» sieht der Admin, welche Schuhe an welchen Mieter vermietet wurden. Gemäss dem obigen Beispiel sollte ein Schuh an einem Mieter vermietet sein. Hier sieht man auch die Pagination. Wenn der Vermeiten den «Freigeben» Button drückt, wird der Status wieder auf «Verfügbar» geändert, und gleichzeitig sendet das System eine E-Mail an den Vermieter, dass die Schuhe wieder zum Vermieten verfügbar sind.



# Aufgaben und Funktionen eingebundener Drittsysteme
20. BIld: Die API von Disify (https://disify.com/api/email/)wurde sowohl im Frontend als auch im Backend integriert. Diese API dient der Validierung von E-Mails und der Überprüfung, ob der DNS Eintrag vorhanden ist und ob die E-Mail-Adresse einem temporären E-Mail-Provider gehört. Im nachfolgenden Screenshot ist ein Beispiel für einen möglichen Fehlschlag zu sehen.

21. Bild: Nebst dieser Implementierung ermöglicht die Plattform auch das Versenden von E-Mails über SMTP. Hierzu wurde die folgende E-Mail-Adresse erstellt: «tzwinse2@gmail.com». Wenn ein Mieter ein paar Schuh mietet, wird er per E-Mail darüber benachrichtigt, dass das Mieten erfolgreich war. Die E-Mail hat folgenden Inhalt.
Ebenfalls erhält der Vermieter eine Benachrichtigung, dass jemand seine Schuhe gemietet hat. Die entsprechende E-Mail sieht wie folgt aus.

22. BIld: Wenn der Mieter die Schuhe zurücksendet und der Vermieter die Benachrichtigung per Mail an den Admin sendet und er die Schuhe wieder freigibt, erhält der Vermieter eine Mail, dass die Schuhe zurückgegeben worden sind, somit weiss er nun dass seine Schuhe wieder im Schuhe Portal zur Verfügung stehen zum Vermieten. Die folgende E-Mail.
Damit ist der Mietvorgang für die Schuhe zwischen Mieter und Vermieter abgeschlossen.

# Automatisierte ROllenzuweisung
23. Bild: Wenn ein neuer Benutzer sich registriert und dabei die Rolle auswählt, die er tätigen möchte, dann wird im Auth0 automatisch diese Rolle zugewisen, und somit auch die Berechtigungen, die er für diese Rolle erhält. Nun muss die Rolle nicht mehr manuell hinzugefügt werden auf Auth0 für jeden neuen Benutzer.

# ChatGPT (Optional)
Neben der E-Mail Anbindung wurde auch eine Integration mit ChatGPT für das Projekt 
implementiert. Das Ziel besteht darin, bei der Erstellung von Schuhen mit Hilfe der ChatGPT-API eine detaillierte Schuhbeschreibung zu generieren (siehe Kapitel Beschreibung des Frontends). Diese Information wird im Backend gespeichert und anschliessend an das Front-end übermittelt. Der Vermieter kann in der Schuhebeschreibung einzelne Punkte stichwortar-tig erfassen, und in der Ausgabe erhält man eine ausführliche Beschreibung der Schuhe. Der API-Key wurde uns zur Verfügung gestellt.  





