# Testkonzept und Dokumentation
## CI/CD
Tests werden im Repository bei jedem push automatisch ausgeführt. Außerdem wird ausgewertet welche Tests erfolgreich waren und welche fehlgeschlagen sind. Bei den fehlgeschlagenen Tests wird außerdem die Fehlermeldung protokolliert, damit der Fehler behoben werden kann.

Die Informationen zum aktuellsten Durchlauf des CI/CD Systems befinden sich unter: https://git.nico-dreher.de/University/sw-quality/splitter/pipelines/latest/test_report

Durch auswählen des *Tests* Reiter, gelangt man zur Auflistung der Test suites. Wählt man davon eine aus, kann man die Auflistung der Tests sehen.
## UI-Test
Die Oberflächentests sollen ungewünschte Verhaltensweisen bei der Bedienung der Oberfläche aufdecken. Hierzu wurde die Funktionalität der einzelnen UI-Elemente getestet und, ob diese zum gewünschten Ergebnis führen (z.B. ein Fenster richtig öffnen). Hierbei werden auch fehlerhafte Eingaben überprüft.

Die Kernfunktionialität der Oberfläche wird über parametrisierte Tests gestaltet. Hierbei soll zum einen geprüft werden, ob die Felder zur manuellen Eingabe mit den richtigen Daten gefüllt wird, wenn eine Eingabe getätigt wird. Hierzu wurden folgende Testfälle definiert, welche alle zum richtigen Ergebnis führen. (Testfälle in Anhang 1)

Neben diesen wurde auch die richtige Generierung der Briefanrede sowie das Anzeigen auf der Benutzeroberfläche gezeigt. In Anhang 2 werden die Testdaten gezeigt, welche alle zum erwarteten Ergebnis führen. Dieser Test bekommen als Parameter den Eingabestring und das erwartete Ergebnis und werden dann ausgewertet.

## Unit Test
Durch Unit Tests sollen die Backend Komponenten getestet werden. Dadurch können Testfälle abgedeckt werden, welche in den UI-Tests nicht möglich sind. Dazu zählen Schnittstellen, die in der Anwendung so ineinandergreifen, dass die Funktionen der Komponenten durch die UI-Tests nicht ausreichend überprüft werden.

Für einige Testfälle werden parametriesierte Tests verwendet. Dadurch können viele Testfälle durch wenig Code und einer Tabelle abgebildet werden. 

Um das Parsen durch die standard Muster zu überprüfen, wird ein parametrisierter Test durchgeführt. Dazu wird der Input Text und die erwarteten Outputs definiert. (Testfälle in Anhang 3)

Um das Parsen von einzelnen Mustern zu überprüfen, wird ein parametrisierter Test durchgeführt. Dazu wird ein Muster angegeben und die erwarteten Outputs definiert. Es sind nur Testfälle für ein Geschlecht und zwei Muster angegeben, weil das Verhalten der Paser unabhänig vom Geschlecht und Muster getestet wird. Die Testfälle befinden sich unter Anhang 4.

Um zu überprüfen ob der Ausgabe Parser mit den Eingaben die richtige Briefanrede generiert, wird ein parametrisierter Test durchgeführt. Dazu wurde die Testfälle aus Anhang 2 verwendet.

Um das Erkennen der Titel zu testen, wird ein parametrisierter Test durchgeführt. Dazu wird eine Eingabe in die Titel zerlegt und mit dem erwarteten Ergebnis verglichen. (Testfälle in Anhang 5)

Damit die Nachname korrekt formatiert werden, wird ein parametrisierter Test durchgeführt. Dazu werden die Tokens eines Nachnamen mit Semikolon separiert eigegeben und mit der erwarteten formatierten Ausgabe verglichen. Die Destfälle befinden sich in Anhang 6.

Zusätzlich zu den zuvor genannten Tests, wird auch das Verhalten überprüft, wenn kein Muster zu einer Eingabe passt. Außerdem wurde das Verhalten bei ungültigen Tokens im Muster getestet. Das Verhalten beim Versuch einen Kontakt auszugeben, für den es kein Ausgabemuster gibt, wird auch überprüft. Das Regex muster für Namen wird mithilfe aller Namen aus der Namensliste getestet.

## Anhang 1

| Eingabe                                                      | Vorname (erwartet)     | Nachname (erwartet)              | Geschlecht     | Titel (erwartet, mit ; getrennt)                   | Sprache       |
| ------------------------------------------------------------ | ---------------------- | -------------------------------- | -------------- | -------------------------------------------------- | ------------- |
| Frau Sandra Berger                                           | "Sandra"               | "Berger"                         | "FEMALE"       |                                                    | "Deutsch"     |
| Herr Dr. Sandro Gutmensch                                    | "Sandro"               | "Gutmensch"                      | "MALE"         | "Dr."                                              | "Deutsch"     |
| Professor Heinrich Freiherr vom Wald                         | "Heinrich"             | "Freiherr vom Wald"              | "MALE"         | "Professor"                                        | "Deutsch"     |
| Mrs. Doreen Faber                                            | "Doreen"               | "Faber"                          | "FEMALE"       |                                                    | "Englisch"    |
| Mme. Charlotte Noir                                          | "Charlotte"            | "Noir"                           | "FEMALE"       |                                                    | "Französisch" |
| Frau Prof. Dr. rer. nat. Maria von  Leuthäuser-Schnarrenberger | "Maria"                | "von Leuthäuser-Schnarrenberger" | "FEMALE"       | "Prof.; Dr. rer. nat"                              | "Deutsch"     |
| Herr Dipl. Ing. Max von Müller                               | "Max"                  | "von Müller"                     | "MALE"         | "Dipl. Ing."                                       | "Deutsch"     |
| Dr. Russwurm, Winfried                                       | "Winfried"             | "Russwurm"                       | "MALE"         | "Dr."                                              | "Deutsch"     |
| Herr Dr.-Ing. Dr. rer. nat. Dr. h.c.  mult. Paul Steffens    | "Paul"                 | "Steffens"                       | "MALE"         | "Dr.-Ing.; Dr. rer. nat.; Dr. h.c.  mult."         | "Deutsch"     |
| Herr Prof. Dr. rer. nat. Dr.-Ing. Dr.  h.c. mult. Fridolin Müller | "Fridolin"             | "Müller"                         | "MALE"         | "Prof.; Dr. rer. nat.; Dr.-Ing.; Dr. h.  c. mult." | "Deutsch"     |
| Señor Salvador Gonzales                                      | "Salvador"             | "Gonzales"                       | "MALE"         |                                                    | "Spanisch"    |
| Signora Marcella Paolini                                     | "Marcella"             | "Paolini"                        | "FEMALE"       |                                                    | "Italienisch" |
| Signora Dr. Vanessa Torez                                    | "Vanessa"              | "Torez"                          | "FEMALE"       | "Dr."                                              | "Italienisch" |
| M Alexandre Renault                                          | "Alexandre"            | "Renault"                        | "MALE"         |                                                    | "Französisch" |
| Mr. Ron Weasley                                              | "Ron"                  | "Weasley"                        | "MALE"         |                                                    | "Englisch"    |
| Mrs. Katy O'Shea                                             | "Katy"                 | "O'Shea"                         | "FEMALE"       |                                                    | "Englisch"    |
| Frau Dr. Caitlyn Jenner                                      | "Caitlyn"              | "Jenner"                         | "FEMALE"       | "Dr."                                              | "Deutsch"     |
| Señora Prof. Kim Torres                                      | "Kim"                  | "Torres"                         | "FEMALE"       | "Prof."                                            | "Spanisch"    |
| Sig. Dr. rer. nat. Dr.-Ing. Dr. h.c.  mult. Antonio Cipolla  | "Antonio"              | "Cipolla"                        | "MALE"         | "Dr.-Ing.; Dr. rer. nat.; Dr. h.c.  mult."         | "Italienisch" |
| Mr Prof. Tobias Rieper                                       | "Tobias"               | "Rieper"                         | "MALE"         | "Prof."                                            | "Englisch"    |
| Frau Anabelle Maria Friedrich                                | "Anabelle Maria"       | "Friedrich"                      | "FEMALE"       |                                                    | "Deutsch"     |
| Herr Tobias Raphael Meier Müller                             | "Tobias Raphael"       | "Meier-Müller"                   | "MALE"         |                                                    | "Deutsch"     |
| Herr Dr. Tobias Max Lukas Max Prinz von  Sachsen Anhalt      | "Tobias Max Lukas Max" | "Prinz von Sachsen-Anhalt"       | "MALE"         | "Dr."                                              | "Deutsch"     |

 
## Anhang 2

| Eingabe                                   | Generierte Briefanrede                                 |
| ----------------------------------------- | ------------------------------------------------------ |
| Frau Sandra  Berger                       | "Sehr geehrte Frau Sandra Berger"                      |
| Herr  Professor Winfried Schröder         | "Sehr geehrter Herr Professor Winfried  Schröder"      |
| Luca Meier                                | "Sehr geehrter Herr Luca Meier"                        |
| Herr Dr.  Dipl. Ing. Darius Nightrider    | "Sehr geehrter Herr Dr. Dipl. Ing. Darius  Nightrider" |
| M. Adam  Bernard                          | "Monsieur Adam Bernard"                                |
| Señor  Salvador Gonzales                  | "Estimada Señor Salvador Gonzales"                     |
| Mr. Dr.  Tobias Rieper                    | "Dear Mr Dr. Tobias Rieper"                            |
| Mrs. Doreen  Faber                        | "Dear Mrs Doreen Faber"                                |
| Señora Dr.  Dr. rer. nat. Kimberly Torres | "Estimada Señora Dr. rer. nat. Dr.  Kimberly Torres"   |
| Mme.  Charlotte Bernand                   | "Madame Charlotte Bernand"                             |

## Anhang 3
| Eingabe                                                          | Sprache (erwartet) | Geschlecht (erwartet) | Titel (erwartet)                                   | Vorname (erwartet)    | Nachname (erwartet)             |
| ---------------------------------------------------------------- | ------------------ | --------------------- | -------------------------------------------------- | --------------------- | ------------------------------- |
|"Herr Dr. Hans Wurst"                                             |"Deutsch"           |"MALE"                 | "Dr."                                              | "Hans"                | "Wurst"                         |
|"Herr Hans Wurst"                                                 |"Deutsch"           |"MALE"                 | ""                                                 | "Hans"                | "Wurst"                         |
|"Herr Dr. Dr. rer. nat. Hans Wurst"                               |"Deutsch"           |"MALE"                 | "Dr. Dr. rer. nat."                                | "Hans"                | "Wurst"                         |
|"Herr Dr. Dr. rer. nat. Hans Wurst Fabrik"                        |"Deutsch"           |"MALE"                 | "Dr. Dr. rer. nat."                                | "Hans"                | "Wurst-Fabrik"                  |
|"Frau Dr. Anne Marie Müller Maier"                                |"Deutsch"           |"FEMALE"               | "Dr."                                              | "Anne Marie"          | "Müller-Maier"                  |
|"Frau Sandra Berger"                                              |"Deutsch"           |"FEMALE"               | ""                                                 | "Sandra"              | "Berger"                        |
|"Herr Dr. Sandro gutmensch"                                       |"Deutsch"           |"MALE"                 | "Dr."                                              | "Sandro"              | "Gutmensch"                     |
|"Professor heinrich freiherr vom Wald"                            |"Deutsch"           |"MALE"                 | "Professor"                                        | "Heinrich"            | "Freiherr vom Wald"             |
|"Mrs. Doreen Faber"                                               |"Englisch"          |"FEMALE"               | ""                                                 | "Doreen"              | "Faber"                         |
|"Mme. charlotte noir"                                             |"Französisch"       |"FEMALE"               | ""                                                 | "Charlotte"           | "Noir"                          |
|"Frau Prof. Dr. rer. nat. Maria von leuthäuser-Schnarrenberger"   |"Deutsch"           |"FEMALE"               | "Prof. Dr. rer. nat."                              | "Maria"               | "von Leuthäuser-Schnarrenberger"|
|"Herr Dipl. Ing. Max von Müller"                                  |"Deutsch"           |"MALE"                 | "Dipl. Ing."                                       | "Max"                 | "von Müller"                    |
|"Dr. Russwurm| Winfried"                                          |"Deutsch"           |"MALE"                 | "Dr."                                              | "Winfried"            | "Russwurm"                      |
|"Herr Dr.-Ing. Dr. rer. nat. Dr. h.c. mult. Paul Steffens"        |"Deutsch"           |"MALE"                 | "Dr.-Ing. Dr. rer. nat. Dr. h.c. mult."            | "Paul"                | "Steffens"                      |
|"Herr Prof. Dr. rer. nat. Dr.-Ing. Dr. h.c. mult. Fridolin Müller"|"Deutsch"           |"MALE"                 | "Prof. Dr. rer. nat. Dr.-Ing. Dr. h.c. mult."      | "Fridolin"            | "Müller"                        |
|"Señor Salvador Gonzales"                                         |"Spanisch"          |"MALE"                 | ""                                                 | "Salvador"            | "Gonzales"                      |
|"Signora Marcella Paolini"                                        |"Italienisch"       |"FEMALE"               | ""                                                 | "Marcella"            | "Paolini"                       |
|"Signora Dr. Vanessa Torez"                                       |"Italienisch"       |"FEMALE"               | "Dr."                                              | "Vanessa"             | "Torez"                         |
|"M Alexandre Renault"                                             |"Französisch"       |"MALE"                 | ""                                                 | "Alexandre"           | "Renault"                       |
|"Mr. Ron Weasley"                                                 |"Englisch"          |"MALE"                 | ""                                                 | "Ron"                 | "Weasley"                       |
|"Mrs. Katy O'Shea"                                                |"Englisch"          |"FEMALE"               | ""                                                 | "Katy"                | "O'Shea"                        |
|"Frau Dr. Caitlyn Jenner"                                         |"Deutsch"           |"FEMALE"               | "Dr."                                              | "Caitlyn"             | "Jenner"                        |
|"Señora Prof. Kim Torres"                                         |"Spanisch"          |"FEMALE"               | "Prof."                                            | "Kim"                 | "Torres"                        |
|"Sig. Dr. rer. nat. Dr.-Ing. Dr. h.c. mult. Antonio cipolla"      |"Italienisch"       |"MALE"                 | "Dr. rer. nat. Dr.-Ing. Dr. h.c. mult."            | "Antonio"             | "Cipolla"                       |
|"Mr Prof. Tobias Rieper"                                          |"Englisch"          |"MALE"                 | "Prof."                                            | "Tobias"              | "Rieper"                        |
|"Frau Anabelle Maria Friedrich"                                   |"Deutsch"           |"FEMALE"               | ""                                                 | "Anabelle Maria"      | "Friedrich"                     |
|"Herr Tobias Raphael Meier Müller"                                |"Deutsch"           |"MALE"                 | ""                                                 | "Tobias Raphael"      | "Meier-Müller"                  |
|"Herr Dr. Tobias Max Lukas Max Prinz von Sachsen Anhalt"          |"Deutsch"           |"MALE"                 | "Dr."                                              | "Tobias Max Lukas Max"| "Prinz von Sachsen-Anhalt"      |

## Anhang 4
| Sprache | Geschlecht | Eingabemuster                    | Eingabe                                  | Geschlecht (erwartet)    | Titel (erwartet)  | Vorname (erwartet) | Nachname (erwartet) | Null erwartet |
| ------- |----------- | -------------------------------- | ---------------------------------------- | ------------------------ | ----------------- | ------------------ | --------------------| ------------- |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"Herr Dr. lukas müller"                   |"MALE"                    |"Dr."              |"Lukas"             |"Müller"             |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"Herr luki mon"                           |"MALE"                    |""                 |"Luki"              |"Mon"                |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"HerR Dr. Dr. rer. nat. Tim maier"        |"MALE"                    |"Dr. Dr. rer. nat."|"Tim"               |"Maier"              |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"HERR Dr. Dr. rer. nat. Hans Schöne stadt"|"MALE"                    |"Dr. Dr. rer. nat."|"Hans"              |"Schöne-Stadt"       |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"Herr Dr. Dr. rer. nat. Hans Schöne stadt"|"MALE"                    |"Dr. Dr. rer. nat."|"Hans"              |"Schöne-Stadt"       |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"XXXXX"                                   |"NONE"                    |""                 |""                  |""                   |true           |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|""                                        |"NONE"                    |""                 |""                  |""                   |true           |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"Herr"                                    |"MALE"                    |""                 |""                  |""                   |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"Herr Dr. Dr. "                           |"MALE"                    |"Dr. Dr."          |""                  |""                   |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"Herr Dr. Dr. Daniel Daniel"              |"MALE"                    |"Dr. Dr."          |"Daniel"            |"Daniel"             |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %VORNAMEN %NACHNAMEN"|"Herr Dr. Dr. Daniel Daniel Meier"        |"MALE"                    |"Dr. Dr."          |"Daniel Daniel"     |"Meier"              |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %NACHNAMEN %VORNAMEN"|"Herr Dr. Dr. Hans Dieter"                |"MALE"                    |"Dr. Dr."          |"Dieter"            |"Hans"               |false          |
|"Deutsch"|"MALE"      |"Herr %TITEL %NACHNAMEN %VORNAMEN"|"Herr Dr. Dr. Müller Müller"              |"MALE"                    |"Dr. Dr."          |"Müller"            |"Müller"             |false          |

## Anhang 5
| Eingabe                                  | StartIndex | Titel (erwartet)  | EndIndex (erwartet) |
| ---------------------------------------- | ---------- | ----------------- | ------------------- |
|"Dr. Dr. Dr. Hans Wurst"                  |0           |"Dr.;Dr.;Dr."      |3                    |
|"Hans Meier"                              |0           |""                 |0                    |
|"Dr. dr. Dr. Nanana Batman"               |0           |"Dr.;Dr.;Dr."      |3                    |
|"Dr. dr. DADADA Dr. Nanana Batman"        |0           |"Dr.;Dr."          |2                    |
|"Frau Sandra Berger"                      |1           |""                 |1                    |
|"Herr Professor Winfried Schröder"        |1           |"Professor"        |2                    |
|"Herr PrOfEsSoR Winfried Schröder"        |1           |"Professor"        |2                    |
|"Luca Meier"                              |0           |""                 |0                    |
|"Herr Dr. Dipl. Ing. Darius Nightrider"   |1           |"Dr.;Dipl. Ing."   |4                    |
|"M. Adam Bernard"                         |1           |""                 |1                    |
|"Señor Salvador Gonzales"                 |1           |""                 |1                    |
|"Mr. Dr. Tobias Rieper"                   |1           |"Dr."              |2                    |
|"Mrs. Doreen Faber"                       |1           |""                 |1                    |
|"Señora Dr. Dr. rer. nat. Kimberly Torres"|1           |"Dr.;Dr. rer. nat."|5                    |
|"Mme. Charlotte Bernand"                  |1           | ""                |1                    |

## Anhang 6
| Nachname Tokens                | Ausgabe (erwartet)             |
| ------------------------------ | ------------------------------ |
|"Berger"                        |"Berger"                        |
|"Gutmensch"                     |"Gutmensch"                     |
|"Freiherr;vom;Wald"             |"Freiherr vom Wald"             |
|"Faber"                         |"Faber"                         |
|"Noir"                          |"Noir"                          |
|"Gonzales"                      |"Gonzales"                      |
|"von;Leuthäuser;Schnarrenberger"|"von Leuthäuser-Schnarrenberger"|
|"von Müller"                    |"von Müller"                    |
|"Steffens"                      |"Steffens"                      |