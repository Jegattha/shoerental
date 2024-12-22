# Stand der Implementation, nächste Schritte (mit Referenz auf den Backlog). Nimmt sowohl Bezug auf funktionale wie auch auf nicht funktionale Anforderungen.

Der MVP von Shoerental wurde erfolgreich implementiert, und grundlegende Funktionen und Abläufe sind voll funktionsfähig. Es ist möglich, ein paar Schuh zu mieten, und der Admin kann es anschließend zurücksetzen. Vermietete Schuhe sind korrekt gesperrt, sodass sie weder von anderen Personen gemietet noch vom Vermieter bearbeitet oder gelöscht werden können. Die Integration mit Drittsystemen funktioniert wie erwartet, wobei der Vermieter eine Benachrichtigung erhält, wenn ein Schuh vermietet wird, und der Mieter eine Benachrichti-gung, wenn er etwas mietet. Nur der Admin hat die Möglichkeit neue Vermieter und Mieter zu erstellen und zu löschen. Das Login ist ebenfalls erfolgreich implementiert und beinhaltet die automatische ROllenzuweisung als Dritt-System. Die Benutzer können schon beim Registrieren ihre ROlle auswählen. NAch erfolgreicher Registrierung wird dem Benutzer die Rolle automatisch zugeteilt auf Auth0. Für den Mieter bietet die Account-Seite einen klaren Überblick über die aktuell gemieteten Schuhe und ihre Mietdauer. Auf der Vermietete Schuhe gibt es einen Überblick für den Admin darüber, welche Schuhe an welchen Mieter vermietet wurden. Alle erstellten Schuhe erhalten automatisch ein Bild, was das visuelle Erscheinungsbild ansprechend gestaltet. Auf diese Weise kann der Mieter leicht erkennen, welche Schuhe er gemietet hat.

# Nächste Schritte
Einer der nächsten Schritte besteht darin, die Rücksendeabwicklung genauer zu klären. Derzeit geht man davon aus, dass der Mieter die Schuhe rechtzeitig zurücksendet oder vorzeitig per E-Mail bescheid gibt. Auf der Account Seite des Mieters gibt es bereits eine Übersicht über die gemieteten Schuhe. Eine Möglichkeit zur Verbesserung könnte die Implementierung eines Buttons sein, der mit «Schuhe zurückgeben» beschriftet ist. Wenn der Mieter auf diesen Button klickt, wird der Vermieter benachrichtigt, dass die Kleidung zurückgesendet wird. In den folgenden Tagen kann der Vermieter dann den Status von «vermietet» auf «verfügbar» zurücksetzen, indem er dem Admin ein Mail schreibt. Diese Logik ist bereits weit verbreitet, wie es beispielsweise bei Zalando der Fall ist. Bei Zalando hat der Benutzer die gleiche Funktionalität, um die Schuhe zurückzugeben, das er nicht kaufen möchte. Man legt ausserdem bei jeden Schuhen ein Rücksendeetikett mit. Neben dem Rücksende-Button sollte auch die Möglichkeit bestehen, eine Verlängerung der Mietdauer für die Schuhe zu fordern. Auf diese Weise hat der Mieter jederzeit die Möglichkeit, die Mietperiode zu verlängern.
Die Zahlungsabwicklung ist derzeit sehr einfach gestaltet. Das Mieten wird durch Betätigen des Mieten-Buttons bestätigt. Dies könnte jedoch weiterentwickelt werden, um echte Zahlungsoptionen wie Twint, PayPal oder Kreditkartenzahlung zu integrieren.
Derzeit erfüllt das System Sicherheitsstandards durch verschlüsselte Logins und rollenbasierte Zugriffsrechte. Zukünftig sollen Funktionen wie 2-Faktor-Authentifizierung implementiert werden.
Die Bildzuordnung basiert derzeit auf dem Key-Value-Prinzip. Es wäre jedoch ansprechender, wenn der Vermieter die Möglichkeit hätte, eigene Bilder für die Schuhe hochzuladen. Dadurch könnten die Schuhe einzigartig sein. Ein möglicher Ansatz hierfür wäre die Verwendung eines FileReader in Kombination mit der Hochladung des Bildes in Base64-Format.
Aktuell ist die Plattform auf Vermieter ausgerichtet, da sie eine umfangreiche Auswahl an luxuriöse Schuhe anbieten. Eine Überlegung könnte sein, weitere Schuhlöden zu kontakierten und deren Schuhe ebenfalls auf Shoerental zu veröffentlichen. Der Vorteil dabei wäre, dass durch das Veröffentlichen externer Schuhe eine Provision erhoben werden kann. Auf diese Weise eröffnen sich zusätzliche Einkommensmöglichkeiten neben der Vermietung der eigenen Marke.

# Kurz und knapp zusammengefasst:

1. Stand der Implementierung

Der MVP ist implementiert, grundlegende Funktionen sind vorhanden:
Mieten und Rückgabe von Schuhen.
Rollenbasierte Ansichten (Mieter, Vermieter, Admin).
Admins können Vermieter und Mieter erstellen oder löschen.
Automatische Bildzuordnung für Schuhe.
Nicht-funktionale Anforderungen:
Sicherheitsstandards durch rollenbasierte Authentifizierung (z. B. via Auth0).
Nutzerfreundliche Dashboards für verschiedene Rollen.

2. Nächste Schritte

Funktionale Erweiterungen:

Rückgabeprozess: Implementierung eines Rücksende-Buttons, der Benachrichtigungen für Mieter und Vermieter generiert.
Zahlungsintegration: Hinzufügen von Zahlungsoptionen wie PayPal, Twint oder Kreditkarte.
Bilder-Upload: Vermietern ermöglichen, eigene Schuhbilder hochzuladen.
Externe Schuhläden: Kontaktaufnahme mit Partnern zur Erweiterung des Sortiments.
Nicht-funktionale Verbesserungen:

Sicherheit: Einführung von 2-Faktor-Authentifizierung.
Performance: Optimierung der Datenbankabfragen für eine größere Nutzerbasis.
Skalierbarkeit: Vorbereitung des Systems auf die Integration externer Partner.

3. Bezug zum Backlog

Die genannten Schritte sind bereits im Backlog erfasst und priorisiert.
Funktionen wie der Rücksende-Button und die Zahlungsintegration haben hohe Priorität für die nächste Iteration.


