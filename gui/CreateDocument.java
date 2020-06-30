package gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javafx.scene.control.TextInputDialog;
import logic.Exam;
import logic.Question;
import logic.User;

/**
 * this class generates the exam word file and downloading it to client file
 * system
 * 
 * @author chen1
 *
 */
public class CreateDocument {
	// *********************************
	// Fields
	// *********************************
	Exam exam;
	private User student;

	/**
	 * constructor to recive the requested exam details and the student info
	 * 
	 * @param exam
	 * @param student
	 */
	public CreateDocument(Exam exam, User student) {
		this.exam = exam;
		this.student = student;
	}

	/**
	 * this method create the exam word file and transferring it to student file
	 * system to his requested folder
	 * 
	 * @return
	 * @throws IOException
	 */
	public Boolean createWordExam() throws IOException {
		// Blank Document
		XWPFDocument document = new XWPFDocument();
		Iterator it = exam.getQuestions().entrySet().iterator();
		int i = 0;
		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run1 = paragraph1.createRun();
		run1.setText("ExamID: " + exam.getExamID());
		run1.addBreak();
		run1.setText("Course Name: " + exam.getCourseName());
		run1.addBreak();
		if (exam.getInstructionForStudent() != null) {
			run1.setText("instructions: " + exam.getInstructionForStudent());
			run1.addBreak();
		}
		run1.setText("please select the correct answer by writing V in the [   ] next to the correct answer");
		run1.addBreak();

		while (it.hasNext()) {
			i++;
			Map.Entry pair = (Map.Entry) it.next();
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.RIGHT);
			XWPFRun run = paragraph.createRun();
			run.setText("(" + pair.getValue().toString() + " points)");
			run.addBreak();
			run.setText(i + ". " + ((Question) pair.getKey()).getQuestionTxt());

			for (int j = 0; j < 4; j++) {
				run.addBreak();
				run.setText("   [ ]" + "(" + (j + 1) + ") " + ((Question) pair.getKey()).getAnswers()[j]);
			}
			run.addBreak();
			run.addBreak();
		}

		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = paragraph2.createRun();
		run2.setText("Good Luck!");
		// Write the Document in file system
		TextInputDialog dialog = new TextInputDialog("C:\\exams");
		dialog.setTitle("insert location");
		dialog.setHeaderText("please enter requested folder location");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			FileOutputStream out = new FileOutputStream(
					new File(result.get() + "\\exam_" + exam.getExamID() + "_" + student.getuID() + ".docx"));
			document.write(out);
			out.close();
			return true;
		}
		return false;

	}

}