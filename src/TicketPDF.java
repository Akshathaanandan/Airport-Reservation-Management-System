import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TicketPDF {

    // ── layout constants ────────────────────────────────────────────────────────
    private static final float PAGE_W      = PDRectangle.A4.getWidth();   // 595
    private static final float PAGE_H      = PDRectangle.A4.getHeight();  // 842
    private static final float MARGIN      = 50f;
    private static final float CONTENT_W   = PAGE_W - 2 * MARGIN;

    // colours
    private static final Color HEADER_BG   = new Color(13,  71, 161);   // deep blue
    private static final Color SECTION_BG  = new Color(227, 242, 253);  // light blue
    private static final Color TABLE_HEAD  = new Color(21, 101, 192);   // medium blue
    private static final Color ROW_ALT     = new Color(241, 248, 255);  // very light blue
    private static final Color TEXT_WHITE  = Color.WHITE;
    private static final Color TEXT_DARK   = new Color(30, 30, 30);
    private static final Color ACCENT      = new Color(255, 193, 7);    // amber

    // row height for table rows
    private static final float ROW_H = 22f;

    // ── public entry point ───────────────────────────────────────────────────────
    /**
     * Generates a PDF boarding-style ticket for the given confirmed booking.
     * The file is saved to  tickets/Ticket_<PNR>.pdf
     * relative to the working directory.
     *
     * Does nothing if the booking is null or not CONFIRMED.
     */
    public static void generate(Booking booking) {
        if (booking == null) return;
        if (!"CONFIRMED".equalsIgnoreCase(booking.getBookingStatus())) return;

        // ensure output directory exists
        File dir = new File("tickets");
        if (!dir.exists()) dir.mkdirs();

        String filename = "tickets" + File.separator + "Ticket_" + booking.getPnr() + ".pdf";

        try (PDDocument doc = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            // font references (PDFBox 3 uses Standard14Fonts.FontName enum)
            PDType1Font fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font fontBold    = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font fontItalic  = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);

            try (PDPageContentStream cs = new PDPageContentStream(
                    doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {

                float y = PAGE_H; // cursor starts at top

                // ── 1. Header banner ───────────────────────────────────────────
                y = drawHeaderBanner(cs, fontBold, fontRegular, y);

                // ── 2. PNR strip ───────────────────────────────────────────────
                y = drawPnrStrip(cs, fontBold, fontRegular, booking.getPnr(), y);

                // ── 3. Flight info table ───────────────────────────────────────
                y = drawSectionTitle(cs, fontBold, "FLIGHT INFORMATION", y);
                Flight f = booking.getFlight();
                y = drawFlightTable(cs, fontBold, fontRegular, f, y);

                // ── 4. Booking info table ──────────────────────────────────────
                y = drawSectionTitle(cs, fontBold, "BOOKING INFORMATION", y);
                y = drawBookingTable(cs, fontBold, fontRegular, booking, y);

                // ── 5. Passenger table ─────────────────────────────────────────
                y = drawSectionTitle(cs, fontBold, "PASSENGER DETAILS", y);
                y = drawPassengerTable(cs, fontBold, fontRegular,
                        booking.getPassengers(), y);

                // ── 6. Fare summary ────────────────────────────────────────────
                y = drawSectionTitle(cs, fontBold, "FARE SUMMARY", y);
                y = drawFareSummary(cs, fontBold, fontRegular, booking, y);

                // ── 7. Footer ──────────────────────────────────────────────────
                drawFooter(cs, fontItalic, fontBold);
            }

            doc.save(filename);
            System.out.println("\n  PDF ticket saved  =>  " + new File(filename).getAbsolutePath());

        } catch (IOException e) {
            System.out.println("\n  [WARNING] PDF generation failed: " + e.getMessage());
            System.out.println("  Your booking is CONFIRMED. The PDF could not be created.");
        }
    }

    // ── Helper: get departure/arrival from Flight (they're stored on Flight) ────
    // (Flight stores departureTime / arrivalTime directly)

    // ═══════════════════════════════════════════════════════════════════════════
    //  DRAWING HELPERS
    // ═══════════════════════════════════════════════════════════════════════════

    /** Full-width blue header with title and tagline. Returns new y. */
    private static float drawHeaderBanner(PDPageContentStream cs,
            PDType1Font fontBold, PDType1Font fontRegular, float y) throws IOException {

        float bannerH = 80f;
        fillRect(cs, 0, y - bannerH, PAGE_W, bannerH, HEADER_BG);

        // airline logo placeholder – amber bar on the left
        fillRect(cs, 0, y - bannerH, 8f, bannerH, ACCENT);

        drawText(cs, fontBold, 18f, MARGIN, y - 32f, TEXT_WHITE,
                "AIRPORT RESERVATION SYSTEM");
        drawText(cs, fontRegular, 10f, MARGIN, y - 50f, new Color(187, 222, 251),
                "Official Boarding Ticket  |  Keep this document safe");
        drawText(cs, fontBold, 9f, MARGIN, y - 68f, ACCENT,
                "STATUS: CONFIRMED");

        return y - bannerH - 10f;
    }

    /** Prominent PNR strip. Returns new y. */
    private static float drawPnrStrip(PDPageContentStream cs,
            PDType1Font fontBold, PDType1Font fontRegular,
            String pnr, float y) throws IOException {

        float stripH = 36f;
        fillRect(cs, MARGIN, y - stripH, CONTENT_W, stripH, SECTION_BG);

        // left label
        drawText(cs, fontRegular, 9f, MARGIN + 10f, y - 14f,
                new Color(66, 66, 66), "PNR NUMBER");
        drawText(cs, fontBold, 15f, MARGIN + 10f, y - 29f,
                HEADER_BG, pnr);

        // right label
        String rightLabel = "BOOKING CONFIRMED";
        float labelW = fontBold.getStringWidth(rightLabel) / 1000f * 11f;
        drawText(cs, fontBold, 11f, MARGIN + CONTENT_W - labelW - 10f, y - 22f,
                new Color(27, 94, 32), rightLabel);

        return y - stripH - 14f;
    }

    /** Section title bar. Returns new y. */
    private static float drawSectionTitle(PDPageContentStream cs,
            PDType1Font fontBold, String title, float y) throws IOException {

        float titleH = 20f;
        fillRect(cs, MARGIN, y - titleH, CONTENT_W, titleH, TABLE_HEAD);
        drawText(cs, fontBold, 9f, MARGIN + 8f, y - 14f, TEXT_WHITE, title);
        return y - titleH;
    }

    /** Two-column flight details table. Returns new y. */
    private static float drawFlightTable(PDPageContentStream cs,
            PDType1Font fontBold, PDType1Font fontRegular,
            Flight flight, float y) throws IOException {

        if (flight == null) return y - 10f;

        String[][] rows = {
            { "Flight Number",    safeGet(flight.getFlightNumber()) },
            { "Airline",          safeGet(flight.getAirline())       },
            { "From (Source)",    safeGet(flight.getSource())        },
            { "To (Destination)", safeGet(flight.getDestination())   },
            { "Departure Time",   safeGet(flight.getDepartureTime()) },
            { "Arrival Time",     safeGet(flight.getArrivalTime())   },
        };
        return drawTwoColTable(cs, fontBold, fontRegular, rows, y);
    }

    /** Two-column booking info table. Returns new y. */
    private static float drawBookingTable(PDPageContentStream cs,
            PDType1Font fontBold, PDType1Font fontRegular,
            Booking b, float y) throws IOException {

        String[][] rows = {
            { "Travel Class",      b.getTravelClass()                           },
            { "Number of Tickets", String.valueOf(b.getNumberOfTickets())        },
            { "Booking Status",    b.getBookingStatus()                         },
        };
        return drawTwoColTable(cs, fontBold, fontRegular, rows, y);
    }

    /** Passenger table (multi-row, one row per passenger). Returns new y. */
    private static float drawPassengerTable(PDPageContentStream cs,
            PDType1Font fontBold, PDType1Font fontRegular,
            ArrayList<Passenger> passengers, float y) throws IOException {

        if (passengers == null || passengers.isEmpty()) return y - 10f;

        // column widths
        float col0 = 40f;    // #
        float col1 = 190f;   // Name
        float col2 = 60f;    // Age
        float col3 = CONTENT_W - col0 - col1 - col2;  // Gender

        // header row
        float rowY = y;
        fillRect(cs, MARGIN, rowY - ROW_H, CONTENT_W, ROW_H, TABLE_HEAD);
        float tx = MARGIN + 6f;
        drawText(cs, fontBold, 8.5f, tx,          rowY - 14f, TEXT_WHITE, "#");
        drawText(cs, fontBold, 8.5f, tx + col0,   rowY - 14f, TEXT_WHITE, "PASSENGER NAME");
        drawText(cs, fontBold, 8.5f, tx + col0 + col1, rowY - 14f, TEXT_WHITE, "AGE");
        drawText(cs, fontBold, 8.5f, tx + col0 + col1 + col2, rowY - 14f, TEXT_WHITE, "GENDER");
        rowY -= ROW_H;

        for (int i = 0; i < passengers.size(); i++) {
            Passenger p = passengers.get(i);
            Color bg = (i % 2 == 0) ? Color.WHITE : ROW_ALT;
            fillRect(cs, MARGIN, rowY - ROW_H, CONTENT_W, ROW_H, bg);
            drawText(cs, fontRegular, 9f, tx,          rowY - 14f, TEXT_DARK, String.valueOf(i + 1));
            drawText(cs, fontRegular, 9f, tx + col0,   rowY - 14f, TEXT_DARK, p.getName());
            drawText(cs, fontRegular, 9f, tx + col0 + col1, rowY - 14f, TEXT_DARK, String.valueOf(p.getAge()));
            drawText(cs, fontRegular, 9f, tx + col0 + col1 + col2, rowY - 14f, TEXT_DARK, p.getGender());
            rowY -= ROW_H;
        }
        return rowY - 10f;
    }

    /** Fare breakdown box. Returns new y. */
    private static float drawFareSummary(PDPageContentStream cs,
            PDType1Font fontBold, PDType1Font fontRegular,
            Booking b, float y) throws IOException {

        String[][] rows = {
            { "Price Per Ticket", "Rs. " + String.format("%.2f", b.getPricePerTicket()) },
            { "Number of Tickets", String.valueOf(b.getNumberOfTickets()) },
        };
        y = drawTwoColTable(cs, fontBold, fontRegular, rows, y);

        // Total fare highlighted row
        float totalH = ROW_H + 4f;
        fillRect(cs, MARGIN, y - totalH, CONTENT_W, totalH, HEADER_BG);
        drawText(cs, fontBold, 10f, MARGIN + 10f, y - 17f, ACCENT, "TOTAL FARE");
        String totalStr = "Rs. " + String.format("%.2f", b.getTotalPrice());
        float tw = fontBold.getStringWidth(totalStr) / 1000f * 12f;
        drawText(cs, fontBold, 12f, MARGIN + CONTENT_W - tw - 10f, y - 18f, TEXT_WHITE, totalStr);

        return y - totalH - 14f;
    }

    /** Small footer at the bottom of the page. */
    private static void drawFooter(PDPageContentStream cs,
            PDType1Font fontItalic, PDType1Font fontBold) throws IOException {

        float footerY = 42f;
        fillRect(cs, 0, footerY - 2f, PAGE_W, 1.5f, ACCENT);  // amber line
        drawText(cs, fontItalic, 8f, MARGIN, footerY, new Color(100, 100, 100),
                "This is a computer-generated ticket. No signature required.");
        drawText(cs, fontBold, 8f, PAGE_W - MARGIN - 160f, footerY,
                new Color(100, 100, 100),
                "Airport Reservation System  |  2026");
    }

    // ─── generic two-column table ────────────────────────────────────────────────
    private static float drawTwoColTable(PDPageContentStream cs,
            PDType1Font fontBold, PDType1Font fontRegular,
            String[][] rows, float y) throws IOException {

        float labelW = CONTENT_W * 0.42f;
        float valueW = CONTENT_W - labelW;

        for (int i = 0; i < rows.length; i++) {
            Color bg = (i % 2 == 0) ? Color.WHITE : ROW_ALT;
            fillRect(cs, MARGIN, y - ROW_H, CONTENT_W, ROW_H, bg);

            drawText(cs, fontBold,    8.5f, MARGIN + 10f,            y - 14f, TEXT_DARK, rows[i][0]);
            drawText(cs, fontRegular, 9f,   MARGIN + labelW + 10f,   y - 14f, TEXT_DARK, rows[i][1]);
            y -= ROW_H;
        }
        return y - 8f;
    }

    // ─── primitive helpers ───────────────────────────────────────────────────────

    private static void fillRect(PDPageContentStream cs,
            float x, float y, float w, float h, Color color) throws IOException {
        cs.setNonStrokingColor(color);
        cs.addRect(x, y, w, h);
        cs.fill();
    }

    private static void drawText(PDPageContentStream cs,
            PDType1Font font, float size, float x, float y,
            Color color, String text) throws IOException {
        if (text == null) text = "";
        cs.setNonStrokingColor(color);
        cs.beginText();
        cs.setFont(font, size);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    // PDFBox 3 Flight helper: expose departure/arrival if not already on Booking
    private static String safeGet(String s) {
        return s != null ? s : "-";
    }
}
