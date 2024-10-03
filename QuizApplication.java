import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Question
{
    String questionText;
    String[] Options;
    int correctOption;

    public Question(String questionText, String []Options, int correctOption){
        this.questionText = questionText;
        this.Options = Options;
        this.correctOption = correctOption;
    }

    public String getQuestionText(){
        return questionText;
    }

    public String[] getOptions(){
        return Options;
    }

    public int correctOption(){
        return correctOption;
    }
}
public class QuizApplication
{
    private List<Question> questions;
    private int score;

    public QuizApplication(){
        this.questions = new ArrayList<>();
        this.score = 0;
    }

    public void addQuestion(Question q){
        questions.add(q);
    }

    public void startQuiz(){
        Scanner sc = new Scanner(System.in);

        for(Question q : questions){
            System.out.println("\n"+q.getQuestionText());
            String []Options = q.getOptions();
            for(int i = 0; i < Options.length; i++){
                System.out.println((i+1)+": "+Options[i]);
            }
            // variables track time and user input.
            final boolean[] answered = {false};
            final int[] userAnswer = {-1};
            // Timer thread
            Thread timerThread = new Thread(() -> {
                int remainingTime = 15;
                try {
                    while(remainingTime > 0 && !answered[0]){
                        System.out.print("\rTime remaining: "+remainingTime+" seconds  ");
                        Thread.sleep(1000); 
                        remainingTime--;
                    }
                } catch (InterruptedException e) {
                    System.out.println("\nTime Stopped.");
                } 
            });

            timerThread.start();

            try {
                System.out.print("\nAnswer : ");
                userAnswer[0] = sc.nextInt()-1;
                answered[0] = true;
                timerThread.interrupt();
            } catch (Exception e) {
                System.out.println("Invalid input. Skipping to next question.");
            }
            

            if(answered[0] && userAnswer[0] >= 0 && userAnswer[0] < Options.length){
                if(userAnswer[0] == q.correctOption()){
                    score++;
                }
            } else{
                System.out.println("Time's up! Moving to the next question...");
            }
        }

        displayResult();
        sc.close();
    }

    private void displayResult(){
        System.out.println("\nQuiz Completed!");
        System.out.println("Your Score: "+score+"/"+questions.size());
    }

    public static void main(String[] args) {
        QuizApplication quiz = new QuizApplication();

        quiz.addQuestion(new Question("What does the 'if' statement do?", new String[]{"Repeat code","Skip code","Execute if true","Exit program"}, 2));
        quiz.addQuestion(new Question("Which data types stores a single character?", new String[]{"Int","Float","Char","String"}, 2));
        quiz.addQuestion(new Question("What is 2+2*3 ?", new String[]{"6","8","10","12"}, 1));
        quiz.addQuestion(new Question("Which loop runs for a set number of times?", new String[]{"while","for","do-while","if-else"}, 1));
        quiz.addQuestion(new Question("What does Break do?", new String[]{"Exit loop","Skip statement","Repeat Statement","Execute statement"}, 0));

        quiz.startQuiz();
    }
}