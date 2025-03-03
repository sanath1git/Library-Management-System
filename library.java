import java.io.*;
import java.util.*;

// Library.java - Handles library operations
class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book added successfully.");
    }

    public void searchBook(String keyword) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) || 
                book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(book);
            }
        }
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Search results:");
            for (Book book : results) {
                System.out.println("- " + book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    public void checkoutBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (!book.isCheckedOut()) {
                    book.setCheckedOut(true);
                    System.out.println("Book checked out successfully.");
                } else {
                    System.out.println("Book is already checked out.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isCheckedOut()) {
                    book.setCheckedOut(false);
                    System.out.println("Book returned successfully.");
                } else {
                    System.out.println("Book was not checked out.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void saveBooksToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Book book : books) {
                writer.println(book.getTitle() + "," + book.getAuthor() + "," + book.isCheckedOut());
            }
            System.out.println("Books saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public void loadBooksFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            books.clear();
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                Book book = new Book(parts[0], parts[1]);
                book.setCheckedOut(Boolean.parseBoolean(parts[2]));
                books.add(book);
            }
            System.out.println("Books loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
