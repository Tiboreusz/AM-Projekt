**Autorzy**

Projekt przygotowali Roman Mirolevich (272285) oraz Tibor Gałęzyka (272244).

**Nawigacja**

Schemat nawigacji znajduje się w projekcie Figma pod adresem https://www.figma.com/design/xioeFTIAKsLDZfLDlfeQTF/AM-Projekt?node-id=0-1&t=csoveFkajHjGSw5R-1 

**Importy**

Lista wykorzystanych "niestandardowych" (nie pobieranych domyślnie przez android studio) importów:

androidx.lifecycle:lifecycle-livedata-ktx:2.4.0, androidx.lifecycle:lifecycle-runtime-ktx:2.4.0, org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0, org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0,
androidx.room:room-runtime:2.6.1, androidx.room:room-ktx:2.6.1, androidx.compose.runtime:runtime-livedata:1.2.0, androidx.compose.material3:material3:1.2.1 ,androidx.navigation:navigation-compose:2.6.0,
androidx.compose.ui:ui:1.6.0, androidx.compose.material3:material3:1.2.1, androidx.navigation:navigation-compose:2.7.7, androidx.compose.ui:ui-tooling-preview:1.6.0, androidx.compose.runtime:runtime:1.5.0,   androidx.datastore:datastore-preferences:1.0.0, androidx.compose.ui:ui-tooling:1.6.0, androidx.compose.foundation:foundation:1.4.3, io.coil-kt:coil-compose:2.2.0.

**Strunktura**

Finalna wersja aplikacji znajduje się w katalogu "wersja finalna". <br>

**/wersja finalna**<br>
|---**/main**<br>
    |--AndroidManifest.xml      //plik manifestu<br>
    |--**/res**<br>
    |   |--**/drawable**      //ikony i obrazki<br>
    |   |--**/mipmap**      //ikony launchera<br>
    |   |--**/raw**      //audio i wideo<br>
    |   |--**/values**      //zmienne string<br>
    |   |--**/values-en**      //zmienne string po angielsku<br>
    |   |--**/xml**<br>
    |--**/java/com/example/am_projekt**<br>
        |--**/data**     //plik datastoreutils.kt - obsługuje zapis ustawień i preferencji<br>
        |--**/database**     //zawiera plik obsługujący bazę danych oraz DAO (framework room)<br>
        |--**/model**      //zawiera model obiektu "Place"<br>
        |--**/ui**<br>
        |  |--**/screens**      //zawiera definicje UI dla wszystkich ekranów<br>
        |  |--**/theme**      //zawiera pomocniczen wartości wykorzystywane do tworzenia UI<br>
        |--**/utils**      //zawiera LocaleUtils.kt wykrozystywane do obsługi preferencji związanych z lokalizacją<br>
        |--**/viewmodels**      //zawiera viewmodel dla miejsc<br>
        |--MainActivity.kt      //główna aktywność<br>
        |--Muzyczka.kt        //obsługa odtwarzania pliku audio w tle<br>
