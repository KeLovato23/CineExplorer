# CineExplorer

Aplicación Android nativa para explorar series de TV, desarrollada como prueba técnica. Consume la API pública de [TVMaze](https://www.tvmaze.com/api) con persistencia local, favoritos, modo offline y animaciones cinematográficas.

📦 **[Descargar APK](apk/CineExplorer-v1.0.0.apk)**

---

## Pantallas

<table>
  <tr>
    <td align="center"><b>Ícono</b></td>
    <td align="center"><b>Splash</b></td>
    <td align="center"><b>Home</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/0e4791c9-377a-46f4-9f1a-52f679042b0a" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/fb7062d9-3a8e-490f-a47d-fcde7ee4ab4d" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/b1d83ca0-074f-422d-95a7-d1d84209ce0c" width="220"/></td>
  </tr>
  <tr>
    <td align="center"><b>Catálogo</b></td>
    <td align="center"><b>Loading</b></td>
    <td align="center"><b>Detalle — Episodios</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/dec8d16c-443a-4662-b8b0-c38f0a37ea73" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/f3970473-c932-44d6-bcee-ae6c4952a72d" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/afa78cc4-9f7c-4fde-8ca5-e110d4ea82bd" width="220"/></td>
  </tr>
  <tr>
    <td align="center"><b>Detalle — Elenco y Sinopsis</b></td>
    <td align="center"><b>Favoritos</b></td>
    <td align="center"><b>Dispositivo</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/cbf31d28-6875-41b9-9516-cefc42d6669f" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/661aa39b-a3cc-4f92-9029-5fe71d68c8aa" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/20efbaa6-0582-4709-9a19-e3ec53358bde" width="220"/></td>
  </tr>
</table>

---

## Tech Stack

| Categoría | Tecnología |
|---|---|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Arquitectura | Clean Architecture + MVVM |
| DI | Hilt 2.52 |
| Persistencia | Room 2.6.1 |
| Red | Retrofit 2.11.0 + OkHttp 4.12.0 |
| Imágenes | Coil 2.7.0 |
| Navegación | Navigation Compose 2.7.7 |
| Async | Coroutines + StateFlow |
| Tests | JUnit4 + MockK + kotlinx-coroutines-test |

---

## Arquitectura

```
app/
├── data/
│   ├── local/          # Room (AppDatabase, ShowDao, ShowEntity)
│   ├── remote/         # Retrofit (TvMazeApi, DTOs)
│   ├── mapper/         # DTO → Domain mappers
│   └── repository/     # ShowRepositoryImpl
├── domain/
│   ├── model/          # Show, CastMember, Episode
│   ├── repository/     # ShowRepository (interface)
│   └── usecase/        # GetShowsUseCase, GetCastUseCase, etc.
├── presentation/
│   ├── home/           # HomeScreen + HomeViewModel
│   ├── detail/         # DetailScreen + DetailViewModel
│   ├── favorites/      # FavoritesScreen + FavoritesViewModel
│   ├── device/         # DeviceScreen + DeviceViewModel
│   ├── splash/         # SplashScreen
│   ├── navigation/     # NavGraph + Screen
│   └── components/     # FeaturedBanner, PosterCard, ShowCard...
└── connectivity/       # ConnectivityObserver
```

---

## Funcionalidades

- **Listado paginado** de series desde TVMaze con banner destacado y filtro por género
- **Detalle** con hero image, rating, episodios por temporada y elenco con fotos circulares
- **Favoritos** persistidos en Room, no se pierden al refrescar desde red
- **Modo offline** con `ConnectivityObserver` (API nativa `NetworkCallback` + `callbackFlow`) y banner animado
- **Pull-to-refresh** sin resetear al estado Loading cuando ya hay datos en caché
- **Sinopsis expandible** con "Ver más / Ver menos" y animación suave
- **Splash cinematográfico** con efecto zoom estilo Cinemax usando `Animatable`
- **Dark mode** completo con `isSystemInDarkTheme()` y paleta de cine personalizada
- **Navegación floating pill** animada superpuesta sobre el contenido
- **Info del dispositivo** — fabricante, modelo, versión Android, batería con estado de carga

---

## Animaciones

- `Animatable` — zoom del splash (escala `1.35f → 1f → 1.1f`)
- `animateFloatAsState` + `spring` — botón favorito con rebote
- `animateColorAsState` — indicador de nav bar y tint del favorito
- `animateContentSize` — expansión de sinopsis
- `Crossfade` — transición entre estados Loading / Success / Error
- `AnimatedVisibility` — banner de modo offline
- `fadeIn + slideInVertically` con delay en cascada — lista de favoritos

---

## API

Base URL: `https://api.tvmaze.com/`

| Endpoint | Uso |
|---|---|
| `GET /shows?page=0` | Listado paginado |
| `GET /shows/:id` | Detalle de show |
| `GET /shows/:id/cast` | Elenco |
| `GET /shows/:id/episodes` | Episodios por temporada |

No requiere API key.

---

## Tests

```bash
# Unit tests (sin dispositivo)
./gradlew test

# Instrumented tests (requiere emulador)
./gradlew connectedAndroidTest
```

| Archivo | Tipo | Cobertura |
|---|---|---|
| `HomeViewModelTest` | Unit (JVM) | Estados Loading, Success, Error, isRefreshing |
| `DetailViewModelTest` | Unit (JVM) | Carga paralela, toggleFavorite, fallos parciales |
| `ShowDaoTest` | Instrumented | CRUD, favoritos, upsert, preservación |
| `ShowMapperTest` | Instrumented | DTOs → Domain, stripHtml, nulls |

---

## Cómo correr el proyecto

1. Clonar el repositorio
2. Abrir en Android Studio Hedgehog o superior
3. Correr en un dispositivo o emulador con **API 24+**
4. No se requiere ninguna configuración adicional (la API es pública)
