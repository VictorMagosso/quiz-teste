/*PACOTES DO SEU PROJETO*/

public class TestBasicActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 60000; //1 minuto para responder cada questao


    private TextView tituloPergunta, scoreAtual, questionCount, timeCount;
    private RadioGroup radioGroupTest;
    private RadioButton opcao1, opcao2, opcao3, opcao4;
    private Button confirm;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultTimeCount;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private List<QuestionsBasic> questionList;
    private int questionCounter, questionCountTotal, score;
    private QuestionsBasic questaoAtual;
    private boolean answered;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.questoes_basic );
        getSupportActionBar().hide();

        tituloPergunta = findViewById( R.id.tituloPergunta );
        questionCount = findViewById( R.id.questaoAtual );
        scoreAtual = findViewById( R.id.scoreAtual );
        timeCount = findViewById( R.id.timeCount );
        radioGroupTest = findViewById( R.id.radioGroupTest );
        opcao1 = findViewById( R.id.opcaoUm );
        opcao2 = findViewById( R.id.opcaoDois );
        opcao3 = findViewById( R.id.opcaoTres );
        opcao4 = findViewById( R.id.opcaoQuatro );
        confirm = findViewById( R.id.buttonConfirm );

        textColorDefaultRb = opcao1.getTextColors();
        textColorDefaultTimeCount = timeCount.getTextColors();

        BasicTestDbHelper dbHelper = new BasicTestDbHelper( this );
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle( questionList );

        showNextQuestion();

        confirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered){
                    if(opcao1.isChecked() || opcao2.isChecked() || opcao3.isChecked() || opcao4.isChecked()){
                        checkAnswer();
                    }else{
                        Toast.makeText( TestBasicActivity.this, "Please, select an answer!", Toast.LENGTH_SHORT ).show();
                    }
                }else{
                    showNextQuestion();
                }
            }
        } );
    }
    private void showNextQuestion(){
        opcao1.setTextColor( textColorDefaultRb );
        opcao2.setTextColor( textColorDefaultRb );
        opcao3.setTextColor( textColorDefaultRb );
        opcao4.setTextColor( textColorDefaultRb );
        radioGroupTest.clearCheck();

        if (questionCounter < questionCountTotal){
            questaoAtual = questionList.get( questionCounter );

            tituloPergunta.setText( questaoAtual.getQuestion() );
            opcao1.setText( questaoAtual.getOption1() );
            opcao2.setText( questaoAtual.getOption2() );
            opcao3.setText( questaoAtual.getOption3() );
            opcao4.setText( questaoAtual.getOption4() );

            questionCounter++;
            questionCount.setText( "Question: " + questionCounter + "/" + questionCountTotal );
            answered = false;
            confirm.setText( "Confirm" );

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();

        }else{
            finishTest();
        }
    }
    private void startCountDown(){
        countDownTimer = new CountDownTimer( timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();

            }
        }.start();
    }

    private void updateCountDownText(){
        int minutes = (int)(timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format( Locale.getDefault(), "%02d:%02d",minutes, seconds );

        timeCount.setText( timeFormatted );

        if (timeLeftInMillis < 10000) {
            timeCount.setTextColor( Color.RED );
        }else{
            timeCount.setTextColor( textColorDefaultTimeCount );
        }
    }
    private void checkAnswer(){
        answered = true;

        countDownTimer.cancel();

        RadioButton selectedOption = findViewById( radioGroupTest.getCheckedRadioButtonId() );
        int answer = radioGroupTest.indexOfChild( selectedOption ) + 1;

        if (answer == questaoAtual.getAnswer()){
            score++;
            scoreAtual.setText( "Score: " + score );
        }
        showSolution();
    }
    private void showSolution(){
        RadioButton selectedOption = findViewById( radioGroupTest.getCheckedRadioButtonId() );
        int answer = radioGroupTest.indexOfChild( selectedOption ) + 1;

        opcao1.setTextColor( Color.RED );
        opcao2.setTextColor( Color.RED );
        opcao3.setTextColor( Color.RED );
        opcao4.setTextColor( Color.RED );

        if (questaoAtual.getAnswer() == answer){
            selectedOption.setTextColor( getResources().getColor( R.color.correctanswer ));
            tituloPergunta.setText( "Correct answer! Well done!" );
        }else{
            tituloPergunta.setText( "Wrong :(" );
        }

        if (questionCounter < questionCountTotal){
            confirm.setText( "Next" );
        }else{
            confirm.setText( "Finish" );
        }
    }
    private void finishTest(){
        Intent resultado = new Intent(  );
        resultado.putExtra( EXTRA_SCORE, score );
        setResult( RESULT_OK, resultado );

        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finish();
        }else{
            Toast.makeText( this, "Press back twice again to go leave the test", Toast.LENGTH_SHORT ).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }
}
