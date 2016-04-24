# Boilerplate za APS2 Naloga2

Dodani so vsi vhodi, ki jih da lalgec in tudi pricakovani izhodi. Projekt je skonfiguriran za IntellJ IDEA, za druge projekte, you're on your own :wink:

### Kaj vsebuje

`Naloga2.java`: samo `class Naloga2` z `main` metodo, tukaj pises kodo in ta datoteka se oddaja.


`Naloga2Test.java`: uporablja JUnit za testiranje, za IntellJ projekt bi moral biti skonfigurirati, drugace dodaj `junit-4.12.jar` v projekt. V IntellJ obstaja moznost za poganjanje samo enega od testov, torej npr. `testOS`, za hitrejse testiranje, da ni potrebno cakati vseh testov. Casovna omejitev je 15s, kar je vec kot na lalgec-u, zato se ne prevec zanasat na to (nastavil sem zaradi neskoncnih zank). IntellJ ponuja tudi diffanje, tako da se lahko zelo enostavno vidi razliko med pricakovanim izhodom in kaj je na izhod izpisal programa. IntellJ je kul :smile:

_Note: Vse, kar se izpise preko `System.out` bo redirectano v `test-[X]-[Y].out`, zato za izpis poglejte v to datoteko oz. diff. Da bo izpis viden v konzoli lahko uporabis `System.err`, ampak ne pozabi tega odstraniti pred oddajo._


`test-files`: folder, ki vsebuje vse inpute in outpute na lalgec-u, Naloga2Test jih uporablja za preverjanje. Branje testov gre po abecednem redu oznak (A, B, C) ter narascajoce po stevilki testa. Ce dodajate svoje teste, poglejte kako se berejo in sortirajo v `Naloga2Test`. Lahko pa vrzes nek input v `test-A-0.in`, pricakovan rezultat v `test-A-0.expected`, argumente programu pa podate v prvem elementu `args` znotraj Naloga2Test (trenutno je narejeno za primer za `ka` iz ucilnice).


`oddaj.sh`: ta skripta omogoca upload in oddajanje na lalgec. Potrebno urediti za ssh username.


### Uporaba

 - clonaj repository (`git clone https://github.com/markogresak/naloga2-boilerplate`) ali potegni kot .zip
 - uvozi projekt v editor
 - srecno pri programiranju :smile:

**Ce kaj manjka ali ne deluje, napisi v issue na GitHubu ali me kontaktiraj po katerem drugem kanalu.**
