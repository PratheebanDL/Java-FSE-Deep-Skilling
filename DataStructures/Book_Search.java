class LibraryBook
{
    private int bookId;
    private String bookTitle;
    private String bookAuthor;

    public LibraryBook(int bookId,
                       String bookTitle,
                       String bookAuthor)
    {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    public int getBookId()
    {
        return bookId;
    }

    public String getBookTitle()
    {
        return bookTitle;
    }

    public String getBookAuthor()
    {
        return bookAuthor;
    }
}

class BookSearchEngine
{
    public static LibraryBook linearSearch(
            LibraryBook[] bookList,
            int targetBookId)
    {
        for (int currentIndex = 0;
             currentIndex < bookList.length;
             currentIndex++)
        {
            if (bookList[currentIndex].getBookId()
                    == targetBookId)
            {
                return bookList[currentIndex];
            }
        }

        return null;
    }

    public static LibraryBook binarySearch(
            LibraryBook[] bookList,
            int targetBookId)
    {
        int lowIndex = 0;
        int highIndex = bookList.length - 1;

        while (lowIndex <= highIndex)
        {
            int middleIndex =
                    lowIndex + (highIndex - lowIndex) / 2;

            int middleBookId =
                    bookList[middleIndex].getBookId();

            if (middleBookId == targetBookId)
            {
                return bookList[middleIndex];
            }
            else if (middleBookId < targetBookId)
            {
                lowIndex = middleIndex + 1;
            }
            else
            {
                highIndex = middleIndex - 1;
            }
        }

        return null;
    }
}

public class Main
{
    public static void main(String[] args)
    {
        LibraryBook[] unsortedBookList =
        {
            new LibraryBook(
                    101,
                    "Digital Communication",
                    "Simon Haykin"),

            new LibraryBook(
                    113,
                    "Microprocessors",
                    "Ramesh Gaonkar"),

            new LibraryBook(
                    165,
                    "Embedded Systems",
                    "Raj Kamal")
        };

        LibraryBook[] sortedBookList =
        {
            new LibraryBook(
                    101,
                    "Digital Communication",
                    "Simon Haykin"),

            new LibraryBook(
                    113,
                    "Microprocessors",
                    "Ramesh Gaonkar"),

            new LibraryBook(
                    165,
                    "Embedded Systems",
                    "Raj Kamal")
        };

        LibraryBook linearSearchResult =
                BookSearchEngine.linearSearch(
                        unsortedBookList,
                        113);

        if (linearSearchResult == null)
        {
            System.out.println(
                    "Book not found using Linear Search");
        }
        else
        {
            System.out.println(
                    "Book found using Linear Search");
            System.out.println(
                    linearSearchResult.getBookTitle()
                    + " by "
                    + linearSearchResult.getBookAuthor());
        }

        LibraryBook binarySearchResult =
                BookSearchEngine.binarySearch(
                        sortedBookList,
                        165);

        if (binarySearchResult == null)
        {
            System.out.println(
                    "Book not found using Binary Search");
        }
        else
        {
            System.out.println(
                    "Book found using Binary Search");
            System.out.println(
                    binarySearchResult.getBookTitle()
                    + " by "
                    + binarySearchResult.getBookAuthor());
        }
    }
}