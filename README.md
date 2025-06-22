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

Finalna wersja aplikacji znajduje się w katalogu "wersja finalna"

/wersja finalna
|---/main
    |--AndroidManifest.xml      //plik manifestu
    |--/res
    |   |--/drawable      //ikony i obrazki
    |   |--/mipmap      //ikony launchera
    |   |--/raw      //audio i wideo
    |   |--/values      //zmienne string
    |   |--/values-en      //zmienne string po angielsku
    |   |--/xml
    |--/java/com/example/am_projekt
        |--/data      //plik datastoreutils.kt - obsługuje zapis ustawień i preferencji
        |--/database      //zawiera plik obsługujący bazę danych oraz DAO (framework room)
        |--/model      //zawiera model obiektu "Place"
        |--/ui
        |  |--/screens      //zawiera definicje UI dla wszystkich ekranów
        |  |--/theme      //zawiera pomocniczen wartości wykorzystywane do tworzenia UI
        |--/utils      //zawiera LocaleUtils.kt wykrozystywane do obsługi preferencji związanych z lokalizacją
        |--/viewmodels      //zawiera viewmodel dla miejsc
        |--MainActivity.kt      //główna aktywność
        |--Muzyczka.kt        //obsługa odtwarzania pliku audio w tle
