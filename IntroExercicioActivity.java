/* AREA PARA OS PACOTES DE SEU PROJETO */

public class IntroExercicioActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_TEST = 1; //vamos passar como parametro para iniciar a activity do teste
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore"; //precisaremos passar a chave como parametro
    private ImageView trofeuBasic; //imagem caso o usuario acerte mais do que o minimo
    private TextView textViewHighScore, textoAprovado;

    private int highscore;

    Button buttonStartTest;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.intro_basic_test );
        getSupportActionBar().hide();
        trofeuBasic = findViewById( R.id.trofeuBasic );
        textoAprovado = findViewById( R.id.textoAprovado );
        textViewHighScore = findViewById( R.id.highScore );
        loadHighscore();

        buttonStartTest = findViewById( R.id.buttonStartTest );
        buttonStartTest.setOnClickListener( new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                startTest();
            }
        } );
        //aqui para recuperar o que esta disponivel caso ele tenha sido aprovado
        if (highscore > 13){

          trofeuBasic.setVisibility( View.VISIBLE );
          textoAprovado.setText( "Congratulations! You're now able to start the Intermediate Sessions! Click on the button and let's do it!" );

       }
    }
    private void startTest(){
        startActivityForResult( new Intent( getApplicationContext(), TestBasicActivity.class ), REQUEST_CODE_TEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == REQUEST_CODE_TEST){
            if (resultCode == RESULT_OK){ 
                int score = data.getIntExtra( TestBasicActivity.EXTRA_SCORE, 0 ); //0 como valor default
                if (score > highscore){
                    updateHighScore(score);

                }
            }
        }
    }
    private void loadHighscore(){
        SharedPreferences preferences = getSharedPreferences( SHARED_PREFS, MODE_PRIVATE );
        highscore = preferences.getInt( KEY_HIGHSCORE, 0 );
        textViewHighScore.setText( "Highscore: " + highscore );

    }
    private void updateHighScore(int highscoreNew){
        highscore = highscoreNew;
        textViewHighScore.setText( "Highscore: " + highscore );

        SharedPreferences preferences = getSharedPreferences( SHARED_PREFS, MODE_PRIVATE );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt( KEY_HIGHSCORE, highscore );
        editor.apply();


    }
}
