# 🛒 Ecommerce Fullstack App - Juan Barragán prueba

Aplicación fullstack de ecommerce desarrollada como prueba técnica.

- **Backend:** Spring Boot + Spring Security (JWT) + JPA + H2 + JUnit + Mockito
- **Frontend:** Vue 3 + Vite + Bootstrap

---

## 📦 Estructura del proyecto

```
ecommerce/
├── ecommerce-backend/    # Backend en Spring Boot
└── ecommerce-frontend/   # Frontend en Vue 3
```

---

## 🚀 Requisitos previos

- **Java 17+**
- **Maven 3.8+**
- **Node.js 18+**
- **npm 9+**

Verificar versiones:

```bash
java -version
mvn -version
node -v
npm -v
```

---

## 📦 Levantar Backend (Spring Boot)

```bash
cd ecommerce-backend
mvn spring-boot:run
```

Acceso:

```
http://localhost:8080
```

### 📂 Base de datos H2

```
http://localhost:8080/h2-console
```

- **JDBC URL:** `jdbc:h2:file:./data/ecommerce-db`
- **Usuario:** `sa`
- **Contraseña:** *(vacía)*

Datos iniciales desde `src/main/resources/data.sql`

---

## 🎨 Levantar Frontend (Vue 3)

```bash
cd ecommerce-frontend
npm install
npm run dev
```

Acceso:

```
http://localhost:5173
```

---

## 🐳 Levantar proyecto con Docker

Desde la raíz del proyecto:

```bash
docker compose up --build
```

Accesos:

- **Backend:** `http://localhost:8080`
- **Frontend:** `http://localhost:5173`

Para detener:

```bash
docker compose down
```

---

## 📖 Endpoints disponibles

### 🔒 Autenticación (`/api/auth`)

| Método | Endpoint          | Descripción                  |
| ------ | ----------------- | ---------------------------- |
| `POST` | `/api/auth/login` | Iniciar sesión y obtener JWT |

### 👥 Usuarios (`/api/users`)

| Método   | Endpoint              | Descripción               |
| -------- | --------------------- | ------------------------- |
| `POST`   | `/api/users/register` | Registrar nuevo usuario   |
| `GET`    | `/api/users`          | Listar todos los usuarios |
| `GET`    | `/api/users/{id}`     | Consultar usuario por ID  |
| `PUT`    | `/api/users/{id}`     | Actualizar usuario por ID |
| `DELETE` | `/api/users/{id}`     | Eliminar usuario por ID   |

### 📦 Productos (`/api/products`)

| Método   | Endpoint               | Descripción                            |
| -------- | ---------------------- | -------------------------------------- |
| `GET`    | `/api/products`        | Listar todos los productos             |
| `GET`    | `/api/products/{id}`   | Consultar producto por ID              |
| `POST`   | `/api/products`        | Crear nuevo producto                   |
| `PUT`    | `/api/products/{id}`   | Actualizar producto por ID             |
| `DELETE` | `/api/products/{id}`   | Eliminar producto por ID               |
| `GET`    | `/api/products/active` | Listar productos activos               |
| `GET`    | `/api/products/search` | Buscar productos por nombre y/o estado |

### 📥 Inventarios (`/api/inventories`)

| Método   | Endpoint                | Descripción                  |
| -------- | ----------------------- | ---------------------------- |
| `GET`    | `/api/inventories`      | Listar todos los inventarios |
| `GET`    | `/api/inventories/{id}` | Consultar inventario por ID  |
| `POST`   | `/api/inventories`      | Crear nuevo inventario       |
| `PUT`    | `/api/inventories/{id}` | Actualizar inventario por ID |
| `DELETE` | `/api/inventories/{id}` | Eliminar inventario por ID   |

### 📝 Órdenes (`/api/orders`)

| Método   | Endpoint                               | Descripción                           |
| -------- | -------------------------------------- | ------------------------------------- |
| `GET`    | `/api/orders`                          | Listar todas las órdenes              |
| `GET`    | `/api/orders/{id}`                     | Consultar orden por ID                |
| `POST`   | `/api/orders/with-items`               | Crear nueva orden con items           |
| `PUT`    | `/api/orders/{id}`                     | Actualizar orden por ID               |
| `DELETE` | `/api/orders/{id}`                     | Eliminar orden por ID                 |
| `GET`    | `/api/orders/order-items?orderId={id}` | Consultar items de una orden          |
| `GET`    | `/api/orders/reports/top-products`     | Reporte: Top 5 productos más vendidos |
| `GET`    | `/api/orders/reports/top-clients`      | Reporte: Top 5 clientes frecuentes    |

---

## ✅ Integración continua (CI)

El proyecto cuenta con 2 workflows en **GitHub Actions**:

| Workflow          | Qué hace                                                                                                       | Path                                |
| ----------------- | -------------------------------------------------------------------------------------------------------------- | ----------------------------------- |
| `ci-backend.yml`  | Compila backend, ejecuta pruebas unitarias (JUnit + Mockito), genera reporte Surefire y lo sube como artefacto | `.github/workflows/ci-backend.yml`  |
| `ci-frontend.yml` | Compila frontend, ejecuta ESLint (análisis estático) y Vitest (pruebas unitarias de Vue)                       | `.github/workflows/ci-frontend.yml` |

Se ejecutan automáticamente en cada `push` o `pull request`.

## 📊 Análisis estático y reportes

### 📌 Backend

- Análisis estático: Maven Surefire con reporte JUnit XML
- Reporte: disponible desde **GitHub Actions** → workflow → *Artifacts*

### 📌 Frontend

- Análisis estático: ESLint (`npm run lint`)
- Pruebas unitarias: Vitest (`npm run test`)
- Errores y advertencias: visibles en logs del workflow CI

---

## 📚 Tecnologías utilizadas

### Backend

- Java 21
- Spring Boot 3+
- Spring Security + JWT
- Spring Data JPA
- JPA/Hibernate
- H2 Database
- JUnit 5
- Mockito

### Frontend

- Vue 3
- Vite
- Bootstrap 5
- Axios

---

## 📥 Clonar repositorio

```bash
git clone https://github.com/jbarragan123/ecommerce
cd ecommerce
```

---

## 📦 Estado de compilación

![CI Backend](https://github.com/jbarragan123/ecommerce/actions/workflows/ci-backend.yml/badge.svg)
![CI Frontend](https://github.com/jbarragan123/ecommerce/actions/workflows/ci-frontend.yml/badge.svg)

## 📌 Observaciones

- No requiere instalación previa de bases de datos externas.
- El proyecto incluye datos de prueba precargados mediante `data.sql`

---

## 🎞️ Video demostrativo

En el video adjunto se explica:

- Flujo de autenticación con JWT.
- Consumo de endpoints desde Postman.
- Interacción desde frontend.
- Proceso de login y uso de token.
- Especificación de funcionalidades de la prueba técnica.

