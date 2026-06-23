import java.util.*;

interface Document
{
    public void display();
}

class WordDocument implements Document
{
    @Override
    public void display()
    {
        System.out.println("Word Document is being created.");
    }
}

class PdfDocument implements Document
{
    @Override 
    public void display()
    {
        System.out.println("Pdf Document is being created.");
    }
}

class ExcelDocument implements Document
{
    @Override
    public void display()
    {
        System.out.println("Excel Document is being created.");
    }
}

abstract class DocumentFactory
{
    public abstract Document createDocument();
    
    public void docdisp()
    {
        Document document = createDocument();
        document.display();
    }
}

class Word extends DocumentFactory
{
    public Document createDocument()
    {
        return new WordDocument();
    }
}

class Pdf extends DocumentFactory
{
    public Document createDocument()
    {
        return new PdfDocument();
    }
}

class Excel extends DocumentFactory
{
    public Document createDocument()
    {
        return new ExcelDocument();
    }
}

class Main
{
    public static void main(String[] args)
    {
        DocumentFactory word = new Word();
        word.docdisp();
        
        DocumentFactory excel = new Excel();
        excel.docdisp();
        
        DocumentFactory pdf = new Pdf();
        pdf.docdisp();
    }
}