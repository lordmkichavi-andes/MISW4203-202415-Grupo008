# 🎵 **Proyecto Vinilos - Aplicación Móvil**

Bienvenido al repositorio del proyecto Vinilos, una aplicación móvil para gestionar álbumes de vinilos y artistas. Este documento proporciona una guía de cómo navegar por la documentación del proyecto, junto con los enlaces a las diferentes páginas de la Wiki.

## 📚 **Índice**
1. ### 🏁 [Inception - Fase Inicial](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki)
   - Detalles sobre la planificación inicial del proyecto, backlog y decisiones iniciales de diseño UX/UI.

2. ### 🚀 [Sprint 1](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/%F0%9F%9A%80-Sprint-1)
   - Implementación de las siguientes historias de usuario:
     - **HU01**: Consultar catálogo de álbumes
     - **HU07**: Crear un álbum

3. ### 🚀 [Sprint 2](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/%F0%9F%9A%80-Sprint-2)
   - Implementación de las siguientes historias de usuario:
     - **HU02**: Consultar la información detallada del álbum
     - **HU04**: Asociar tracks con un álbum
     - **HU03**: Consultar listado de artistas

4. ### 🚀 [Sprint 3](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/%F0%9F%9A%80-Sprint-3)
   - Implementación de las siguientes historias de usuario:
     - **HU03**: Consultar el listado de artistas
     - **HU06**: Consultar la información detallada de coleccionista
     - **HU08**: Asociar tracks con un álbum

5. ### 🎨 [Diseño de UX/UI](https://www.figma.com/design/LHPjiMR6PoKYuAWbi2r7eM/Vinilos?node-id=0-1&node-type=canvas&t=hQAK8jVM35NhaM17-0)
   - Documentación sobre la interfaz de usuario.

6. ### 📝 [Evidencias](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/%F0%9F%93%9D-Evidencias-y-Reuniones)
   - Registro de las reuniones del equipo, actas y bitácoras de las sesiones semanales.
  
7. ### [Documento de arquitectura](https://github.com/lordmkichavi-andes/MISW4203-202415-Grupo008/wiki/Arquitectura-del-sistema)
   - Documentación arquitectura del software.

---

## 📋 **Backlog de Historias de Usuario**

El proyecto está centrado en las siguientes historias de usuario:

| **ID**  | **Descripción** | **Sprint Asignado** |
|:-------:|:----------------|:-------------------:|
| **[HU01](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/HU01-%E2%80%90-Consultar-cat%C3%A1logo-de-%C3%A1lbumes)** | Crear álbum | Sprint 1 |
| **[HU02](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/HU02-%E2%80%90-Consultar-la-informaci%C3%B3n-detallada-del-%C3%A1lbum)** | Consultar la información detallada del álbum | Sprint 1 |
| **[HU03](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/HU03-%E2%80%90-Consultar-listado-de-artistas)** | Consultar catálogo de álbumes | Sprint 1 |
| **[HU04](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/HU04-%E2%80%90-Consultar-la-informaci%C3%B3n-detallada-del-artista)** | Asociar tracks con un álbum | Sprint 2 |
| **[HU05](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/HU05-%E2%80%90-Consultar-el-listado-de-Coleccionistas)** | Comentar álbum | Sprint 2 |
| **[HU06](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/HU06-%E2%80%90-Consultar-la-informaci%C3%B3n-detallada-del-coleccionista)** | Agregar álbum a coleccionista | Sprint 3 |
| **[HU07](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/HU07-%E2%80%90-Crear-alb%C3%BAm)** | Consultar la información detallada de un artista | Sprint 3 |
| **[HU08](https://github.com/lordmkichavi-andes/MISW4203-202415-GrupoXYZ/wiki/HU08-%E2%80%90-Asociar-tracks-con-un-%C3%A1lbum)** | Consultar el listado de artistas | Sprint 3 |

---

## 💻 **Instrucciones para Configuración Local**

### 📋 Requisitos del Entorno

Para compilar y ejecutar esta aplicación de manera local, asegúrate de tener instalado:

- **🛠️ Android Studio**: versión recomendada 4.2 o superior.  
  - **Versión usada**: 2024.1.2 (Koala Feature Drop)

- **☕ Java Development Kit (JDK)**: versión 11 o superior.  
  - **Versión usada**: 23.0.1

- **⚙️ Gradle**: El proyecto incluye un archivo `gradle-wrapper`, por lo que no es necesario instalar Gradle manualmente.

### 📝 Configuración Inicial

1. **Clona el repositorio**:
   - Abre la terminal y usa:
     ```bash
     git clone https://github.com/lordmkichavi-andes/MISW4203-202415-Grupo008.git
     cd MISW4203-202415-Grupo008
     ```

2. **Abre el proyecto en Android Studio**:
   - En **Android Studio**, selecciona **Open an existing project** y navega hasta el directorio del proyecto.

3. **Sincroniza las dependencias**:
   - Android Studio debería detectar automáticamente el archivo `build.gradle` y comenzar la sincronización.
   - Si hay errores de sincronización, verifica la compatibilidad de Gradle con tu versión de Android Studio.

### ▶️ Compilar y Ejecutar la Aplicación

1. **Configura un dispositivo de prueba**:
   - Usa un emulador de Android o un dispositivo físico conectado.

2. **Ejecuta la aplicación**:
   - En Android Studio, utiliza el botón de **Run** o presiona `Shift + F10` para compilar y desplegar la aplicación en tu dispositivo.

---

## 🔍 **Pruebas Automatizadas - Ejecución de Pruebas E2E**

El proyecto incluye pruebas de extremo a extremo (E2E) para verificar las funcionalidades clave de la aplicación. Estas pruebas simulan la interacción del usuario en dos flujos principales.

### 📂 Clases de Prueba

#### 1️⃣ **AddAlbumScreenE2E**
   - Valida la funcionalidad de la pantalla para agregar un álbum en el perfil "Coleccionista".
   - **Métodos**:
     - `testAddAlbumSuccessfullyAsCollector`: Verifica que un coleccionista puede agregar un álbum con todos los datos completos.
     - `testAlbumAdditionFailsWithIncompleteFields`: Asegura que el botón "Agregar" esté deshabilitado si faltan datos.

#### 2️⃣ **GetAlbumCatalogE2E**
   - Evalúa la visualización del catálogo de álbumes para el perfil "Visitante".
   - **Métodos**:
     - `testAlbumCatalogIsDisplayedForVisitorProfile`: Verifica que el catálogo se muestra correctamente.
     - `testAlbumLoadingMessageIsDisplayed`: Confirma que aparece un mensaje de carga mientras se obtienen los datos.

#### 3️⃣ **GetArtistsScreenE2E**
   - Evalúa la visualización del listado de artistas para ambos perfiles (“Coleccionista” y “Visitante”). 
   - **Métodos**:
     - `testAlbumCatalogIsDisplayedForVisitorProfile`: Verifica que el listado de artistas se muestra correctamente.
     - `testAlbumCatalogIsDisplayedForCollectorProfile`: Verifica que el catálogo se muestra correctamente para el perfil coleccionista.

#### 4️⃣ **GetAlbumScreenE2E**
   - Evalúa la visualización del detalle de álbum para ambos perfiles.
   - **Métodos**:
     - `testAddTrackButtonIsDisplayedForCollectorProfile`: Verifica que el detalle del álbum se muestre correctamente para el perfil coleccionista.
     - `testAddTrackButtonIsNotDisplayedForUserProfile`: Verifica que el detalle del álbum se muestre correctamente para el perfil del usuario.

#### 5️⃣ **ArtistDetailScreenE2E**
   - Evalúa la visualización del detalle de artista para ambos perfiles.
   - **Métodos**:
     - `testAlbumCatalogIsDisplayedForCollectorProfile`: Verifica que el detalle del artista se muestre correctamente para el perfil coleccionista.
     - `testAlbumCatalogIsDisplayedForVisitorProfile`: Verifica que el detalle del artista se muestre correctamente para el perfil del usuario.

### 🚀 Ejecución de Pruebas

#### 🔹 Desde Android Studio:
   - Abre el archivo de prueba (`AddAlbumScreenE2E.kt`, `GetAlbumCatalogE2ETest.kt`, `GetArtistsScreenE2ETest.kt`, `GetAlbumScreenE2E.kt` o `ArtistDetailScreenE2E.kt`).
   - Haz clic derecho en el método que deseas ejecutar y selecciona **Run 'NombreDeLaPrueba'**.

#### 🔹 Desde la Terminal:
   - Ejecuta todas las pruebas de una clase completa con los siguientes comandos:

     - Para **AddAlbumScreenE2E**:
       ```bash
       ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class="co.edu.uniandes.vinilos.AddAlbumScreenE2E"
       ```

     - Para **GetAlbumCatalogE2ETest**:
       ```bash
       ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class="co.edu.uniandes.vinilos.GetAlbumCatalogE2ETest"
       ```

     - Para **GetArtistsScreenE2ETest**:
       ```bash
       ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class="co.edu.uniandes.vinilos.GetArtistsScreenE2ETest"
       ```

     - Para **GetAlbumScreenE2E**:
       ```bash
       ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class="co.edu.uniandes.vinilos.GetAlbumScreenE2E"
       ```

     - Para **ArtistDetailScreenE2E**:
       ```bash
       ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class="co.edu.uniandes.vinilos.ArtistDetailScreenE2E"
       ```

> **ℹ️ Nota**: Los resultados de cada prueba, incluyendo el tiempo de ejecución en milisegundos, aparecerán en la consola de Android Studio o en la terminal para facilitar el análisis de rendimiento.

---
