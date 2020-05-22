# Release Notes

## Eingegebene Anreden werden in ihre Bestandteile aufgeteilt
Anreden, welche in das Eingabefeld eingegeben werden, werden in die Bestandteile aufgeteilt:
* Anrede
* Standardisierte Briefanrede
* Titel
* Geschlecht
* Vorname
* Nachname

Das Aufteilen wird während der Eingabe durchgeführt. 
Nach dem Eingeben einer Anrede kann eine standardisierte Briefanrede durch den "Anrede generieren"-Knopf erzeugt werden.
## Die Benutzeroberfläche wurde erweitert, um manuelle Verbesserungen der automatischen Zuweisung vorzunehmen.
Der Benutzer kann die automatische Erkennung zur Berechnung der Briefanrede über manuelle Felder überschreiben. Hierzu stehen alle Teile der Anrede als Felder oder Auswahllisten zur Verfügung.

## Titel sind über eine Menüoption konfigurierbar.
Über eine Option im Menü können neue Titel konfiguriert und im Programm gespeichert werden, um so eine wachsende Automatisierung des Systems zu erreichen.

## Muster zur automatischen Erkennung sind konfigurierbar. 
Neben der Option zur Konfiguration von Titel ist auch eine Konfiguration von Mustern möglich, die das System zur Generierung einer Briefanrede verwenden kann.

## Schnittstelle zur Überprüfung von Duplikaten zum CRM System wird angeboten (Nicht implementiert)
Eine Schnittstelle, an welche das CRM-System angebunden werden kann, ist nun bereitgestellt. Aktuell wird hierbei der Benutzer über eine entsprechende Meldung informiert, dass keine Verbindung zum CRM-System möglich ist.

## Automatische Erkennung des Geschlechts durch den Vornamen
Das System nimmt eine automatische Unterteilung der Namen in Vornamen und Nachnamen vor.
In der Anrede wird das Geschlecht anhand des Vornamens oder der Formulierung automatisch erkannt. Falls kein Geschlecht zugeordnet werden kann, wird kein Geschlecht ausgewählt. Das führt zu einer geschlechtsneutralen Anrede.