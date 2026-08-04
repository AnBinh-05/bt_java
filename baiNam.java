import java.util.Scanner;

public class baiNam {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhap n nguyen duong: ");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Vui long nhap so nguyên duong n > 0.");
        } else {
            System.out.print(n + " so Fibonacci dau tien: ");

            long f0 = 1;
            long f1 = 1;

            for (int i = 1; i <= n; i++) {
                if (i == 1) {
                    System.out.print(f0 + " ");
                } else if (i == 2) {
                    System.out.print(f1 + " ");
                } else {
                    long fn = f0 + f1;
                    System.out.print(fn + " ");
                    f0 = f1;
                    f1 = fn;
                }
            }
            System.out.println();
        }

        scanner.close();
    }
}