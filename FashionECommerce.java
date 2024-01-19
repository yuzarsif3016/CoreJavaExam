import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

enum Category {
    MENS_TSHIRT, MENS_SHIRTS, WOMENS_JEANS
}

enum Size {
    S, M, L, XL
}

class Cloth {
    private static int idCounter = 1;
    private int id;
    private Category category;
    private int stock;
    private Date stockUpdateDate;
    private Size size;
    private double price;
    private String brand;
    private String color;
    private double discount;

    public Cloth(Category category, int stock, Size size, double price, String brand, String color) {
        this.id = idCounter++;
        this.category = category;
        this.stock = stock;
        this.stockUpdateDate = new Date();
        this.size = size;
        this.price = price;
        this.brand = brand;
        this.color = color;
        this.discount = 0.0;
    }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        this.stockUpdateDate = new Date();
    }

    public Date getStockUpdateDate() {
        return stockUpdateDate;
    }

    public Size getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Category: " + category +
                ", Stock: " + stock +
                ", Stock Update Date: " + stockUpdateDate +
                ", Size: " + size +
                ", Price: " + price +
                ", Brand: " + brand +
                ", Color: " + color +
                ", Discount: " + discount;
    }
}

class FashionECommerce {
    private List<Cloth> clothes;

    public FashionECommerce() {
        clothes = new ArrayList<>();
    }

    public void addCloth() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Category (MENS_TSHIRT, MENS_SHIRTS, WOMENS_JEANS): ");
        Category category = Category.valueOf(scanner.nextLine());
        System.out.print("Enter Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Size (S, M, L, XL): ");
        Size size = Size.valueOf(scanner.nextLine());
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter Color: ");
        String color = scanner.nextLine();

        Cloth cloth = new Cloth(category, stock, size, price, brand, color);
        clothes.add(cloth);

        System.out.println("Cloth added successfully!");
    }

    public void updateStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Cloth ID to Update Stock: ");
        int clothId = scanner.nextInt();

        Cloth cloth = getClothById(clothId);
        if (cloth == null) {
            System.out.println("Cloth not found!");
            return;
        }

        System.out.print("Enter New Stock: ");
        int newStock = scanner.nextInt();
        scanner.nextLine();

        cloth.setStock(newStock);

        System.out.println("Stock updated successfully!");
    }

    public void setDiscountForCategoryAndBrand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Category (MENS_TSHIRT, MENS_SHIRTS, WOMENS_JEANS): ");
        Category category = Category.valueOf(scanner.nextLine());
        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter Discount (%): ");
        double discount = scanner.nextDouble();
        scanner.nextLine();

        for (Cloth cloth : clothes) {
            if (cloth.getCategory() == category && cloth.getBrand().equals(brand)) {
                cloth.setDiscount(discount);
            }
        }

        System.out.println("Discount set successfully for all clothes of given Category and Brand!");
    }

    public void removeOutofStockClothes() {
        Date currentDate = new Date();
        for (int i = clothes.size() - 1; i >= 0; i--) {
            Cloth cloth = clothes.get(i);
            long diffInMillies = Math.abs(currentDate.getTime() - cloth.getStockUpdateDate().getTime());
            long diffInMonths = diffInMillies / 1000 / 60 / 60 / 24 / 30; // Assuming 30 days per month
            if (diffInMonths >= 3 && cloth.getStock() == 0) {
                clothes.remove(i);
            }
        }

        System.out.println("Out of stock clothes removed successfully!");
    }

    public void listOutofStockClothes() {
        System.out.println("Out of stock clothes today:");
        for (Cloth cloth : clothes) {
            if (cloth.getStock() == 0) {
                System.out.println(cloth);
            }
        }
    }

    private Cloth getClothById(int clothId) {
        for (Cloth cloth : clothes) {
            if (cloth.getId() == clothId) {
                return cloth;
            }
        }
        return null;
    }

    public void printMenu() {
        System.out.println();
        System.out.println("Fashion E-commerce Company - Site Admin");
        System.out.println("1. Add new Cloth");
        System.out.println("2. Update stock of a Cloth");
        System.out.println("3. Set discount (in %) for all the clothes of given Category and Brand");
        System.out.println("4. Remove Clothes which are out of stock for last 3 months");
        System.out.println("5. List out Clothes which are out of stock today");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void main(String[] args) {
        FashionECommerce fashionECommerce = new FashionECommerce();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            fashionECommerce.printMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    fashionECommerce.addCloth();
                    break;
                case 2:
                    fashionECommerce.updateStock();
                    break;
                case 3:
                    fashionECommerce.setDiscountForCategoryAndBrand();
                    break;
                case 4:
                    fashionECommerce.removeOutofStockClothes();
                    break;
                case 5:
                    fashionECommerce.listOutofStockClothes();
                    break;
                case 0:
                    System.out.println("Thank you for using the Fashion E-commerce program!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }
}
