# Online Shopping Simulation

A small Java console program that simulates an online shop's pricing and receipt logic. It models three kinds of products (regular, percentage-discounted, and fixed-promotional-price) and three kinds of users (regular, premium, and admin), then prints a receipt for each user.

This is a teaching-scale simulation built around object-oriented design. It is **not** a deployed store. There is no web interface, database, inventory, cart persistence, or payment processing, and all data is hard-coded in `OnlineShopDemo`.

## Features

- **Three product types** with their own pricing rules:
  - `RegularProduct`: sold at its base price.
  - `DiscountedProduct`: base price minus a percentage discount.
  - `FinalProduct`: sold at a fixed promotional price (and the class is `final`).
- **Three user roles** with their own receipt behavior:
  - `RegularUser`: itemized receipt and order total.
  - `PremiumUser`: the same receipt, plus loyalty points earned on the order.
  - `AdminUser`: no purchase receipt, but can view system statistics.
- **Product listing** that prints each product's details through its own `toString()`.
- **Discount lookup** that uses `instanceof` and a downcast to read discount percentages.

## Requirements

- A Java Development Kit (JDK), version 8 or newer. The code uses only standard library classes (`java.util.List`, `java.util.ArrayList`).
- Tested with JDK 24.0.2.

No build tool or external dependencies are needed.

## Build and Run

From the repository root:

```bash
javac -encoding UTF-8 -d out *.java
java -cp out OnlineShopDemo
```

`-d out` writes compiled `.class` files into `out/`, which is ignored by Git. `-encoding UTF-8` is included because the receipts print an arrow character (`→`). The flag keeps compilation consistent on systems whose default encoding isn't UTF-8.

## File Structure

```
.
├── OnlineShopDemo.java     # Entry point: builds sample data and runs the simulation
├── Product.java            # Abstract base class for all products
├── RegularProduct.java     # Product sold at base price
├── DiscountedProduct.java  # Product with a percentage discount
├── FinalProduct.java       # Final class: product with a fixed promotional price
├── User.java               # Abstract base class for all users
├── RegularUser.java        # Customer with an itemized receipt
├── PremiumUser.java        # Customer who also earns loyalty points
├── AdminUser.java          # Admin with no receipt and a final stats method
├── README.md
└── .gitignore
```

### Class Responsibilities

| Class | Responsibility |
| --- | --- |
| `Product` | Stores name, base price, and product ID. Declares the abstract `getFinalPrice()` and provides a base `toString()`. |
| `RegularProduct` | Returns the base price as the final price. |
| `DiscountedProduct` | Stores a discount percentage and applies it to the base price. Adds the discount to `toString()`. |
| `FinalProduct` | Stores a promotional price and returns it as the final price. Adds the promo price to `toString()`. Cannot be subclassed. |
| `User` | Stores user ID and username. Declares the abstract `printReceipt(List<Product>)`. |
| `RegularUser` | Prints each product's base and final price, then the order total. |
| `PremiumUser` | Prints the same receipt as `RegularUser`, then awards and tracks loyalty points. |
| `AdminUser` | Prints a notice instead of a receipt. Provides the `final` method `viewSystemStats(...)`. |
| `OnlineShopDemo` | Creates the sample products and users and prints every section of the output. |

## Pricing, Roles, and Receipts

### Product pricing

| Product type | Final price |
| --- | --- |
| `RegularProduct` | `price` |
| `DiscountedProduct` | `price - (price * discountPercentage / 100)`, rounded half-up to the nearest cent |
| `FinalProduct` | `promotionalPrice` (the base price is shown but not used) |

Example from the demo: a $999.00 iPhone with a 15% discount costs `999 - 149.85 = 849.15`. Rounding to cents keeps each receipt total equal to the sum of its printed line items. For example, 50% off $12.99 is charged as $6.50, not $6.495.

### Input validation

Product constructors throw an `IllegalArgumentException` for values that would produce an invalid price:

- **All products:** the base price must be a finite number of 0 or more.
- **`DiscountedProduct`:** the discount percentage must be between 0 and 100, inclusive.
- **`FinalProduct`:** the promotional price must be between 0 and the base price, inclusive.

### Currency formatting

All prices and totals print as dollars with exactly two decimal places (for example, `$60.00`) through the shared helper `Product.formatCurrency(double)`. It formats with `Locale.US`, so the decimal separator is always a period, whatever the system locale. Discount percentages print as plain numbers (for example, `15.0%`).

### User roles and receipts

- Each customer receipt (regular or premium) lists every product as `base price → final price` and then prints the **total of the final prices**.
- **Premium users** earn **1 loyalty point per full $10** of the order total (rounded down). For example, a $919.14 order earns 91 points. Points accumulate on the `PremiumUser` object across receipts and can be read with `getLoyaltyPoints()`.
- **Admin users** get no purchase receipt. `printReceipt` prints a notice saying so. `viewSystemStats(totalProducts, totalUsers)` prints the counts it is given.

Every receipt covers the full product list passed in. There are no quantities, taxes, shipping costs, or per-user carts.

## Object-Oriented Concepts Used

- **Abstraction:** `Product` and `User` are abstract classes. Each one defines shared state and declares one abstract method (`getFinalPrice()` and `printReceipt(...)`) that its subclasses must implement.
- **Inheritance:** All product types extend `Product`, and all user roles extend `User`. Each one reuses the shared fields and getters and calls `super(...)` in its constructor. `DiscountedProduct` and `FinalProduct` also extend the parent's `toString()` through `super.toString()`.
- **Polymorphism and late binding:** The demo stores objects in `List<Product>` and `List<User>` (upcasting). Calls such as `user.printReceipt(products)`, `product.getFinalPrice()`, and `System.out.println(product)` run the overriding method of each object's actual class at runtime.
- **Safe downcasting:** The demo checks `product instanceof DiscountedProduct` before it casts, so it can call the subclass-only method `getDiscountedPercentage()`.
- **`final` keyword:** `FinalProduct` is a `final` class, and `AdminUser.viewSystemStats(...)` is a `final` method, so neither can be extended or overridden.

## Example Output

Running `java -cp out OnlineShopDemo` prints:

```
--- Receipt for PremiumUser: alice123 ---
Product: iPhone - $999.00 → Final Price: $849.15
Product: Jeans - $60.00 → Final Price: $60.00
Product: T-Shirt - $25.00 → Final Price: $9.99
Total: $919.14
Points Earned: 91

--- Receipt for RegularUser: bob456 ---
Product: iPhone - $999.00 → Final Price: $849.15
Product: Jeans - $60.00 → Final Price: $60.00
Product: T-Shirt - $25.00 → Final Price: $9.99
Total: $919.14

--- Product Details (late binding) ---
Product ID: 1, Name: iPhone, Price: $999.00, Discount: 15.0%
Product ID: 2, Name: Jeans, Price: $60.00
Product ID: 3, Name: T-Shirt, Price: $25.00, Promo Price: $9.99

--- Special Behavior (safe downcasting) ---
iPhone has a discount of 15.0%

Admin Stats: Total Products: 3 | Total Users: 2
```

## Current Limitations

- All products and users are hard-coded in `main`. There is no user input, file, or database storage.
- Prices are stored and summed as `double`. Amounts are printed rounded to two decimal places with `Product.formatCurrency`, but the arithmetic itself is not exact decimal (`BigDecimal`) money math.
- Validation covers prices and discounts only. Product and user IDs, names, and usernames are not checked, and IDs are not required to be unique.
- There are no quantities, carts, taxes, shipping costs, or payment processing.
- Loyalty points are kept only in memory for the lifetime of the program.
- `RegularUser` and `PremiumUser` duplicate the itemized-receipt loop.
- There are no automated tests. Verification is done by running the demo and checking its output.

## Author

Yusuf Almahoozi
