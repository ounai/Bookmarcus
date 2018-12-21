# Bookmarcus
[![Build Status](https://travis-ci.org/ounai/Bookmarcus.svg?branch=master)](https://travis-ci.org/ounai/Bookmarcus)
[![codecov](https://codecov.io/gh/ounai/Bookmarcus/branch/master/graph/badge.svg)](https://codecov.io/gh/ounai/Bookmarcus)

Bookmarking App
  - [Product Backlog](https://docs.google.com/spreadsheets/d/1BRYb5EGVMEszLWBK_oi_MtSrRDbMbrU323mzProowRY/edit?usp=sharing)
## Raportti
[Raportti valmistuu tänne](https://docs.google.com/document/d/1ZRFBGQo2cenOXOfo-UxmktxmGYtlHAKbdGHu4W5cJ0Q/edit?usp=sharing)
## Käyttöönotto-ohjeet
  - Lataa release-sivulta .zip tiedosto
  - Pura tiedosto
  - Suorita kansiossa bin oleva Bookmarcus.sh (linux) tai Bookmarcus.bat (windows)

## Ohjeet distribuution tekijälle
  - Tagaa uusin commit joka tulee mukaan
  - Poista Bookmarcus.db (sql/db) mikäli haluat tyhjän tietokannan
  - Suorita `gradle distZip`
  - Vie versio githubin releaseihin

## Demodata
Demodataa on kansiossa sql/demodata. Kopioi sql-tiedostot sql-kansioon niin buildi (gradle) suorittee ne.

## Tietokanta
Tietokanta luodaan nyt build.gradlessa scriptillä. Luonti SQL löytyy sql/init.sql
Jarit eivät oletuksena toimi koska jariin ei tule mukaan muita kansioita.
Nämä määritellään sitten distZip komennon luotavaksi (tämä luo sovelluksesta levitysversion)

Devaus versioon saat tietokannan käyttöön vain suorittamalla gradle build tai gradle run (eli luodaan suorituksen yhteydessä).

Netbeansin kanssa oli ongelma kun netbeans suoritti buildin sql-komennot väärässä kansiossa (netbeansin oletuskansiossa). Nyt jos tietokannan luominen ei onnistu ei se kaada buildia, vaan tulee vain virheilmoitus. gradle run tai gradle build toimii yleisesti, mutta jos tietokanta ei ilmesty tarkasta konsolista onko virheilmoituksia (SQLException).

## Javadoc
Koodi on dokumentoitu javadocilla. Komento `gradle javadoc` luo javadocin kansioon build/docs/javadoc.

## Testaus
Loin tuonne ihan vain testiksi koodia Mainiin sekä Dummy-luokan. Kaiken voi poistaa, tarkoitus vain
näyttää että tietokanta toimii ja testit toimii.

BookmarkIO testeissä luodaan testitietokanta jokaisen testin alussa ja poistetaan se jokaisen testin lopussa. Kun teette luokkia jotka ovat riippuvaisia BookmarkIO:sta, käyttäkää riippuvuutuena rajapintaa DatabaseIO ja testeissä injektoikaa stubi, kuten laskareissa opimme.

Definition of Done pitää määritellä Cucumberin avulla, joten kyseinen työkalu on konfiguroitu. `dummy.feature` on projektissa mukana toistaiseksi, kunnes koodin varsinaisia osuuksia saadaan määriteltyä tuon avulla.

## BookmarkIO
Kaikki tietokantakyselyt tämän kautta. Tällä hetkellä implementoituina metodeina ovat: 
   - Find(id) palauttaa Bookmark:in jos id:llä löytyy, muuten null
   - Find(author) palauttaa Bookmarkit ArrayListina, joissa author täsmää
   - getAll()  palauttaa kaikki Bookmarkit ArrayListina
   - getAllUnRead()  palauttaa kaikki lukemattomat Bookmarkit ArrayListina
   - getAllRead()  palauttaa kaikki luetut Bookmarkit ArrayListina
   - add(Bookmark)  lisää uuden Bookmarkin tietokantaan. Palauttaa true jos onnistui, muuten false
   - delete(id) poistaa Bookmarkin tietokannasta. Palauttaa true jos onnistui, muuten false
   - delete(Bookmark)  ei vielä implementoitu
   - markAsRead(id) merkitsee Bookmarkin luetuksi. Palauttaa true jos onnistui, muuten false
   - update(id, bookmark) päivitää Bookmarkin tiedot parametrina annetun Bookmarkin tietojen perusteella. 

## Tyyppi
Tietokannassa on nyt vain useita eri sarakkeita. Eri tyypit käyttävät niistä vain osaa. Jos jokin on null,
sitä ei kuulu näyttää ja eri tyyppien hallinnointi jää koodin hommaksi. Tyyppisarake kertoo mikä tyyppi on kyseessä. Tällä hetkellä seuraavasti:
   - 1 = Book
   - 2 = Article
   - 3 = Blogpost
Bookmarkin rakenne on hieman sekava ja täytyy miettiä mitkä kaikki tulisi pitää konstruktorissa ja mitkä
laittaa jälkikäteen. Kaikkia mahdollisia konstruktoreita olisi liikaa, joten parempi jos dataa annetaan
settereillä. Tein tällaisen arkkitehtuuriratkaisun ajan säästämiseksi, oletan ettei tämä ohjelma kasva kovin suureksi.

Bookmarkin tulostuksen hoitaa toString metodi, joka tulostaa kullekin tyypille oleelliset kentät. Jos tyyppiä ei ole valittu, tulostuu "Bookmark type not defined".
