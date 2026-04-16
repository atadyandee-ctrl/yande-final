package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class BankingApp {
    private BankingApp() {}

    public static void main(String[] args) {
        Map<String, Double> balances = new HashMap<>();
        Map<String, List<String>> history = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Simple Banking System");

        while (true) {
            System.out.println();
            System.out.println("1) Create account");
            System.out.println("2) Check balance");
            System.out.println("3) Deposit");
            System.out.println("4) Withdraw");
            System.out.println("5) Transaction history");
            System.out.println("6) Delete account");
            System.out.println("7) Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Account id: ");
                    String id = scanner.nextLine().trim();
                    if (id.isEmpty()) {
                        System.out.println("Id cannot be empty.");
                    } else if (balances.containsKey(id)) {
                        System.out.println("Account already exists.");
                    } else {
                        balances.put(id, 0.0);
                        history.put(id, new ArrayList<>());
                        System.out.println("Account created.");
                    }
                }
                case "2" -> {
                    System.out.print("Account id: ");
                    String id = scanner.nextLine().trim();
                    if (!balances.containsKey(id)) {
                        System.out.println("Account not found.");
                    } else {
                        System.out.printf("Balance: ₱%.2f%n", balances.get(id));
                    }
                }
                case "3" -> {
                    System.out.print("Account id: ");
                    String id = scanner.nextLine().trim();
                    if (!balances.containsKey(id)) {
                        System.out.println("Account not found.");
                        continue;
                    }
                    double amount = readAmount(scanner);
                    if (amount <= 0) {
                        System.out.println("Invalid amount.");
                        continue;
                    }
                    double newBalance = balances.get(id) + amount;
                    balances.put(id, newBalance);
                    history.get(id).add(String.format("DEPOSIT ₱%.2f | balance: ₱%.2f", amount, newBalance));
                    System.out.println("Deposited.");
                }
                case "4" -> {
                    System.out.print("Account id: ");
                    String id = scanner.nextLine().trim();
                    if (!balances.containsKey(id)) {
                        System.out.println("Account not found.");
                        continue;
                    }
                    double amount = readAmount(scanner);
                    double current = balances.get(id);
                    if (amount <= 0 || amount > current) {
                        System.out.println("Withdraw failed.");
                        continue;
                    }
                    double newBalance = current - amount;
                    balances.put(id, newBalance);
                    history.get(id).add(String.format("WITHDRAW ₱%.2f | balance: ₱%.2f", amount, newBalance));
                    System.out.println("Withdrawn.");
                }
                case "5" -> {
                    System.out.print("Account id: ");
                    String id = scanner.nextLine().trim();
                    if (!history.containsKey(id)) {
                        System.out.println("Account not found.");
                        continue;
                    }
                    List<String> log = history.get(id);
                    if (log.isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        System.out.println("--- History ---");
                        for (String row : log) {
                            System.out.println(row);
                        }
                    }
                }
                case "6" -> {
                    System.out.print("Account id: ");
                    String id = scanner.nextLine().trim();
                    if (balances.remove(id) == null) {
                        System.out.println("Account not found.");
                    } else {
                        history.remove(id);
                        System.out.println("Account deleted.");
                    }
                }
                case "7", "q" -> {
                    scanner.close();
                    System.out.println("Goodbye.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static double readAmount(Scanner scanner) {
        System.out.print("Amount: ");
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
