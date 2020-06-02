
public class BasicTestDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BasicTest";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public BasicTestDbHelper(@Nullable Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
  //ID nao é necessario criar na tabela porque já é um atributo automatico do OpenHelper
        final String SQL_CREATE_QUESTIONS = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER + " INTEGER" +
                ")";
        db.execSQL( SQL_CREATE_QUESTIONS );
        criarQuestoes();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //caso queira atualizar a versão do seu banco de dados
        db.execSQL( "DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME );
        onCreate( db );
    }
    private void criarQuestoes(){
        QuestionsBasic q1 = new QuestionsBasic( " --- AQUI VOCÊ COLOCA O TITULO DA PERGUNTA ---  ", "OPCAO 1", "OPCAO 2", "OPCAO 3", "OPCAO 4", 2 ); //o utlimo numero é o numero da resposta certa para usarmos na lista propriamente dita
    // ...   
        QuestionsBasic q20 = new QuestionsBasic(  " --- AQUI VOCÊ COLOCA O TITULO DA PERGUNTA ---  ", "OPCAO 1", "OPCAO 2", "OPCAO 3", "OPCAO 4", 4 );
        addQuestion( q1 );
     //   ...
        addQuestion( q20 );
    }
    private void addQuestion(QuestionsBasic questionsBasic){
    //insere as questões na tabela
        ContentValues cv = new ContentValues(  );
        cv.put( QuestionsTable.COLUMN_QUESTION, questionsBasic.getQuestion() );
        cv.put( QuestionsTable.COLUMN_OPTION1, questionsBasic.getOption1() );
        cv.put( QuestionsTable.COLUMN_OPTION2, questionsBasic.getOption2() );
        cv.put( QuestionsTable.COLUMN_OPTION3, questionsBasic.getOption3() );
        cv.put( QuestionsTable.COLUMN_OPTION4, questionsBasic.getOption4() );
        cv.put( QuestionsTable.COLUMN_ANSWER, questionsBasic.getAnswer() );
        db.insert( QuestionsTable.TABLE_NAME, null, cv );
    }

    public List<QuestionsBasic> getAllQuestions(){
        List<QuestionsBasic> questionsBasicList = new ArrayList<>(  );
        db = getReadableDatabase();
        Cursor c = db.rawQuery( "SELECT * FROM " + QuestionsTable.TABLE_NAME, null );

        if (c.moveToFirst()) {
            do {
                QuestionsBasic questionsBasic = new QuestionsBasic(  );
                questionsBasic.setQuestion( c.getString( c.getColumnIndex( QuestionsTable.COLUMN_QUESTION ) ) );
                questionsBasic.setOption1( c.getString( c.getColumnIndex( QuestionsTable.COLUMN_OPTION1 ) ) );
                questionsBasic.setOption2( c.getString( c.getColumnIndex( QuestionsTable.COLUMN_OPTION2 ) ) );
                questionsBasic.setOption3( c.getString( c.getColumnIndex( QuestionsTable.COLUMN_OPTION3 ) ) );
                questionsBasic.setOption4( c.getString( c.getColumnIndex( QuestionsTable.COLUMN_OPTION4 ) ) );
                questionsBasic.setAnswer( c.getInt( c.getColumnIndex( QuestionsTable.COLUMN_ANSWER ) ) );
                questionsBasicList.add( questionsBasic );
            } while (c.moveToNext());
        }
        c.close();
        return questionsBasicList;
    }
}
