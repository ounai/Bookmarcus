# Bookmarcus
Bookmarking App


## Tietokanta
Tietokanta luodaan nyt build.gradlessa scriptillä. Luonti SQL löytyy sql/init.sql
Jarit eivät oletuksena toimi koska jariin ei tule mukaan muita kansioita.
Nämä määritellään sitten distZip komennon luotavaksi (tämä luo sovelluksesta levitysversion)

Devaus versioon saat tietokannan käyttöön vain suorittamalla gradle build tai gradle run (eli luodaan suorituksen yhteydessä).

Netbeansin kanssa oli ongelma kun netbeans suoritti buildin sql-komennot väärässä kansiossa (netbeansin oletuskansiossa). Nyt jos tietokannan luominen ei onnistu ei se kaada buildia, vaan tulee vain virheilmoitus. gradle run tai gradle build toimii yleisesti, mutta jos tietokanta ei ilmesty tarkasta konsolista onko virheilmoituksia (SQLException).

## Testaus
Loin tuonne ihan vain testiksi koodia Mainiin sekä Dummy-luokan. Kaiken voi poistaa, tarkoitus vain
näyttää että tietokanta toimii ja testit toimii.

## BookmarkIO
Kaikki tietokantakyselyt tämän kautta. Tällä hetkellä implementoituina metodeina ovat: 
   - Find(id) palauttaa Bookmark:in jos id:llä löytyy, muuten null
   - getAll()  palauttaa kaikki Bookmarkit ArrayListina
   - add(Bookmark)  lisää uuden Bookmarkin tietokantaan. Palauttaa true jos onnistui, muuten false
   - delete(id)  ei vielä implementoitu
   - delete(Bookmark)  ei vielä implementoitu
