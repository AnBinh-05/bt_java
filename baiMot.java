import java.util.Scanner;

public class baiMot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        int sum = 0;

        if (n % 2 != 0) {
            n--;
        }

        for (int i = 2; i <= n; i += 2) {
            sum += i;
        }

        System.out.println("Tong cac so chan = " + sum);

        sc.close();
    }
}