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
|--build.gradle.kts &emsp;&emsp;&emsp;//_dołączanie bibliotek, pluginów, wersji itp._<br>
|--**/main**<br>
&emsp;|----AndroidManifest.xml   &emsp;&emsp;&emsp;   //_plik manifestu_<br>
    &emsp;|----**/res**<br>
    &emsp;|&emsp;&emsp;|----**/drawable** &emsp;&emsp;&emsp;     //_ikony i obrazki_<br>
    &emsp;|&emsp;&emsp;|----**/mipmap**  &emsp;&emsp;&emsp;    //_ikony launchera_<br>
    &emsp;|&emsp;&emsp;|----**/raw**   &emsp;&emsp;&emsp;   //_audio i wideo_<br>
    &emsp;|&emsp;&emsp;|----**/values** &emsp;&emsp;&emsp;     //_zmienne string_<br>
    &emsp;|&emsp;&emsp;|----**/values-en**    &emsp;&emsp;&emsp;  //_zmienne string po angielsku_<br>
    &emsp;|&emsp;&emsp;|----**/xml**<br>
    &emsp;|----**/java/com/example/am_projekt**<br>
          &emsp;|&emsp;&emsp;|----**/data**   &emsp;&emsp;&emsp;  //_plik datastoreutils.kt - obsługuje zapis ustawień i preferencji_<br>
         &emsp;|&emsp;&emsp;|----**/database**  &emsp;&emsp;&emsp;   //_zawiera plik obsługujący bazę danych oraz DAO (framework room)_<br>
         &emsp;|&emsp;&emsp;|----**/model**    &emsp;&emsp;&emsp;  //_zawiera model obiektu "Place"_<br>
         &emsp;|&emsp;&emsp;|----**/ui**<br>
         &emsp;|&emsp;&emsp;|&emsp;&emsp;|----**/screens** &emsp;&emsp;&emsp;     //_zawiera definicje UI dla wszystkich ekranów_<br>
         &emsp;|&emsp;&emsp;|&emsp;&emsp;|----**/theme**  &emsp;&emsp;&emsp;    //_zawiera pomocniczen wartości wykorzystywane do tworzenia UI_<br>
         &emsp;|&emsp;&emsp;|----**/utils**   &emsp;&emsp;&emsp;   //_zawiera LocaleUtils.kt wykrozystywane do obsługi preferencji związanych z lokalizacją_<br>
         &emsp;|&emsp;&emsp;|----**/viewmodels**   &emsp;&emsp;&emsp;   //_zawiera viewmodel dla miejsc_<br>
        &emsp;|----MainActivity.kt   &emsp;&emsp;&emsp;   //_główna aktywność_<br>
        &emsp;|----Muzyczka.kt     &emsp;&emsp;&emsp;   //_obsługa odtwarzania pliku audio w tle_<br>
