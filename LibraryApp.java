import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class LibraryApp {
    private final Map<String, Book> books = new LinkedHashMap<>();
    private final Map<String, String> members = new LinkedHashMap<>();

    private static class Book {
        final String title;
        String borrowerId;

        Book(String title) {
            this.title = title;
        }
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            new LibraryApp().run(input);
        }
    }

    private void run(Scanner input) {
        System.out.println("Library Management");
        System.out.println("Use | between fields. IDs are case-sensitive.");
        System.out.println("add | book ID | title");
        System.out.println("register | member ID | name");
        System.out.println("borrow | book ID | member ID");
        System.out.println("return | book ID | member ID");
        System.out.println("list");
        System.out.println("exit");

        while (true) {
            System.out.print("> ");
            if (!input.hasNextLine()) {
                return;
            }
            String[] fields = input.nextLine().split("\\|", -1);
            for (int i = 0; i < fields.length; i++) {
                fields[i] = fields[i].trim();
            }
            switch (fields[0]) {
                case "add":
                    if (hasFields(fields, 3)) {
                        addBook(fields[1], fields[2]);
                    }
                    break;
                case "register":
                    if (hasFields(fields, 3)) {
                        register(fields[1], fields[2]);
                    }
                    break;
                case "borrow":
                case "return":
                    if (hasFields(fields, 3)) {
                        updateLoan(fields[1], fields[2], fields[0].equals("borrow"));
                    }
                    break;
                case "list":
                    if (hasFields(fields, 1)) {
                        listBooks();
                    }
                    break;
                case "exit":
                    if (hasFields(fields, 1)) {
                        System.out.println("Goodbye.");
                        return;
                    }
                    break;
                default:
                    System.out.println("Unknown command. Use add, register, borrow, return, list or exit.");
            }
        }
    }

    private static boolean hasFields(String[] fields, int count) {
        if (fields.length == count) {
            for (String field : fields) {
                if (field.isEmpty()) {
                    System.out.println("Each field must have a value.");
                    return false;
                }
            }
            return true;
        }
        System.out.println("Wrong number of fields. Check the command format above.");
        return false;
    }

    private void addBook(String id, String title) {
        if (books.putIfAbsent(id, new Book(title)) == null) {
            System.out.println("Book added.");
        } else {
            System.out.println("Book ID already exists.");
        }
    }

    private void register(String id, String name) {
        if (members.putIfAbsent(id, name) == null) {
            System.out.println("Member registered.");
        } else {
            System.out.println("Member ID already exists.");
        }
    }

    private void updateLoan(String bookId, String memberId, boolean borrowing) {
        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("Book ID not found.");
            return;
        }
        if (!members.containsKey(memberId)) {
            System.out.println("Member ID not found.");
            return;
        }
        if (borrowing) {
            if (book.borrowerId != null) {
                System.out.println("Book is already on loan.");
                return;
            }
            book.borrowerId = memberId;
            System.out.println("Book borrowed.");
        } else {
            if (!memberId.equals(book.borrowerId)) {
                System.out.println("This member has no active loan for the book.");
                return;
            }
            book.borrowerId = null;
            System.out.println("Book returned.");
        }
    }

    private void listBooks() {
        if (books.isEmpty()) {
            System.out.println("Catalog is empty.");
            return;
        }
        books.forEach((id, book) -> {
            String status = book.borrowerId == null ? "Available"
                    : "On loan to " + members.get(book.borrowerId) + " (" + book.borrowerId + ")";
            System.out.printf("%s | %s | %s%n", id, book.title, status);
        });
    }
}
