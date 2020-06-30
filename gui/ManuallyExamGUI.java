package gui;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import client.ClientConsole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Exam;
import logic.ExamInExecution;
import logic.OvertimeDetails;
import logic.StudentController;
import logic.StudentInExam;
import login.LoginGUI;

/**
 * This class let the student perform an exam manually , the student downloads
 * the exam to his computer than the count-down begins and then he can upload
 * the solved answer back to system.
 * 
 * @author Rotem Vaknin
 *
 */
public class ManuallyExamGUI {

	// **************************************************
	// Fields
	// **************************************************
	ExamInExecution exam;
	@FXML
	Button downloadExam;
	@FXML
	Button uploadExam;

	@FXML
	TextField examID;

	@FXML
	TextField courseNameTF;

	@FXML
	private Text hoursTimer;

	@FXML
	private Text minutesTimer;

	@FXML
	private ImageView uploadImage;
	@FXML
	private ImageView downloadImage;

	@FXML
	private Text secondTimer;
	Map<Integer, String> numberMap;
	Integer currSeconds;
	Thread thrd;
	Thread lockThread;
	StudentController st;
	Integer countPassedTime;
	StudentInExam s;
	private ClientConsole client;
	Boolean flag = false;

	/**
	 * This method converts the time allocated for the exam to seconds.
	 * 
	 * @param h
	 *            - the hours allocated for the exam
	 * @param m
	 *            - the minutes allocated for the exam
	 * @param s
	 *            - seconds allocated for the exam
	 * @return The amount of time allocated for the exam to seconds.
	 */
	public Integer hmsToSeconds(Integer h, Integer m, Integer s) {
		Integer hToSeconds = h * 3600;
		Integer mToSeconds = m * 60;
		Integer total = hToSeconds + mToSeconds + s;
		return total;
	}

	/**
	 * Starts the count down, counts in seconds
	 */
	void startCountDown() {
		thrd = new Thread(new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					while (true) {
						setOutput();
						Thread.sleep(1000);
						if (currSeconds == 0) {
							uploadExam.setDisable(true);
							uploadImage.setVisible(false);
							s.setStudentStatus("NotFinished");
							int actualDuration = (countPassedTime / 60) + 1;
							s.setActualDuration(actualDuration);
							uploadExam.setVisible(false);
							uploadImage.setVisible(false);
							st.changeStudentInExamStatus(s);
							countPassedTime++;
							thrd.stop();

						}
						currSeconds -= 1;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		thrd.start();
		lockThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (client.getMessage().getReturnObj() instanceof String) {
						if (((String) client.getMessage().getReturnObj()).equals("examIsLocked")) {
							System.out.println("locked :)");
							thrd.stop();
							s.setStudentStatus("notFinished");
							int actualDuration = (countPassedTime / 60) + 1;
							s.setActualDuration(actualDuration);
							st.changeStudentInExamStatus(s);
							uploadImage.setVisible(false);
							uploadExam.setVisible(false);
							lockThread.stop();
						}
					} else if (client.getMessage().getqueryToDo() instanceof String) {
						if (((String) client.getMessage().getqueryToDo()).equals("overtimeApproved") && flag == false) {
							System.out.println("over time is approved yesss");
							flag = true;
							client.getMessage().setqueryToDo(null);
							flag = false;
							int ot = ((OvertimeDetails) client.getMessage().getReturnObj()).getRequestedTime();
							currSeconds += (ot * 60);
						}
					}
				}

			}

		});
		lockThread.start();
	}

	/**
	 * displays the time onto the countdown clock, changes every second
	 */
	void setOutput() {
		LinkedList<Integer> currHms = secondsToHms(currSeconds);
		hoursTimer.setText(numberMap.get(currHms.get(0)));
		minutesTimer.setText(numberMap.get(currHms.get(1)));
		secondTimer.setText(numberMap.get(currHms.get(2)));

	}

	/**
	 * Converts the time from seconds to hours, minutes and seconds
	 * 
	 * @param currSecond
	 *            - the seconds to convert
	 * @return a list consists of hours, minutes and seconds
	 */
	LinkedList<Integer> secondsToHms(Integer currSecond) {
		Integer hours = currSecond / 3600;
		currSecond = currSecond % 3600;
		Integer minutes = currSecond / 60;
		currSecond = currSecond % 60;
		Integer seconds = currSecond;
		LinkedList<Integer> answer = new LinkedList<Integer>();
		answer.add(hours);
		answer.add(minutes);
		answer.add(seconds);
		return answer;
	}

	/**
	 * This method downloads the exam onto the user computer and starts the
	 * countdown.
	 * 
	 * @throws IOException
	 */
	public void downloadExamAction(ActionEvent ae) throws IOException {
		s.setStudentStatus("Started");
		st.changeStudentInExamStatus(s);
		Exam e = st.getExamByExamID(exam);
		CreateDocument createWord = new CreateDocument(e, st.getStudent());
		if (!createWord.createWordExam())
			return;
		countPassedTime = 0;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		startCountDown();
		downloadImage.setVisible(false);
		downloadExam.setDisable(true);

	}

	/**
	 * this method upload the exam from student to system and stops clock.
	 */
	public void uploadExamAction(ActionEvent ae) {
		thrd.stop();
		st.uploadManualExam("exam_" + exam.getExamDet().getExamID() + "_" + st.getStudent().getuID() + ".docx");
		uploadExam.setDisable(true);
		uploadImage.setVisible(false);
		int actualTime = (countPassedTime / 60) + 1;
		s.setActualDuration(actualTime);
		s.setStudentStatus("finished");
		st.changeStudentInExamStatus(s);
	}

	/**
	 * this initialize the data of the manually exam window
	 */
	public void initData(ExamInExecution examInExecution) {
		this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port, null);
		st = new StudentController();
		exam = examInExecution;
		s = new StudentInExam();
		s.setIsComp(false);
		s.setStudentID(st.getStudent().getuID());
		s.setStudentName(st.getStudent().getuName());
		s.setExamID(exam.getExamDet().getExamID());
		s.setExecutionID(exam.getExecutionID());
		s.setStudentStatus("notStarted");
		examID.setText(examInExecution.getExamDet().getExamID());
		courseNameTF.setText(examInExecution.getCourseName());
		numberMap = new TreeMap<Integer, String>();
		for (Integer i = 0; i <= 60; i++) {
			if (0 <= i && i <= 9)
				numberMap.put(i, "0" + i.toString());
			else
				numberMap.put(i, i.toString());
		}
		int duration = examInExecution.getExamDet().getDuration();
		currSeconds = hmsToSeconds(duration / 60, duration % 60, 0);
		setOutput();
		Stage window = (Stage) downloadExam.getScene().getWindow();
		// window.setOnCloseRequest(event -> {
		// if (uploadImage.isVisible())
		// event.consume();
		// });

	}

}
