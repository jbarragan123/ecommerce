
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

Tener instaladas las siguientes herramientas en su equipo:

- **Java 17+**
- **Maven 3.8+**
- **Node.js 18+**
- **npm 9+**

Verificar versiones ejecutando:

```bash
java -version
mvn -version
node -v
npm -v
```

---

## 🖥️ Instrucciones para levantar el proyecto desde cero

---

## 📦 Levantar Backend (Spring Boot)

1️⃣ Entrar a la carpeta del backend:

```bash
cd ecommerce-backend
```

2️⃣ Levantar la aplicación:

```bash
mvn spring-boot:run
```

Esto iniciará el backend en:

```
http://localhost:8080
```

---

### 📂 Base de datos H2

Se utiliza una base de datos embebida **H2** con persistencia en disco local.

Para acceder al panel de administración:

```
http://localhost:8080/h2-console
```

**Configuración de conexión:**

- **JDBC URL:** `jdbc:h2:file:./data/ecommerce-db`
- **Usuario:** `sa`
- **Contraseña:** *(vacía)*

⚠️ Los datos iniciales se cargan automáticamente desde el archivo `src/main/resources/data.sql` al iniciar la aplicación.

---

## 🎨 Levantar Frontend (Vue 3)

1️⃣ Entrar a la carpeta del frontend:

```bash
cd ecommerce-frontend
```

2️⃣ Instalar las dependencias:

```bash
npm install
```

3️⃣ Levantar el servidor de desarrollo:

```bash
npm run dev
```

Esto iniciará el frontend en:

```
http://localhost:5173
```

---

## 📖 Endpoints disponibles

Todos los endpoints excepto login y register requieren autenticación mediante JWT.

### 🔒 Autenticación (`/api/auth`)

| Método | Endpoint           | Descripción                |
|:--------|:--------------------|:---------------------------|
| `POST` | `/api/auth/login`  | Iniciar sesión y obtener JWT |

---

### 👥 Usuarios (`/api/users`)

| Método | Endpoint               | Descripción                     |
|:--------|:------------------------|:--------------------------------|
| `POST` | `/api/users/register`   | Registrar nuevo usuario         |
| `GET`  | `/api/users`            | Listar todos los usuarios       |
| `GET`  | `/api/users/{id}`       | Consultar usuario por ID        |
| `PUT`  | `/api/users/{id}`       | Actualizar usuario por ID       |
| `DELETE` | `/api/users/{id}`     | Eliminar usuario por ID         |

---

### 📦 Productos (`/api/products`)

| Método | Endpoint                  | Descripción                          |
|:--------|:-----------------------------|:--------------------------------------|
| `GET`  | `/api/products`             | Listar todos los productos            |
| `GET`  | `/api/products/{id}`        | Consultar producto por ID             |
| `POST` | `/api/products`             | Crear nuevo producto                  |
| `PUT`  | `/api/products/{id}`        | Actualizar producto por ID            |
| `DELETE` | `/api/products/{id}`      | Eliminar producto por ID              |
| `GET`  | `/api/products/active`      | Listar productos activos              |
| `GET`  | `/api/products/search`      | Buscar productos por nombre y/o estado |

---

### 📥 Inventarios (`/api/inventories`)

| Método | Endpoint                     | Descripción                       |
|:--------|:--------------------------------|:----------------------------------|
| `GET`  | `/api/inventories`              | Listar todos los inventarios      |
| `GET`  | `/api/inventories/{id}`         | Consultar inventario por ID       |
| `POST` | `/api/inventories`              | Crear nuevo inventario            |
| `PUT`  | `/api/inventories/{id}`         | Actualizar inventario por ID      |
| `DELETE` | `/api/inventories/{id}`       | Eliminar inventario por ID        |

---

### 📝 Órdenes (`/api/orders`)

| Método | Endpoint                              | Descripción                                    |
|:--------|:-------------------------------------------|:------------------------------------------------|
| `GET`  | `/api/orders`                             | Listar todas las órdenes                        |
| `GET`  | `/api/orders/{id}`                        | Consultar orden por ID                          |
| `POST` | `/api/orders/with-items`                  | Crear nueva orden con items                     |
| `PUT`  | `/api/orders/{id}`                        | Actualizar orden por ID                         |
| `DELETE` | `/api/orders/{id}`                      | Eliminar orden por ID                           |
| `GET`  | `/api/orders/order-items?orderId={id}`    | Consultar items de una orden                    |
| `GET`  | `/api/orders/reports/top-products`        | Reporte: Top 5 productos más vendidos           |
| `GET`  | `/api/orders/reports/top-clients`         | Reporte: Top 5 clientes frecuentes              |

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

Si desea clonar este proyecto y ejecutarlo localmente:

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
- El proyecto incluye datos de prueba precargados mediante `data.sql`.

---

## 🎥 Video demostrativo

En el video adjunto a este proyecto se explicará:

- Flujo de autenticación con JWT.
- Consumo de endpoints desde Postman.
- Interacción completa desde frontend.
- Proceso de login y uso de token.
- Específicación funcionalidades específicas requeridas en prueba técnica
