draft domain model in:
![forest](images/domain1.png)

# Keep Dishes Going

A fine-dining marketplace platform where **restaurants manage their menus** and **customers explore and order dishes**.  
The project follows a **Hexagonal Architecture** for the backend (Spring Boot) and a **modern React + TypeScript + MUI** frontend.

---

## Project Overview

### For Restaurant Owners
- Manage your restaurant’s menu.
- Edit dishes as drafts.
- Publish or unpublish dishes.
- Enforce fine-dining limits: **maximum 10 published dishes**.

### For Customers
- Browse restaurants and explore their dishes.
- View dish details and images.
- (Later) Add dishes to a basket and place orders.

---

## Architecture

### Backend — Hexagonal Architecture (Spring Boot)

**Layers:**
- **Domain layer** — core entities (`Dish`, `Menu`, `Restaurant`, `DishType`, `FoodTag`, etc.).
- **Application layer** — use cases (business logic):
    - `EditDishUseCase`
    - `PublishDishUseCase`
    - `LoadDishByRestaurantUseCase`
- **Infrastructure layer** — JPA repositories and REST controllers.
- **Adapters layer** — persistence via `DishJpaAdapter`, `MenuJpaAdapter`, and REST exposure via `DishController`.

---

##  Backend Implementation Details

### Key Features
- **Dish Management**
    - Edit draft dishes via:
      ```http
      PUT /restaurants/{restaurantId}/dishes/{dishId}
      ```
    - Publish dishes (enforces the 10-published limit):
      ```http
      POST /restaurants/{restaurantId}/dishes/{dishId}/publish
      ```

- **Menu Aggregate Root**
    - Controls publishing logic:
      ```java
      if (publishedCount >= MAX_PUBLISHED_DISHES) {
          throw new IllegalStateException("Cannot publish more than 10 dishes.");
      }
      ```

- **Filtering Logic**
    - Only `PUBLISHED` dishes are returned to customer-facing endpoints.

- **Mapping**
    - Entity ↔ D
