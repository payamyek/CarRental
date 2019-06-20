package payam.yektamaram.com.SupremeCarRentals;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import javax.print.*;
import java.io.IOException;
import java.io.File;

/**
 * Prints the invoice for the customer, also provides a print dialog so that the
 * customer can choose what printer and the settings.
 * 
 * <p>
 * <h2>Global Variables</h2>
 * <p>
 * <b>url - </b> The location of the invoice pdf file.
 * <p>
 * <b>pdf - </b> An instance of File that is a path of the url.
 * <p>
 * 
 * @version 1.4.0
 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth
 * @since 2018-05-07
 */

public class DropOffPrintInvoice {

	// File file = new
	// File("/SupremeCarRental/SupremeCarRentals/InvoiceTemplate.pdf");
	File pdf = new File("/SupremeCarRental/SupremeCarRentals/pickUpInvoice.pdf");

	// Default constructor
	public DropOffPrintInvoice() throws IOException, PrinterException {
		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
		printPDF(pdf, choosePrinter());
	}

	/**
	 * Gets the print dialog for the program. Allows the user to choose a printer to
	 * print from.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>printJob - </b> Creates an instance of the PrinterJob class which is used
	 * to get the print dialog for the program.
	 * <p>
	 */
	public static PrintService choosePrinter() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		if (printJob.printDialog()) {
			return printJob.getPrintService();
		} else {
			return null;
		}
	}

	/**
	 * Prints the documents using the printer that was generated from
	 * choosePrinter()
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>job - </b> Sets the print service and gets the printer job.
	 * <p>
	 */
	public static void printPDF(File pdf2, PrintService printer) throws IOException, PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		if (printer == null) {

		} else {
			job.setPrintService(printer);
			PDDocument doc = PDDocument.load(pdf2);
			job.setPageable(new PDFPageable(doc));
			job.print();
		}

	}
}