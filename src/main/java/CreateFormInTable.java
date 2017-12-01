/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/21028286/itext-editable-texfield-is-not-clickable
 */

 
import java.io.File;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
 
public class CreateFormInTable {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/create_form_in_table.pdf";
 
    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CreateFormInTable().manipulatePdf(DEST);
    }
 
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
 
        for (int i = 0; i < 50; i++) {
            System.out.println(i);
            Table table = new Table(2);
            Cell cell;
            cell = new Cell().add("Name:");
            table.addCell(cell);
            cell = new Cell();
            cell.setNextRenderer(new MyCellRenderer(cell, "name" + i));
            table.addCell(cell);
            cell = new Cell().add("Address");
            table.addCell(cell);
            cell = new Cell();
            cell.setNextRenderer(new MyCellRenderer(cell, "address" + i));
            table.addCell(cell);
            doc.add(table);
        }
        doc.close();
    }
 
 
    private class MyCellRenderer extends CellRenderer {
        protected String fieldName;
 
        public MyCellRenderer(Cell modelElement, String fieldName) {
            super(modelElement);
            this.fieldName = fieldName;
        }
 
        @Override
        public void draw(DrawContext drawContext) {
            super.draw(drawContext);
            PdfTextFormField field = PdfFormField.createText(drawContext.getDocument(), getOccupiedAreaBBox(), fieldName, "");
            PdfAcroForm form = PdfAcroForm.getAcroForm(drawContext.getDocument(), true);
            form.addField(field);
        }
    }
}