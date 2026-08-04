import java.util.Scanner;

public class baiBon {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhap canh a: ");
        double a = scanner.nextDouble();
        System.out.print("Nhap canh b: ");
        double b = scanner.nextDouble();
        System.out.print("Nhap canh c: ");
        double c = scanner.nextDouble();


        if (a > 0 && b > 0 && c > 0 && (a + b > c) && (a + c > b) && (b + c > a)) {
            System.out.println("Ba so a, b, c Tao thanh mot tam giac.");

            double eps = 1e-9;
            boolean isVuongAtA = Math.abs(b * b + c * c - a * a) < eps;
            boolean isVuongAtB = Math.abs(a * a + c * c - b * b) < eps;
            boolean isVuongAtC = Math.abs(a * a + b * b - c * c) < eps;
            boolean isVuong = isVuongAtA || isVuongAtB || isVuongAtC;

            if (a == b && b == c) {
                System.out.println("Loai tam giac: Tam giac DEU");
            } else if (isVuong && (a == b || b == c || a == c)) {
                System.out.println("Loai tam giac: Tam giac VUONG CAN");
            } else if (isVuong) {
                System.out.println("Loai tam giac: Tam giac VUONG");
            } else if (a == b || b == c || a == c) {
                System.out.println("Loai tam giac: Tam giac CAN");
            } else {
                System.out.println("Loai tam giac: Tam giac THUONG");
            }
        } else {
            System.out.println("Ba so a, b, c KHONG PHAI la 3 canh cua mot tam giac.");
        }

        scanner.close();
    }
}